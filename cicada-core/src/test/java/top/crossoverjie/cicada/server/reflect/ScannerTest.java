package top.crossoverjie.cicada.server.reflect;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScannerTest {


    private static final Logger LOGGER = LoggerBuilder.getLogger(ScannerTest.class) ;

    @Test
    public void getClasses() throws Exception {
        Set<Class<?>> classes = ClassScanner.getClasses("top.crossoverjie.cicada.server");

        LOGGER.info("classes=[{}]", JSON.toJSONString(classes));
    }


    @Test
    public void getActionAction() throws Exception{
        Map<String, Class<?>> cicadaAction = ClassScanner.getCicadaAction("top.crossoverjie.cicada.server");
        LOGGER.info("classes=[{}]", JSON.toJSONString(cicadaAction));
    }


    @Test
    public void getConfiguration() throws Exception {
        List<Class<?>> configuration = ClassScanner.getConfiguration("top.crossoverjie.cicada.server");
        LOGGER.info("configuration=[{}]",configuration.toString());
    }


    @Test
    public void stringTest(){
        String text = "/cicada-example/routeAction/getUser" ;
        text = text.replace("/cicada-example","");
        LOGGER.info(text);
    }



}