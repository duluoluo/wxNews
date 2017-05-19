package com.wxandroid.common.utils.city;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.wxandroid.common.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

/**
 * Created by wenxin
 */
public class CityPicker extends AutoLinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker provincePicker;
	private ScrollerNumberPicker cityPicker;
	private ScrollerNumberPicker counyPicker;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	private Context context;

	public CityPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public CityPicker(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.country_layout, this);
		// 获取控件引用
		provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);
		cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
		counyPicker = (ScrollerNumberPicker) findViewById(R.id.couny);

		provincePicker.setData(getArrayList(AddressData.PROVINCES));
		provincePicker.setDefault(0);
		
		cityPicker.setData(getArrayList(AddressData.CITIES[0]));
		cityPicker.setDefault(0);
		
		counyPicker.setData(getArrayList(AddressData.COUNTIES[0][0]));
		counyPicker.setDefault(0);
		
		provincePicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (tempProvinceIndex != id) {
					String selectDay = cityPicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					
					
					cityPicker.setData(getArrayList(AddressData.CITIES[id]));
					cityPicker.setDefault(0);
					
					counyPicker.setData(getArrayList(AddressData.COUNTIES[id][0]));
					counyPicker.setDefault(0);
					
					int lastDay = Integer.valueOf(provincePicker.getListSize());
					if (id > lastDay) {
						provincePicker.setDefault(lastDay - 1);
					}
				}
				tempProvinceIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub
			}
		});
		cityPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub
				if (text.equals("") || text == null)
					return;
				if (temCityIndex != id) {
					String selectDay = provincePicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					
					counyPicker.setData(getArrayList(AddressData.COUNTIES[provincePicker.getSelected()][id]));
					counyPicker.setDefault(0);
					
					int lastDay = Integer.valueOf(cityPicker.getListSize());
					if (id > lastDay) {
						cityPicker.setDefault(lastDay - 1);
					}
				}
				temCityIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}
		});
		counyPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				// TODO Auto-generated method stub

				if (text.equals("") || text == null)
					return;
				if (tempCounyIndex != id) {
					String selectDay = provincePicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = cityPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					
					int lastDay = Integer.valueOf(counyPicker.getListSize());
					if (id > lastDay) {
						counyPicker.setDefault(lastDay - 1);
					}
				}
				tempCounyIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}
		});
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_VIEW:
				if (onSelectingListener != null)
					onSelectingListener.selected(true);
				break;
			default:
				break;
			}
		}

	};

	private ArrayList<String> getArrayList(String[] names) {
		ArrayList<String> mArrayList = new ArrayList<String>();
		for (int i = 0; i < names.length; i++) {
			mArrayList.add(names[i]);
		}
		return mArrayList;

	}
	
	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
		this.onSelectingListener = onSelectingListener;
	}

	public void SetProvince(int Pindex,int Cindex,int CCindex){


		provincePicker.setData(getArrayList(AddressData.PROVINCES));
		provincePicker.setDefault(Pindex);

		cityPicker.setData(getArrayList(AddressData.CITIES[Pindex]));
		cityPicker.setDefault(Cindex);

		counyPicker.setData(getArrayList(AddressData.COUNTIES[Pindex][Cindex]));
		counyPicker.setDefault(CCindex);
	}

	public String getprovince_string() {
		return provincePicker.getSelectedText();
	}

	public String getcity_string() {
		return cityPicker.getSelectedText();
	}

	public String getcouny_string() {
		return counyPicker.getSelectedText();
	}

	public int getprovince_code() {
		return AddressData.P_ID[provincePicker.getSelected()];
	}

	public int getcity_code() {
		return AddressData.C_ID[provincePicker.getSelected()][cityPicker.getSelected()];
	}

	public int getcouny_code() {
		return AddressData.C_C_ID[provincePicker.getSelected()][cityPicker.getSelected()][counyPicker.getSelected()];
	}

	public interface OnSelectingListener {

		public void selected(boolean selected);
	}
}