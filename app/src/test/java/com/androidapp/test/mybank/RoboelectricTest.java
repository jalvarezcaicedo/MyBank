package com.androidapp.test.mybank;


import com.androidapp.test.mybank.data.model.login.Login;
import com.androidapp.test.mybank.ui.container.ContainerActivity;
import com.androidapp.test.mybank.ui.login.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.subscribers.TestSubscriber;

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

    @Test
    public void testLoginCall() {
        TestSubscriber<Login> testSubscriber = new TestSubscriber<>();


    }
    /*

    @InjectMocks
    LoginDataManager loginDataManager;
    @Mock
    LoginView loginView;
    @Mock
    RetrofitService retrofitService;

    private LoginPresenter loginPresenter;

    @Before
    public void prepareDataForTest() {
        MockitoAnnotations.initMocks(LoginPresenterTest.this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(mScheduler -> Schedulers.trampoline());
        loginPresenter = new LoginPresenter(loginDataManager);
        loginPresenter.attachView(loginView);
    }

public void pruebaRetrofit() {
        DocumentTypeRequest documentTypeRequest = new DocumentTypeRequest();
        documentTypeRequest.setOpList(Constants.KEY_DOCUMENT_OP_LIST);

        Mockito.when(retrofitService.getDocumentTypes(documentTypeRequest)).thenReturn(
                Observable.just(obtenerDocumentResponse()));

        TestObserver<DocumentResponse> testObserver =
                retrofitService.getDocumentTypes(documentTypeRequest).test();

        testObserver.assertNoErrors().assertValue(documentResponse1 -> documentResponse1.getDocumentList().size() == 3);
    }

private DocumentResponse obtenerDocumentResponse() {
        DocumentResponse documentResponse = new DocumentResponse();

        DocumentResponse.DocumentBody body = new DocumentResponse.DocumentBody();

        DocumentResponse.DocumentBody.ListResponse listResponse = new DocumentResponse.DocumentBody.ListResponse();

        DocumentResponse.DocumentBody.ListResponse.DocumentListResponse documentListResponse = new
                DocumentResponse.DocumentBody.ListResponse.DocumentListResponse();

        List<Document> documentList = new ArrayList<>();
        Document document = new Document();
        document.setDescription("Documento1");
        document.setId(1);
        document.setName("Nombre1");
        documentList.add(document);
        document = new Document();
        document.setDescription("Documento2");
        document.setId(2);
        document.setName("Nombre2");
        documentList.add(document);
        document = new Document();
        document.setDescription("Documento3");
        document.setId(3);
        document.setName("Nombre3");
        documentList.add(document);

        documentListResponse.setDocumentList(documentList);
        listResponse.setDocumentListResponse(documentListResponse);
        body.setListResponse(listResponse);
        documentResponse.setBody(body);

        return documentResponse;
    }

     */

}
