import java.util.*;
import java.io.*;

 class MarksManagementSystem {
    private static final String FILE_NAME = "marks.csv";
    private static final String DELIMITER = ",";
    
    public static void main(String[] args) {
        List<StudentMarks> studentMarksList = loadStudentMarksFromFile();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. View Marks(Teacher)");
            System.out.println("2. Update Mark(Teacher)");
            System.out.println("3. View Final Result(Student)");
            System.out.println("4. Add Student and Marks(Teacher)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    viewMarks(scanner, studentMarksList);
                    break;
                case 2:
                    updateMark(scanner, studentMarksList);
                    break;
                case 3:
                    publishResult(studentMarksList);
                    break;
                case 4:
                    addStudentMarks(scanner, studentMarksList);
                    break;
                case 5:
                    saveStudentMarksToFile(studentMarksList);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudentMarks(Scanner scanner, List<StudentMarks> studentMarksList) {
        System.out.println("Enter Roll No of student : ");
        int rollNo = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter name of student : ");
        String name = scanner.nextLine();
        System.out.println("Enter marks of Software Engineering : ");
        double softwareEngineering = scanner.nextInt();
        System.out.println("Enter marks of Object Oriented Programming : ");
        double sanskrit = scanner.nextInt();
        System.out.println("Enter marks of Database Mangement System : ");
        double environmentalScience = scanner.nextInt();
        studentMarksList.add(new StudentMarks(rollNo, name, softwareEngineering, sanskrit, environmentalScience));
        System.out.println("Added Student details and marks successfully");
    }

    private static void viewMarks(Scanner scanner, List<StudentMarks> studentMarksList) {
        System.out.println("Enter Student Roll No:");
        int rollNo = scanner.nextInt();
        System.out.println("Printing Marks of Student of Roll No " + rollNo + ": ");
        for (StudentMarks studentMarks : studentMarksList) {
            if (studentMarks.getRollNo() == rollNo) {
                System.out.println(studentMarks);
                break;
            }
        }
    }

    private static void publishResult(List<StudentMarks> studentMarksList) {
        Collections.sort(studentMarksList, new SortByTotalMarks());
        System.out.println("Publishing Result");
        for (StudentMarks studentMarks : studentMarksList) {
            System.out.println(studentMarks);
        }
    }

    private static void updateMark(Scanner scanner, List<StudentMarks> studentMarksList) {
        System.out.println("Enter Student Roll No:");
        int rollNo = scanner.nextInt();
        System.out.println("1.Object Oriented Programming");
        System.out.println("2.Database Management System");
        System.out.println("3.Software Engineering");
        System.out.println("Enter Subject number");
        int subInd = scanner.nextInt();
        System.out.println("Enter updated mark ");
        double updateMark = scanner.nextDouble();
        for (StudentMarks studentMarks : studentMarksList) {
            if (studentMarks.getRollNo() == rollNo) {
                if (subInd == 1) studentMarks.setSoftwareEngineering(updateMark);
                else if (subInd == 2) studentMarks.setSanskrit(updateMark);
                else studentMarks.setEnvironmentalScience(updateMark);
                System.out.println("Updated Successfully");
                break;
            }
        }
    }

    private static List<StudentMarks> loadStudentMarksFromFile() {
        List<StudentMarks> studentMarksList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                studentMarksList.add(new StudentMarks(
                        Integer.parseInt(data[0]), 
                        data[1], 
                        Double.parseDouble(data[2]),
                        Double.parseDouble(data[3]), 
                        Double.parseDouble(data[4])
                ));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading student marks: " + e.getMessage());
        }
        return studentMarksList;
    }

    private static void saveStudentMarksToFile(List<StudentMarks> studentMarksList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (StudentMarks studentMarks : studentMarksList) {
                writer.write(studentMarks.getRollNo() + DELIMITER + 
                             studentMarks.getName() + DELIMITER +
                             studentMarks.getSoftwareEngineering() + DELIMITER + 
                             studentMarks.getSanskrit() + DELIMITER + 
                             studentMarks.getEnvironmentalScience());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving student marks: " + e.getMessage());
        }
    }
}

class StudentMarks {
    int rollNo;
    String name;
    double softwareEngineering, sanskrit, environmentalScience;

    public StudentMarks(int rollNo, String name, double softwareEngineering, double sanskrit, double environmentalScience) {
        this.rollNo = rollNo;
        this.name = name;
        this.softwareEngineering = softwareEngineering;
        this.sanskrit = sanskrit;
        this.environmentalScience = environmentalScience;
    }

    public String getName() {
        return name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public double getSoftwareEngineering() {
        return softwareEngineering;
    }

    public double getSanskrit() {
        return sanskrit;
    }

    public double getEnvironmentalScience() {
        return environmentalScience;
    }

    void setSoftwareEngineering(double softwareEngineering) {
        this.softwareEngineering = softwareEngineering;
    }

    void setSanskrit(double sanskrit) {
        this.sanskrit = sanskrit;
    }

    void setEnvironmentalScience(double environmentalScience) {
        this.environmentalScience = environmentalScience;
    }

    public String toString() {
        return rollNo + " " + name + " " + softwareEngineering + " " + sanskrit + " " + environmentalScience + " ";
    }

    public double totalMarks() {
        return softwareEngineering + sanskrit + environmentalScience;
    }
}

class SortByTotalMarks implements Comparator<StudentMarks> {
    // Used for sorting in descending order
    public int compare(StudentMarks m1, StudentMarks m2) {
        if (m2.totalMarks() == m1.totalMarks()) return 0;
        else if (m2.totalMarks() > m1.totalMarks()) return 1;
        else return -1;
    }
}

