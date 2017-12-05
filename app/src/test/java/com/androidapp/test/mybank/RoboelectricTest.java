package com.androidapp.test.mybank;


import com.androidapp.test.mybank.ui.container.ContainerActivity;
import com.androidapp.test.mybank.ui.login.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RoboelectricTest {

    private ContainerActivity containerActivity;

    @Before
    public void setUp() throws Exception {
        containerActivity = Robolectric.buildActivity(ContainerActivity.class).create().resume().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(containerActivity);
    }

    @Test
    public void shouldHaveLoginFragment() throws Exception {
        assertNotNull(containerActivity.getSupportFragmentManager().findFragmentByTag(LoginFragment.FRAGMENT_TAG));
    }

}
