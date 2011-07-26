/*****************************************************************************
 *  Copyright 2011 Alex Starchenko (sandrstar at hotmail dot com)            *
 *****************************************************************************/

/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by/3.0/
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA.
 */

package com.sandrstar.android.mathwidgetstest;

import android.util.Log;

public class MathWidgetsTest extends android.test.ActivityInstrumentationTestCase2<MathWidgetsTestActivity> {
    @SuppressWarnings("unused")
    private MathWidgetsTestActivity mActivity = null;
    private final static String TAG = MathWidgetsTest.class.getSimpleName();
    enum MathWidgetsTestStatus {
        COMPLETE,
        ERROR
    };

    // Main Constructor
    public MathWidgetsTest() {
        super("com.sandrstar.android.mathwidgetstest", MathWidgetsTestActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        Log.i(TAG, "setUp(): started");
        super.setUp();
        // start test activity in order to let it
        mActivity = this.getActivity();
    }

    public void testPreConditions() {
        Log.i(TAG, "testPreconditions(): no preconditions currently to be tested");
    }

    @Override
    protected void tearDown () {

        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
