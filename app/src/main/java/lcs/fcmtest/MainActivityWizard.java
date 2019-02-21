/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lcs.fcmtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


import java.util.List;

import lcs.fcmtest.database.DatabaseDAO;
import lcs.fcmtest.model.Children;
import lcs.fcmtest.model.InfoAboutInstalledApps;
import lcs.fcmtest.model.Parent;
import lcs.fcmtest.model.Person;
import lcs.fcmtest.services.GetListOfInstalledApps;
import lcs.fcmtest.utils.Constants;
import lcs.fcmtest.utils.Utils;
import lcs.fcmtest.wizard.models.AbstractWizardModel;
import lcs.fcmtest.wizard.models.ModelCallbacks;
import lcs.fcmtest.wizard.models.Page;
import lcs.fcmtest.wizard.models.ReviewItem;
import lcs.fcmtest.wizard.ui.PageFragmentCallbacks;
import lcs.fcmtest.wizard.ui.ReviewFragment;
import lcs.fcmtest.wizard.ui.StepPagerStrip;

public class MainActivityWizard extends FragmentActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new SandwichWizardModel(this);

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    private boolean isChildren=true;
    private boolean isParent=false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Leandro", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Utils.setTokenPreference(getApplication(), token);
                    }
                });
        //if (!Utils.getRolePreference(this).equals(""))
          //  return;
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
//                    DialogFragment dg = new DialogFragment() {
//                        @Override
//                        public Dialog onCreateDialog(Bundle savedInstanceState) {
//                            return new AlertDialog.Builder(getActivity())
//                                    .setMessage(R.string.submit_confirm_message)
//                                    .setPositiveButton(R.string.submit_confirm_button, null)
//                                    .setNegativeButton(android.R.string.cancel, null)
//                                    .create();
//                        }
//                    };

//                    dg.show(getSupportFragmentManager(), "place_order_dialog");
                   // Intent i = new Intent(getApplicationContext(), BlockAppsActivity.class);
                    //startActivity(i);
                    Page currentPage = mCurrentPageSequence.get(mCurrentPageSequence.size() -1);
                    Bundle bundle = currentPage.getData();
                    String key = currentPage.getKey().split(":")[0];
                    Person person = null;
                    Intent intent;
                    Utils.setNamePreference(getApplicationContext(), bundle.getString(Constants.NAME_DATA_KEY));
                    if ("Children".equals(key)) {
                        // DO kid stuff
                        person = new Children(bundle.getString(Constants.NAME_DATA_KEY), bundle.getString(Constants.EMAIL_DATA_KEY),
                                Utils.getTokenPreference(getApplicationContext()),bundle.getString(Constants.AUTHORIZED_EMAIL_DATA_KEY));
                        Utils.setRolePreference(getApplicationContext(), "children");
                        intent = new Intent(getApplicationContext(),ChildrenQRCodeActivity.class);
                        intent.putExtra(Constants.EMAIL_DATA_KEY, person.getEmail().split("@")[0]);

                    } else {
                        //Do adult stuff
                        person = new Parent(bundle.getString(Constants.NAME_DATA_KEY), bundle.getString(Constants.EMAIL_DATA_KEY),
                                Utils.getTokenPreference(getApplicationContext()), null);
                        Utils.setRolePreference(getApplicationContext(), "parent");
                        intent = new Intent(getApplicationContext(),ParentMainActivity.class);
                    }
                    Utils.setUserPreference(getApplicationContext(),person.getEmail().split("@")[0]);
                    DatabaseDAO.getInstance().savePerson(getApplicationContext(),person);

                    if (Utils.getRolePreference(getApplicationContext()).equals("children")) {
                        GetListOfInstalledApps getListOfInstalledApps = new GetListOfInstalledApps(getApplicationContext(),
                                new GetListOfInstalledApps.AsyncResponse() {
                                    @Override
                                    public void processFinish(List<InfoAboutInstalledApps> output) {
                                        DatabaseDAO.getInstance().addAppList(getApplicationContext(),
                                                Utils.getUserPreference(getApplicationContext()),
                                                output);
                                    }
                                });
                        getListOfInstalledApps.execute();
                    }
                    startActivity(intent);
                    MainActivityWizard.this.finish();
                    Log.d("Leandro",bundle.toString());


                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
