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

import android.content.Context;

import lcs.fcmtest.wizard.models.AbstractWizardModel;
import lcs.fcmtest.wizard.models.BranchPage;
import lcs.fcmtest.wizard.models.ChildrenInfoPage;
import lcs.fcmtest.wizard.models.PageList;
import lcs.fcmtest.wizard.models.ParentInfoPage;


public class SandwichWizardModel extends AbstractWizardModel {
    public SandwichWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new BranchPage(this, "Credential:")
                        .addBranch("Children",
                                new ChildrenInfoPage(this, "Your info")
                                        .setRequired(true)
                        )

                        .addBranch("Parent",
                               new ParentInfoPage(this, "Your info")
                                        .setRequired(true)

                        )

                        .setRequired(true)


        );
    }
}
