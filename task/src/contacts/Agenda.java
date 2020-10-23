package contacts;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Agenda implements Serializable {
    private static final long serialVersionUID = 42L;
    List<Record> registers;
    Map<Integer, Record> matched;

    public Agenda() {
        this.registers = new ArrayList<>();

    }

    public int getRegistersCount() {
        return registers.size();
    }

    public void addRegister(Record register, String fileName) throws IOException {
        this.registers.add(register);
        if (fileName != null) {
            this.save(fileName);
        }
    }

    public void info() {
        showInfoRegister(this.registers);
    }

    static void showInfoRegister(List<Record> records) {
        int index = 0;
        for (Record record : records) {
            index++;
            System.out.println(index + ". " + record.getInfo());
        }
    }

    static void showInfoMap(Map<Integer, Record> indexRecordMap) {
        int index = 1;
        for (Map.Entry<Integer, Record> entry : indexRecordMap.entrySet()) {
            System.out.println(index + ". " + entry.getValue().getInfo());
            index++;
        }
    }

    public void detailedInfo(int index) {
        index--;
        this.registers.get(index).detailedInfo();
    }

    boolean isPersonal(int index) {
        index--;
        boolean personal = this.registers.get(index).getClass()
                .getSimpleName()
                .equals("PersonalRecord");
        return personal;
    }

    public void detailedMatchedInfo(int index) {
        if (this.matched.isEmpty()) {
            System.out.println("Empty Register");
        }
        index--;
        Record record = (Record) this.matched.values().toArray()[index];
        record.detailedInfo();
    }

    public void edit(int index, String field, String data, String fileName) throws Exception {
        if (!registers.isEmpty()) {
            index--;
            this.registers.get(index).edit(field, data);
            this.save(fileName);
            this.detailedInfo(index + 1);
        }
    }

    public void editMatched(int index, String field, String data, String fileName) throws Exception {
        int originalIndex = getOriginalIndex(this.matched, index);
        if (!registers.isEmpty() && originalIndex != -1) {
            registers.get(originalIndex).edit(field, data);
            this.save(fileName);
            this.detailedInfo(originalIndex + 1);
        }
    }

    /**
     * Returns the corresponding index in the registers list
     * @param map
     * @param falseIndex
     * @return Returns the corresponding index in the registers list if not found return -1
     */
    static int getOriginalIndex (Map<Integer, Record> map, int falseIndex) {
        falseIndex--;
        int tempIndex = 0;
        for (Map.Entry<Integer, Record> entry : map.entrySet()) {
            if (tempIndex == falseIndex) {
                return entry.getKey();
            }
            tempIndex++;
        }
        return -1;
    }
    public void count() {
        if (registers.size() != 0) {
            System.out.println("The Phone Book has " + registers.size() + " records.");
        } else {
            System.out.println("The Phone Book has 0 records.");
        }
    }

    public void remove(int index) {
        registers.remove(index);
    }

    public void search(String regex) {
        Map<Integer, Record> indexRecordmatchedRegisters = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        boolean isPresent;
        for (int i = 0; i < this.registers.size(); i++) {
            matcher = pattern.matcher(this.registers.get(i).getAppendedData());
            isPresent = matcher.find();
            if (isPresent) {
                indexRecordmatchedRegisters.put(i, this.registers.get(i));
            }
        }
        this.matched = indexRecordmatchedRegisters;
        System.out.println("Found " + indexRecordmatchedRegisters.size() + " results: ");
        showInfoMap(indexRecordmatchedRegisters);
    }

    private void save(String fileName) throws IOException {
        if (fileName != null) {
            if (!fileName.isBlank()) {
                SerializationUtils.serialize(this, fileName);
                System.out.println("Saved");
                System.out.println();
            }
        }
    }
}
