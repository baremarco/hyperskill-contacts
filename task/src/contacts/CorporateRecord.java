package contacts;

public class CorporateRecord extends Record {
    private String businessName;
    private String businessAddress;

    private static final long serialVersionUID = 42L;

    public CorporateRecord(String businessName, String businessAddress, String phoneNumber) {
        super(phoneNumber);
        this.businessName = businessName;
        this.businessAddress = businessAddress;
    }


    @Override
    String getInfo() {
        return this.businessName;
    }

    @Override
    void detailedInfo() {
        System.out.println("Organization name: " + this.businessName);
        System.out.println("Address: " + this.businessAddress);
        System.out.println("Number: " + this.phoneNumber);
        System.out.println("Time created: " + super.getCreationDate());
        System.out.println("Time last edit: " + super.getLastUpdateDate());
    }

    @Override
    void edit(String field, String data) throws Exception {
        switch (field) {
            case "name":
                this.businessName = data;
                this.setLastUpdateDate();
                break;
            case "address":
                this.businessAddress = data;
                this.setLastUpdateDate();
                break;
            case "number":
                this.phoneNumber = data;
                this.setLastUpdateDate();
                break;
            default:
                throw new Exception("Bad field");
        }
    }

    @Override
    String getAppendedData() {
        return new StringBuilder(this.businessAddress)
                .append(this.businessName)
                .append(this.phoneNumber)
                .toString();
    }
}
