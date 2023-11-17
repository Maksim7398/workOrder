package org.example.app.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ShutDownHookConf implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
