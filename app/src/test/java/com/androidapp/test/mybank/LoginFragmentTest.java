package com.androidapp.test.mybank;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.androidapp.test.mybank.ui.container.ContainerActivity;
import com.androidapp.test.mybank.ui.login.LoginFragment;
import com.androidapp.test.mybank.ui.menu.MenuFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginFragmentTest {

    private Button btnLogin;
    private ContainerActivity containerActivity;
    private Fragment loginFragment;
    private Fragment menuFragment;
    private View view;

    @Before
    public void setUp() {
        containerActivity = Robolectric.buildActivity(ContainerActivity.class).create().resume().get();
        loginFragment = containerActivity.getSupportFragmentManager().findFragmentByTag(LoginFragment.FRAGMENT_TAG);
        menuFragment = containerActivity.getSupportFragmentManager().findFragmentByTag(MenuFragment.FRAGMENT_TAG);

        view = loginFragment.getView();
        btnLogin = view.findViewById(R.id.btn_login);
    }

    @Test
    public void loginSuccess() {
        btnLogin.performClick();
        assertNotNull(containerActivity.getSupportFragmentManager().findFragmentByTag(MenuFragment.FRAGMENT_TAG));
    }

}
