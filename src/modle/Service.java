package modle;

public class Service {
    private String serviceId;
    private String serviceName;
    private double servicePrice;

    public Service(String serviceId, String serviceName, double servicePrice) {
        this.setServiceId(serviceId);
        this.setServiceName(serviceName);
        this.setServicePrice(servicePrice);
    }

    public Service() {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", servicePrice=" + servicePrice +
                '}';
    }
}
