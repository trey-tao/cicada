package top.crossoverjie.cicada.server.route;

import io.netty.handler.codec.http.QueryStringDecoder;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.reflect.ClassScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/13 01:48
 * @since JDK 1.8
 */
public class RouterScanner {

    private static Map<String, Method> routes = null;

    private volatile static RouterScanner routerScanner;

    private AppConfig appConfig = AppConfig.getInstance();

    /**
     * get single Instance
     *
     * @return
     */
    public static RouterScanner getInstance() {
        if (routerScanner == null) {
            synchronized (RouterScanner.class) {
                if (routerScanner == null) {
                    routerScanner = new RouterScanner();
                }
            }
        }
        return routerScanner;
    }

    private RouterScanner() {
    }

    /**
     * get route method
     *
     * @param queryStringDecoder
     * @return
     * @throws Exception
     */
    public Method routeMethod(QueryStringDecoder queryStringDecoder) throws Exception {
        if (routes == null) {
            routes = new HashMap<>(16);
            loadRouteMethods(appConfig.getRootPackageName());
        }

        //default response
        boolean defaultResponse = defaultResponse(queryStringDecoder.path());

        if (defaultResponse) {
            return null;
        }

        Method method = routes.get(queryStringDecoder.path());

        if (method == null) {
            throw new CicadaException(StatusEnum.NOT_FOUND);
        }

        return method;


    }

    private boolean defaultResponse(String path) {
        if (appConfig.getRootPath().equals(path)) {
            CicadaContext.getContext().html("<center> Hello Cicada <br/><br/>" +
                    "Power by <a href='https://github.com/TogetherOS/cicada'>@Cicada</a> </center>");
            return true;
        }
        return false;
    }


    private void loadRouteMethods(String packageName) throws Exception {
        Set<Class<?>> classes = ClassScanner.getClasses(packageName);

        for (Class<?> aClass : classes) {
            Method[] declaredMethods = aClass.getMethods();

            for (Method method : declaredMethods) {
                CicadaRoute annotation = method.getAnnotation(CicadaRoute.class);
                if (annotation == null) {
                    continue;
                }

                CicadaAction cicadaAction = aClass.getAnnotation(CicadaAction.class);
                routes.put(appConfig.getRootPath() + "/" + cicadaAction.value() + "/" + annotation.value(), method);
            }
        }
    }
}
