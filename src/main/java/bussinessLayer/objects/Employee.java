package bussinessLayer.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    public enum Type {
        ZAMESTNANEC,
        MANAGER,
        MAJITEL
    }

    private final SimpleIntegerProperty employeeID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty birthDate;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty endDate;
    private final Type position;
    private final SimpleStringProperty password;

    public Employee(Integer employeeID, String firstName) {
        this(employeeID, firstName,"",null,null,null,null,null,null,null);
    }

    public Employee(Integer employeeID, String firstName, String lastName, String address, String phoneNumber, String birthDate, String startDate, String endDate, Type position, String password) {
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.birthDate = new SimpleStringProperty(birthDate);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.position = position;
        this.password = new SimpleStringProperty(password);
    }

    public Integer getEmployeeID() {
        return employeeID.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public String getStartDate() {
        return startDate.get();
    }

    public String getEndDate() {
        return endDate.get();
    }

    public Type getPosition() {
        return position;
    }

    public String getPassword() {
        return password.get();
    }

    @Override
    public String toString() {
        return firstName.get() +" "+ lastName.get();
    }
}
