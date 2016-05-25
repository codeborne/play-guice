package play.modules.guice;

import org.junit.Test;
import play.mvc.Controller;
import play.mvc.Http;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuicePluginTest {
  @Test
  public void createNonStaticControllerInstance() throws Exception {
    Http.Request.current.set(new Http.Request());
    NonStaticController controllerBean = new NonStaticController();

    GuicePlugin plugin = new GuicePlugin() {
      @Override public <T> T getBeanOfType(Class<T> clazz) {
        return (T) controllerBean;
      }
    };
    plugin.beforeActionInvocation(NonStaticController.class.getMethod("hello"));

    assertThat(Http.Request.current().controllerInstance, is(controllerBean));
  }

  static class NonStaticController extends Controller {
    public void hello() {}
  }
}