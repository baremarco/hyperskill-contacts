package contacts;

import java.time.LocalDate;

public class PersonalRecord extends Record {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String gender;

    private static final long serialVersionUID = 42L;

    public PersonalRecord(String phoneNumber, String name, String surname, String birthDate, String gender) {
        super(phoneNumber);
        this.name = name;
        this.surname = surname;
        if (!birthDate.isBlank()) {
            this.birthDate = LocalDate.parse(birthDate);

        } else {
            this.birthDate = null;

        }

        if (!gender.isBlank()) {
            this.gender = gender;
        } else {
            this.gender = null;
        }
    }

    @Override
    String getInfo() {
        return this.name + " " + this.surname;
    }

    @Override
    void detailedInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println(this.birthDate == null ? "Birth date: [no data]" : "Birth date: " + this.birthDate);
        System.out.println(this.gender == null ? "Gender: [no data]" : "Gender: " + this.gender);
        System.out.println("Number: " + this.phoneNumber);
        System.out.println("Time created: " + super.getCreationDate());
        System.out.println("Time last edit: " + super.getLastUpdateDate());
    }

    @Override
    void edit(String field, String data) throws Exception {
        switch (field) {
            case "name":
                this.name = data;
                this.setLastUpdateDate();
                break;
            case "surname":
                this.surname = data;
                this.setLastUpdateDate();
                break;
            case "birth":
                this.birthDate = LocalDate.parse(data);
                this.setLastUpdateDate();
                break;
            case "gender":
                this.gender = data;
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
        return new StringBuilder(this.gender == null ? " " : this.gender)
                .append(this.name == null ? " " : this.name)
                .append(this.surname == null ? " " : this.surname)
                .append(this.phoneNumber == null ? " " : this.phoneNumber)
                .append(this.birthDate == null ? " " : this.birthDate)
                .toString();
    }
}
