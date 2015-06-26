package wrm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.RestDocumentationResultHandler;
import org.springframework.restdocs.config.RestDocumentationConfigurer;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class DocumentationTestBase {

    

    @Rule
    public PathsRule paths = new PathsRule("v1", "v2");
    
    
    @Autowired
    private WebApplicationContext context;
    
    protected MockMvc mockMvc;

    protected MockHttpSession session;

    @Before
    public void setUp() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .alwaysDo(RestDocumentation.document("." + paths.getPath()+"{method-name}"))
                .apply(new RestDocumentationConfigurer()).build();
    }
    
    
    protected MockHttpServletRequestBuilder get(String urlTemplate, String...urlVariables){
        return MockMvcRequestBuilders.get(paths.getPath() + urlTemplate, urlVariables);
    }
    protected MockHttpServletRequestBuilder post(String urlTemplate, String...urlVariables){
        return MockMvcRequestBuilders.post(paths.getPath() + urlTemplate, urlVariables);
    }
    
    protected MockMultipartHttpServletRequestBuilder fileUpload(String urlTemplate, String...urlVariables){
        return MockMvcRequestBuilders.fileUpload(paths.getPath() + urlTemplate, urlVariables);
    }
   
    
    public static class NoopResultHandler implements ResultHandler{
        @Override public void handle(MvcResult result) throws Exception {}
    }
    
    
    
    public class ResponseDescriptor {
        List<FieldDescriptor> allFields = new LinkedList<FieldDescriptor>();
      
       
        public ResponseDescriptor v1(FieldDescriptor ... fields){
            if (paths.getServletPath().contains("v1"))
                allFields.addAll(Arrays.asList(fields));
            return this;
        }
        public ResponseDescriptor v2(FieldDescriptor ... fields){
            if (paths.getServletPath().contains("v2"))
                allFields.addAll(Arrays.asList(fields));
            return this;
        }
        
        public ResponseDescriptor common(FieldDescriptor ... fields){
            allFields.addAll(Arrays.asList(fields));
            return this;
        }
        
        public RestDocumentationResultHandler build(){
            return RestDocumentation.document("." + paths.getPath()+"{method-name}")
                    .withResponseFields(allFields.toArray(new FieldDescriptor[]{}));
        }
    }
    
    protected ResponseDescriptor response(){
        return new ResponseDescriptor();
    }
    
 
}

