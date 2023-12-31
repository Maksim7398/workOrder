package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.web.model.Client;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    Logger logger = Logger.getLogger(IdProvider.class);
    public String provideId(Client client) {
        return this.hashCode() + "_" + client.hashCode();

    }

    private void initIdProvider() {
        logger.info("provider INIT");
    }

    private void destroyIdProvider() {
        logger.info("provider DESTROY");
    }

    private void defaultInit(){
        logger.info("default INIT in provider");

    }
    private void defaultDestroy(){
        logger.info("default DESTROY in provider");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("provider afterPropertiesSet invoked");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("DisposibleBean destroy invoked");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBeforeInitialization invoke by bean " + beanName);
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAfterInitialization invoke by bean " + beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
    @PostConstruct
    public void postConstructIdProvider(){
        logger.info("PostConstruct annotated method called");
    }
    @PreDestroy
    public void preDestroyIdProvider(){
        logger.info("PreDestroy annotated method called");
    }
}
