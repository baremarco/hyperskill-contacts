package contacts;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String[] args = {"phonebook.db"};

        Scanner scanner = new Scanner(System.in);
        String type;
        String name;
        String surname;
        String birthday;
        String gender;
        String phoneNumber;
        String businessName;
        String businessAddress;
        String field;
        String data;
        int index;
        boolean isLooping = true;
        String searchQuery;
        String fileName = args.length != 0 ? args[0] : null;
        Agenda agenda = loadAgenda(fileName);

        while (isLooping) {
//            System.out.println();
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            String command = scanner.nextLine();
            try {

                switch (command) {
                    case "add":
                        System.out.println("Enter the type (person, organization):");
                        type = scanner.nextLine();
                        if (type.equals("person")) {
                            System.out.println("Enter the name:");
                            name = scanner.nextLine();

                            System.out.println("Enter the surname:");
                            surname = scanner.nextLine();

                            System.out.println("Enter the birth date:");
                            birthday = scanner.nextLine();
                            if (birthday.equals("")) {
                                System.out.println("Bad birth date!");
                            }

                            System.out.println("Enter the gender (M, F):");
                            gender = scanner.nextLine();
                            if (gender.equals("") || !gender.equals("M") || !gender.equals("F")) {
                                System.out.println("Bad gender!");
                            }

                            System.out.println("Enter the number:");
                            phoneNumber = scanner.nextLine();

                            PersonalRecord personalRecord = new PersonalRecord(phoneNumber, name, surname, birthday, gender);

                            agenda.addRegister(personalRecord, fileName);
                        } else if (type.equals("organization")) {
                            System.out.println("Enter the organization name:");
                            businessName = scanner.nextLine();

                            System.out.println("Enter the address:");
                            businessAddress = scanner.nextLine();

                            System.out.println("Enter the number:");
                            phoneNumber = scanner.nextLine();

                            CorporateRecord corporateRecord = new CorporateRecord(businessName, businessAddress, phoneNumber);
                            agenda.addRegister(corporateRecord, fileName);
                        }
                        System.out.println();
                        break;
                    case "search":
                        String firtsMenuSelection;
                        String secondMenuSelection;
                        String fieldToEdit;
                        String valueForUpdate;
                        outersearchloop:
                        while (true) {
                            System.out.println("Enter search query:");
                            searchQuery = scanner.nextLine();
                            agenda.search(searchQuery);
                            System.out.println("[search] Enter action ([number], back, again):");
                            firtsMenuSelection = scanner.nextLine();
                            try {
                                if ("again".equalsIgnoreCase(firtsMenuSelection) || firtsMenuSelection.isBlank()) {
                                    continue;
                                } else if ("back".equalsIgnoreCase(firtsMenuSelection)) {
                                    break;
                                }
                                agenda.detailedMatchedInfo(Integer.parseInt(firtsMenuSelection));
                                while (true) {
                                    if (agenda.isPersonal(Integer.parseInt(firtsMenuSelection))) {
                                        System.out.println("[record] Enter action (edit, delete, menu):");
                                        secondMenuSelection = scanner.nextLine();
                                        if ("edit".equalsIgnoreCase(secondMenuSelection)) {
                                            System.out.println("Select a field (name, surname, birth, gender, number):");
                                            fieldToEdit = scanner.nextLine();
                                            System.out.println("Enter " + fieldToEdit + ":");
                                            valueForUpdate = scanner.nextLine();
                                            agenda.editMatched(Integer.parseInt(firtsMenuSelection),
                                                    fieldToEdit,
                                                    valueForUpdate,
                                                    fileName);
                                        } else if ("menu".equalsIgnoreCase(secondMenuSelection)) {
                                            break;
                                        }
                                    }
                                    System.out.println("[record] Enter action (edit, delete, menu):");
                                    secondMenuSelection = scanner.nextLine();
                                    if ("edit".equalsIgnoreCase(secondMenuSelection)) {
                                        System.out.println("Select a field (name, address, number):");
                                        fieldToEdit = scanner.nextLine();
                                        System.out.println("Enter " + fieldToEdit + ":");
                                        valueForUpdate = scanner.nextLine();
                                        agenda.editMatched(Integer.parseInt(firtsMenuSelection),
                                                fieldToEdit,
                                                valueForUpdate,
                                                fileName);
                                    } else if ("menu".equalsIgnoreCase(secondMenuSelection)) {
                                        break outersearchloop;
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                    case "remove":
                        if (agenda.getRegistersCount() != 0) {
                            agenda.info();
                            System.out.println("Select a record");
                            index = Integer.parseInt(scanner.nextLine());
                            agenda.remove(index);
                        } else {
                            System.out.println("No records to remove");
                        }
                        break;
                    case "edit":
                        if (agenda.getRegistersCount() != 0) {
                            agenda.info();
                            System.out.println("Select a record");
                            index = Integer.parseInt(scanner.nextLine());
                            System.out.println("Select a field (name, address, number):");
                            field = scanner.nextLine();
                            System.out.println("Enter " + field + ":");
                            data = scanner.nextLine();
                            agenda.edit(index, field, data, fileName);

                        } else {
                            System.out.println("No records to edit");
                        }
                        break;
                    case "count":
                        agenda.count();
                        System.out.println();
                        break;
                    case "list":
                        agenda.info();
                        System.out.println();
                        System.out.println("[list] Enter action ([number], back):");
                        firtsMenuSelection = scanner.nextLine();
                        if (firtsMenuSelection.equalsIgnoreCase("back")) {
                            break;
                        }
                        //nuevo
                        agenda.detailedInfo(Integer.parseInt(firtsMenuSelection));
                        System.out.println();
                        while (true) {
                            if (agenda.isPersonal(Integer.parseInt(firtsMenuSelection))) {
                                System.out.println("[record] Enter action (edit, delete, menu):");
                                secondMenuSelection = scanner.nextLine();
                                if ("edit".equalsIgnoreCase(secondMenuSelection)) {
                                    System.out.println("Select a field (name, surname, birth, gender, number):");
                                    fieldToEdit = scanner.nextLine();
                                    System.out.println("Enter " + fieldToEdit + ":");
                                    valueForUpdate = scanner.nextLine();
                                    agenda.edit(Integer.parseInt(firtsMenuSelection),
                                            fieldToEdit,
                                            valueForUpdate,
                                            fileName);
                                } else if ("menu".equalsIgnoreCase(secondMenuSelection)) {
                                    break;
                                }
                            }
                            System.out.println("[record] Enter action (edit, delete, menu):");
                            secondMenuSelection = scanner.nextLine();
                            if ("edit".equalsIgnoreCase(secondMenuSelection)) {
                                System.out.println("Select a field (name, address, number):");
                                fieldToEdit = scanner.nextLine();
                                System.out.println("Enter " + fieldToEdit + ":");
                                valueForUpdate = scanner.nextLine();
                                agenda.edit(Integer.parseInt(firtsMenuSelection),
                                        fieldToEdit,
                                        valueForUpdate,
                                        fileName);
                            } else if ("menu".equalsIgnoreCase(secondMenuSelection)) {
                                break;
                            }
                        }
                        //nuevo
                        break;
                    case "exit":
                        isLooping = false;
                        break;
                    default:
                        throw new Exception("Bad Parameter");
                }
            } catch (Exception e) {
                System.out.println("An error occurs of type:" + e.getMessage());
                isLooping = false;
            }
        }
    }

    /**
     * Load the Agenda in the file, file name inside args else return a new Agenda
     *
     * @param fileName file name to load.
     * @return Agenda
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Agenda loadAgenda(String fileName) throws IOException, ClassNotFoundException {
        if (fileName == null) {
            return new Agenda();
        }
        File file = new File(fileName);
        if (fileName != null && file.exists()) {
            System.out.println("open " + fileName);
            System.out.println();
            return (Agenda) SerializationUtils.deserialize(fileName);
        }
        return new Agenda();
    }
}
