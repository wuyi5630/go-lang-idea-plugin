package ro.redeul.google.go.lang.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.IdeaTestCase;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.fixtures.*;
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ro.redeul.google.go.GoFileType;
import ro.redeul.google.go.util.TestUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public abstract class GoCompletionTestBase extends UsefulTestCase {

    protected JavaCodeInsightTestFixture fixture;
    protected Module module;
    protected String testCaseName;

    protected GoCompletionTestBase() {
        IdeaTestCase.initPlatformPrefix();
    }

    @BeforeMethod
    public void before(Method m) throws Exception {
        testCaseName = m.getName();

        IdeaTestFixtureFactory factory = IdeaTestFixtureFactory.getFixtureFactory();
        TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder = factory.createLightFixtureBuilder(getProjectDescriptor());

        final IdeaProjectTestFixture fixture = fixtureBuilder.getFixture();

        this.fixture = JavaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(
                fixture, new LightTempDirTestFixtureImpl(true));

        this.fixture.setUp();
        this.fixture.setTestDataPath(getTestDataPath());

        module = this.fixture.getModule();
    }

    @AfterMethod
    public void after(Method m) throws Exception {
        testCaseName = null;

        this.fixture.tearDown();
    }

    public static final LightProjectDescriptor JAVA_1_5 = new DefaultLightProjectDescriptor() {
        @Override
        public void configureModule(Module module, ModifiableRootModel model, ContentEntry contentEntry) {
            super.configureModule(module, model, contentEntry);
        }
    };


    protected LightProjectDescriptor getProjectDescriptor() {        
        return JAVA_1_5;
    }

    protected String getTestsBasePath() {
        return "completion" + File.separator + "go";
    }

    protected String getTestsRelativePath() {
        return File.separator;
    }
    
    protected String getTestDataPath() {
        return getTestDataRoot() + File.separator + getTestsBasePath();
    }

    protected String getTestDataRoot() {
        return "testdata";
    }


    public String getTestName() {
        String name = testCaseName;
        name = StringUtil.trimStart(name, "test");
        if (StringUtil.isEmpty(name)) {
          return "";
        }
        
        return name;
    }

    protected void doBasicTest() {
        String testName = getTestsRelativePath() + getTestName();
        fixture.testCompletion(testName + ".go", testName + "_after.go");
    }

    protected void doSmartTest() {
        fixture.configureByFile(getTestName() + ".go");
        fixture.complete(CompletionType.SMART);
        fixture.checkResultByFile(getTestName() + "_after.go", true);
    }

    public void doSmartCompletion(String... variants) throws Exception {
        fixture.configureByFile(getTestName() + ".go");
        fixture.complete(CompletionType.SMART);
        final List<String> list = fixture.getLookupElementStrings();

        Assert.assertNotNull(list);
        UsefulTestCase.assertSameElements(list, variants);
    }

    public void doVariantsTest(String... variants) throws Throwable {
        fixture.configureByFile(getTestName(false) + ".go");
        fixture.complete(CompletionType.BASIC);


        assertOrderedEquals(fixture.getLookupElementStrings(), variants);
    }

    public void doTest() throws Exception {
        final List<String> data = TestUtils.readInput(getTestDataPath() + getTestName(true) + ".test");

        fixture.configureByText(GoFileType.GO_FILE_TYPE, data.get(0));

//       final List<SmartEnterProcessor> processors = getSmartProcessors(GroovyFileType.GROOVY_LANGUAGE);
//       new WriteCommandAction(getProject()) {
//         protected void run(Result result) throws Throwable {
//           final Editor editor = myFixture.getEditor();
//           for (SmartEnterProcessor processor : processors) {
//             processor.process(getProject(), editor, myFixture.getFile());
//           }
//
//         }
//       }.execute();
        fixture.checkResult(data.get(1));
    }

}
