package bussinessLayer.services;

import bussinessLayer.objects.Payment;
import dataLayer.gateway.PaymentGateway;

public class PaymentService {
    public boolean insertPayment(Payment.Type type, double tableID, int employeeID) {
        return PaymentGateway.createPayment(type.ordinal(), tableID, employeeID);
    }
}
