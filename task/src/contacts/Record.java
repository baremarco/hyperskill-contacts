package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Record implements Serializable {
    private static final long serialVersionUID = 42L;
    protected String phoneNumber;
    private LocalDateTime creationDate;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    private LocalDateTime lastUpdateDate;

    abstract String getInfo();

    abstract void detailedInfo();

    abstract void edit(String field, String data) throws Exception;

    abstract String getAppendedData();
    
    public Record(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        setCreationDate();
        setLastUpdateDate();
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        setLastUpdateDate();
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    protected void setLastUpdateDate() {
        this.lastUpdateDate = LocalDateTime.now();
    }

    private void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }
}
