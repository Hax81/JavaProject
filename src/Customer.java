public class Customer { //skapar här Customer-klassen enligt ERD
    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String adress;
    private String password;


    public Customer(int customerId, String name, String email, String phone, String adress, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

    public String getPassword() {
        return password;
    }


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


