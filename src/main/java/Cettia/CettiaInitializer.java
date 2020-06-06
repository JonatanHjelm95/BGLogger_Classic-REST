/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cettia;

import io.cettia.DefaultServer;
import io.cettia.asity.bridge.jwa1.AsityServerEndpoint;
import io.cettia.asity.bridge.servlet3.AsityServlet;
import io.cettia.transport.http.HttpTransportServer;
import io.cettia.transport.websocket.WebSocketTransportServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.DeploymentException;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author jonab
 */
@WebListener
public class CettiaInitializer implements ServletContextListener {

    io.cettia.Server server = new DefaultServer();
    io.cettia.transport.http.HttpTransportServer hts = new HttpTransportServer().ontransport(server);
    io.cettia.transport.websocket.WebSocketTransportServer wts = new WebSocketTransportServer().ontransport(server);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        Servlet asityServlet = new AsityServlet().onhttp(hts);
        ServletRegistration.Dynamic reg = context.addServlet(AsityServlet.class.getName(), asityServlet);
        reg.setAsyncSupported(true);
        reg.addMapping("/cettia");

        ServerContainer container = (ServerContainer) context.getAttribute(ServerContainer.class.getName());
        ServerEndpointConfig.Configurator configurator = new ServerEndpointConfig.Configurator() {
            public <T> T getEndpointInstance(Class<T> endpointClass) {
                AsityServerEndpoint asityServerEndpoint = new AsityServerEndpoint().onwebsocket(wts);
                return endpointClass.cast(asityServerEndpoint);
            }
        };
        try {
            container.addEndpoint(ServerEndpointConfig.Builder.create(AsityServerEndpoint.class, "/cettia").configurator(configurator).build());
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } catch (javax.websocket.DeploymentException ex) {
            Logger.getLogger(CettiaInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
