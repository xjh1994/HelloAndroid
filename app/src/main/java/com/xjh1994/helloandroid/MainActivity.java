package com.xjh1994.helloandroid;

import android.support.v4.app.Fragment;

import com.xjh1994.helloandroid.core.base.activity.tab.BaseTabActivity;

import java.util.ArrayList;

public class MainActivity extends BaseTabActivity {

    @Override
    protected String[] getTitles() {
        return new String[]{"首页", "消息", "发现", "我的"};
    }

    @Override
    protected int[] getIconUnselectIds() {
        return new int[]{R.mipmap.tab_home_unselect, R.mipmap.tab_message_unselect,
                R.mipmap.tab_discover_unselect, R.mipmap.tab_person_unselect};
    }

    @Override
    protected int[] getIconSelectIds() {
        return new int[]{R.mipmap.tab_home_select, R.mipmap.tab_message_select,
                R.mipmap.tab_discover_select, R.mipmap.tab_person_select};
    }

    @Override
    protected void addFragments(ArrayList<Fragment> fragments) {
        fragments.add(new SampleFragment());
        fragments.add(new SampleFragment());
        fragments.add(new SampleFragment());
        fragments.add(new SampleFragment());
    }
}
