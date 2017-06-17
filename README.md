# wxNews
![](https://github.com/yangwenxin/wxNews/blob/master/1.gif) ![](https://github.com/yangwenxin/wxNews/blob/master/2.gif)


一款基于 Material Design + Rxjava2 + Retrofit + dagger2 + MVP 构架项目<br>
### 前言

之前在工作当中一直使用的是Mvc模式，一个逻辑复杂的界面会导致Activity当中代码过于臃肿，这样很不便于阅读，后期维护性也很差，代码看起来乱糟糟的，大部分同学应该有过这种的感受（公司突然扔给你一个项目说有个bug处理下，打开后发现Activity上千行，代码各种乱，阅读性极差，心里瞬间想太阳dog，还只能含泪改bug....）所以为了避免以上情况的发生，坚定了要学习Mvp并运用在新项目中的决心。<br>

### MVP架构模式
相信大家对mvp已经非常熟悉了，我就说一下自己的理解好了<br>

mvp架构模式可以提高代码的可维护性、可扩展性以及可阅读性。<br>

它将view层和model层做了分离，用一个presenter当做桥梁来交互。<br>

### Model
Model用于获取数据，比如项目初期后台接口还没有完善，那么就需要模拟一些数据返回来显示界面，接口完善后，修改下返回方式即可。<br>

### View
View一般表示我们常用的Activity或Fragment，主要用于和用户的交互，那么它只需要做显示的操作即可。<br>

### Presenter
Presenter是用作Model和View之间交互的桥梁，比如View层调用mPresenter.XXXX()函数去请求网络，Presenter会从Model中获取到数据，处理完逻辑之后回调到View层去显示<br>
![](https://github.com/yangwenxin/wxNews/blob/master/mvp.png)

### Retrofit + okhttp + Rxjava联网

用法不过多介绍，推荐两篇文章<br>
[给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083) , [扔物线](https://github.com/rengwuxian) 大佬的rxjava详解<br>
[从微观角度解读RxJava源码](https://zhuanlan.zhihu.com/p/22338235), [张磊](https://www.zhihu.com/people/BaronZ88/answers) 大佬分析的rxjava1.1.9的源码，十分详细<br>

### 常用姿势
1.联网过程中弹出dialog给用户友好的提示<br>

2.特定的界面需要多状态显示比如（加载中、联网失败、数据为空）<br>

### 姿势1
该姿势其实在项目中非常的常见，使用频率也非常高，几乎每一个请求都要显示和隐藏dialog，当时对于刚刚上手mvp+Retrofit+Rxjava的我一脸懵逼，后来看到有人在View层回调出两个方法showDialog()和hideDialog(),然后就屁颠屁颠的定义在了BaseView中使用起来。<br>
后来发现每个界面我都需要这样回调出两个方法，实在是太麻烦了，有没有什么方式能通过框架封装一下省去这个操作？<br>
沿着这个思路我就研究了rxjava的源码，当时使用的是rxjava1 在源码会发现subscribe订阅时会先调用Subscriber的onStart()，(rx2是onSubscribe(Disposabled))这个方法就可以很好的利用起来做一些准备操作，比如我们想实现的showDialog();<br>
于是就封装了一个Callback来实现Observer接口 （rxjava2）<br>

```
public abstract class Callback<T> implements Observer<T> {

   private Context mContext;//该context必须是Activity的context

   public ProgressDialogHandler mHandler;//用于显示dialog

   public Callback() {  //无参构造表示不显示dialog

   }

   public Callback(Context context) {//传递activity的context进行联网显示dialog

          this.mContext=context;

          mHandler=newProgressDialogHandler(context);

   }

   @Override
   public void onSubscribe(Disposable d) {

          showProgressDialog();//联网前显示dialog

          subscribe(d);//添加到CompositeDisposable中用于onDestroy时解除订阅

   }

   @Override
   public void onError(Throwablee) {

          LogUtils.e("出错了 -------------- "+e.toString());
          onFailure();
          dismissProgressDialog();//发生错误隐藏dialog

    }

    @Override
    public void onComplete() {

         dismissProgressDialog();//执行结束隐藏dialog

    }

    @Override
    public void onNext(T t) {

         HttpResult result= (HttpResult)t;

         if(!result.isError()) {//成功

              onSuccess(t);

         }
    }

    private void showProgressDialog() {

         if(mHandler!=null){

            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();

         }

    }

    private void dismissProgressDialog() {

       if(mHandler!=null) {

          mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();

          mHandler=null;

       }

    }

    protected abstract void subscribe(Disposabled);

    protected abstract void onFailure();

    protected abstract void onSuccess(T t);

}

```

以上代码便是实现方式，subscribe订阅时直接new CallBack() 通过是否传递context来决定是否显示dialog，但是有一个比较坑的一点创建dialog时，如果传入构造方法不是一个activity类型的上下文会抛出抛出BadTokenException异常<br>

### 姿势2
多状态布局其实是代理了一个FrameLayout，我们都知道FrameLayout帧布局非常简单，摆放的控件是一个一个叠加在左上角的，那么就可以创建三个我们需要显示状态的布局添加进framelayout<br>
```
public abstract class LoadingPage extends FrameLayout {

    public int state = STATE_LOADING;// 默认加载中的状态
    private View loadingView;// 加载中的界面
    private View errorView;// 加载失败的界面
    private View emptyView;// 加载为空的界面

    public View successView;// 加载成功的界面

    public LoadingPage(Context context) {
        super(context);
        init();
    }
}
```
在LoadingPage的构造方法中执行了一个init()方法，去添加3种不同状态的布局

```
 /**
     * 将几种不同的布局添加到当前布局中
     */
    private void init() {
        if (loadingView == null) {
            loadingView = createLoadingView();// 创建加载界面
            this.addView(loadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (errorView == null) {
            errorView = createErrorView();// 创建加载失败界面
            this.addView(errorView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if (emptyView == null) {
            emptyView = createEmptyView();// 创建加载为空界面
            this.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        showPage();
    }

```

并且记录几种需要的状态，之后通过showPage()去决定界面的显示状态;
```
    public static final int STATE_UNKNOWN = 0;//默认
    public static final int STATE_LOADING = 1;//加载中
    public static final int STATE_ERROR = 2;//联网异常
    public static final int STATE_EMPTY = 3;//界面为空
    public static final int STATE_SUCCESS = 4;//加载成功

      /**
         * 根据不同的状态切换不同的界面
         */
        public void showPage() {
            if (loadingView != null) {
                //  加载中  获取 未知   全部显示
                loadingView.setVisibility(state == STATE_LOADING || state == Constants.STATE_UNKNOWN ? View.VISIBLE : View.INVISIBLE);
            }
            if (errorView != null) {
                //
                errorView.setVisibility(state == Constants.STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
            }
            if (emptyView != null) {
                //
                emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
            }

            //成功界面
            if (state == STATE_SUCCESS) {
                // 请求成后 添加成功界面
                if (successView == null) {
                    successView = View.inflate(CommonApplication.getContext(), getLayoutId(), null);
                    this.addView(successView, new ViewGroup.LayoutParams(-1, -1));
                    initView();
                } else {
                    successView.setVisibility(View.VISIBLE);
                }
            } else {
                if (successView != null) {
                    successView.setVisibility(View.INVISIBLE);
                }
            }
        }
```
这样我们就可以封装到BaseFragment中去使用，Fragment中onCreateView需要返回一个View，那么我们就可以直接返回一个LoadingPage<br>
```
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            mLoadingPage = new LoadingPage(CommonApplication.getContext()) {
                @Override
                protected void initView() {

                    if (isFirst) {
                        LoadingBaseFragment.this.successView = this.successView;
                        bind = ButterKnife.bind(LoadingBaseFragment.this, LoadingBaseFragment.this.successView);
                        LoadingBaseFragment.this.init();
                        isFirst = false;
                    }
                }

                @Override
                protected void loadData() {
                    LoadingBaseFragment.this.loadData();
                }

                @Override
                protected int getLayoutId() {
                    return LoadingBaseFragment.this.getLayoutId();
                }
            };

            if (successView != null) {
                bind = ButterKnife.bind(this, successView);
            }
            loadData();
            return mLoadingPage;
    }

    @Override
    public void setState(int state) {
        if (mLoadingPage != null) {
            mLoadingPage.state = state;
            mLoadingPage.showPage();
        }
    }

```
在loadDeta当中去进行联网操作，无论是成功失败，都可以通过setState，改变当前LoadingPage所记录的state调用showPage进行界面状态的改变，如果是success，那么就会调用LoadingPage的getLayout去获取我们的布局id创建一个successView添加在Loadingpage当中，之后会调用initView,  LoadingPage的initView调用了我们BaseFragment抽象出的init方法这样可以在当中去初始化加载成功的界面了，setAdapter.......<br>

好了，就说到这里吧0.0 <br>

希望看到此项目的同学能有所收获，如果有好的建议欢迎issue，我们共同学习进步！<br>

### 总结

* 感谢 [代码家](http://gank.io/api) 的开放api接口<br>
* 感谢 [JessYan](https://github.com/JessYanCoding) 大佬的 [MVPArms](https://github.com/JessYanCoding/MVPArms) 使我从中学习到了dagger2的应用姿势。<br>
感觉dagger2其实是这些框架当中比较难理解的，之所以没讲是因为我也懂的不是很多，刚开始接触确实不太容易理解，经常研究着就懵逼了，直到现在掌握的也不是很好，但是我认为dagger2的分层很重要，比如Retrofit的创建等等 我们希望是它在应用中的实例只有一个，那么就需要通过Application Component在Application中去注入创建实例，再注入到Activity Component去供Activity使用。<br>
当然这只是最近学习中一些自己的看法，并不一定正确，dagger2的学习之路还很长。。。<br>
如果该项目对您有帮助，帮忙点个star吧！O(∩_∩)O<br>