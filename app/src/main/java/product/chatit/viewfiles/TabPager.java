package product.chatit.viewfiles;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import product.chatit.calllogdesc.CallLogs;
import product.chatit.chatdesc.Chats;
import product.chatit.contactdesc.Contacts;

public class TabPager extends FragmentStatePagerAdapter {

        int tabCount;
        private String[] tabTitles=new String[]{"Calls","Contacts","Chats"};

        public TabPager(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

            public CharSequence getPageTitle(int position){
                return tabTitles[position];
            }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    CallLogs callLogs = new CallLogs();
                    return callLogs;
                case 1:
                    Contacts contacts = new Contacts();
                    return contacts;
                case 2:
                    Chats chats=new Chats();
                    return chats;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }



