package hu.bets.common.config;

import hu.bets.common.util.EnvironmentVarResolver;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CommonWebConfig {

    private static final String WEB_SERVER_HOST = "HOST";
    private static final String WEB_SERVER_PORT = "PORT";

    @Bean
    public Server server(ServletContextHandler servletContextHandler) {

        Server server = new Server(
                new InetSocketAddress(EnvironmentVarResolver.getEnvVar(WEB_SERVER_HOST),
                        Integer.parseInt(EnvironmentVarResolver.getEnvVar(WEB_SERVER_PORT))));
        server.setHandler(servletContextHandler);

        return server;
    }

    @Bean
    public ResourceConfig resourceConfig(Object... resources) {
        ResourceConfig resourceConfig = new ResourceConfig();
        for (Object resource : resources) {
            resourceConfig.register(resource);
        }
        resourceConfig.register(JacksonFeature.class);

        return resourceConfig;
    }

    @Bean
    public ServletContextHandler servletContextHandler(ServletContainer servletContainer) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder sh = new ServletHolder(servletContainer);

        context.setContextPath("/");
        context.addServlet(sh, "/*");

        return context;
    }

    @Bean
    public ServletContainer servletContainer(ResourceConfig resourceConfig) {
        return new ServletContainer(resourceConfig);
    }
}
