package ru.rusal.rest.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by melges on 05.11.14.
 */
@XmlRootElement(name = "response")
public class XmlResponse {
    @XmlElement(name = "result-code", required = true)
    public int resultCode;

    @XmlElement(required = false)
    public Double balance;

    public XmlResponse() {
        resultCode = 0;
    }

    public XmlResponse(int resultCode) {
        this.resultCode = resultCode;
    }

    public XmlResponse(int resultCode, double balance) {
        this.resultCode = resultCode;
        this.balance = balance;
    }
}
