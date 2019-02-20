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

package lcs.fcmtest.wizard.models;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import lcs.fcmtest.wizard.ui.ChildrenInfoFragment;

/**
 * A page asking for a name and an email.
 */
public class ChildrenInfoPage extends Page {
    public static final String NAME_DATA_KEY = "name";
    public static final String EMAIL_DATA_KEY = "email";
    public static final String AUTHORIZED_EMAIL_DATA_KEY = "authorized_email";

    public ChildrenInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ChildrenInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Your name", mData.getString(NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Your email", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Authorized email", mData.getString(AUTHORIZED_EMAIL_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return  !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(AUTHORIZED_EMAIL_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(EMAIL_DATA_KEY));
    }
}
