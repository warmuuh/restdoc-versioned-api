package wrm;

import java.util.LinkedList;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathsRule implements TestRule {

    ServletApiPath curApiPath;
    List<ServletApiPath> paths = new LinkedList<PathsRule.ServletApiPath>();
    
    @AllArgsConstructor @ToString
    public static class ServletApiPath {
        @Getter String servletPath;
        
        public String getPath(){
            return "/" + servletPath + "/";
        }
    }
    
    
    public String getPath() {
        return curApiPath.getPath();
    }
    
    public String getServletPath() {
        return "/" + curApiPath.getServletPath();
    }


    
    /**
     * 
     * @param apis array of comma-separated values, e.g. "appapi, v1_2" or "webapi" (no comma for that)
     */
    public PathsRule(String... apis) {
        for (String api : apis) {
            paths.add(new ServletApiPath(api));
        }
    }


    @Override
    public Statement apply(final Statement base, final Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                for (ServletApiPath p : paths){
                    curApiPath  = p;
                    log.info("Using {}", curApiPath);
                    base.evaluate();
                }
            }
        };
    }

}
