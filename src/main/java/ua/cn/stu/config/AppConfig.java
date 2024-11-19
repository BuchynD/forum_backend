package ua.cn.stu.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class AppConfig extends ResourceConfig {
	public AppConfig() {
        // Register your resources here
        packages("ua.cn.stu.service"); // Adjust to your resources package
        property(ServerProperties.WADL_FEATURE_DISABLE, true); // Disable WADL
    }
}
