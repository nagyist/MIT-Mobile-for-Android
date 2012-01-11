package edu.mit.mitmobile2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TabConfigurator {
	TabHost mTabHost;
	Activity mActivity;
	ArrayList<String> mTabNames;
	ArrayList<Integer> mTabContentResourceIds;
	
	public TabConfigurator(Activity activity, TabHost tabHost) {
		mTabHost = tabHost;
		mActivity = activity;
		mTabNames = new ArrayList<String>();
		mTabContentResourceIds = new ArrayList<Integer>();
	}
	
	
	public void addTab(String tabName, int contentId) {
		mTabNames.add(tabName);
		mTabContentResourceIds.add(contentId);
	}
	
	public void configureTabs() {
		int height = mActivity.getResources().getDimensionPixelSize(R.dimen.tabHeight);
		int displayWidth = mActivity.getWindowManager().getDefaultDisplay().getWidth();
		int width = displayWidth/mTabNames.size();
		
		int remainingWidth = displayWidth; // insure the whole width is used in cases where the division rounds off pixels
		
		
		
		for(int i = 0; i < mTabNames.size(); i++) {
			int tabWidth;
			if(i == mTabNames.size() - 1) {
				tabWidth = remainingWidth;
			} else {
				tabWidth = width;
				remainingWidth -= width;
			}
			String tabName = mTabNames.get(i);
			
			// create the content view for the tab
			// we use linearlayout as wrapper view (this is a work around for some strange bug
			// with using 9-patch backgrounds).  In turn we use 9-patch backgrounds because
			// could not get the builtin tab dividers to display
			LinearLayout indicatorView = new LinearLayout(mActivity);
			indicatorView.setLayoutParams(new LayoutParams(tabWidth, height));
			indicatorView.setBackgroundResource(R.drawable.tab2_background);
			indicatorView.setGravity(Gravity.CENTER);
			TextView textView = new TextView(mActivity);
			textView.setLayoutParams(new LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));
			textView.setText(tabName);
			ColorStateList colors = mActivity.getResources().getColorStateList(R.color.tab_text_color);
			textView.setTextColor(colors);
			textView.setTextSize(mActivity.getResources().getDimensionPixelSize(R.dimen.tabTextSize));
			textView.setTypeface(Typeface.SANS_SERIF);
			indicatorView.addView(textView);
			
			TabSpec tabSpec = mTabHost.newTabSpec(tabName);
			tabSpec.setIndicator(indicatorView);
			tabSpec.setContent(mTabContentResourceIds.get(i)); // ERROR null until AFTER addTab?
			mTabHost.addTab(tabSpec);
		}	
	}

}
