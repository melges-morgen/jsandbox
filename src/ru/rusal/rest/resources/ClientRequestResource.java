package ru.rusal.rest.resources;

import org.apache.log4j.Logger;
import ru.rusal.RequestHandler;
import ru.rusal.exceptions.InvalidUserException;
import ru.rusal.rest.elements.XmlRequest;
import ru.rusal.rest.elements.XmlResponse;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by melges on 04.11.14.
 */
@Path("/")
public class ClientRequestResource {
    private final static Logger logger = Logger.getLogger(ClientRequestResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public XmlResponse processXmlRequest(@Valid XmlRequest request) {
        try {
            switch(request.requestType) {
                case NewCustomer:
                    return new XmlResponse(RequestHandler.registerUser(request.login, request.password));
                case CustomerPayment:
                    RequestHandler.submitCustomerPayment(request.login, request.password, request.payment);
                    return new XmlResponse(0);
                case CustomerBalance:
                    return new XmlResponse(0, RequestHandler.getCustomerBalance(request.login, request.password));
                default:
                    return new XmlResponse(0);

            }
        } catch (InvalidUserException e) {
            return new XmlResponse(3);
        } catch (Throwable e) {
            logger.error("Error while work on request", e);
            return new XmlResponse(5);
        }
    }
}

