package top.crossoverjie.cicada.base.bean;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/14 01:06
 * @since JDK 1.8
 */
public interface CicadaBeanFactory {

    /**
     * Register into bean Factory
     * @param object
     */
    void register(Object object);

    /**
     * Get bean from bean Factory
     * @param name
     * @return
     * @throws Exception
     */
    Object getBean(String name) throws Exception;

    /**
     * release all beans
     */
    void releaseBean() ;
}
