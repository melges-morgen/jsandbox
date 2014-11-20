package ru.rusal.rest.elements;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

/**
 * Created by melges on 05.11.14.
 */
@XmlRootElement(name = "request")
public class XmlRequest {
    @XmlType(name = "request-type")
    @XmlEnum
    public enum RequestType {
        @XmlEnumValue("new-customer")
        NewCustomer,
        @XmlEnumValue("customer-payment")
        CustomerPayment,
        @XmlEnumValue("customer-balance")
        CustomerBalance;
    }

    @NotNull
    @XmlElement(name = "request-type", required = true, nillable = false)
    public RequestType requestType;

    @XmlElement(required = true, name = "login")
    public String login;

    @XmlElement(required = true, name = "password")
    public String password;

    @XmlElement(required = false, name = "payment")
    public double payment;
}
