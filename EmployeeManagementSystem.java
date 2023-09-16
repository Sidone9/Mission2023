
class Employee{
    String name;
    String Designation;
    int yearsOfExperience;
    double basicSalary;

    public Employee(String s, String d, int y, double b){
        name = s;
        Designation = d;
        yearsOfExperience = y;
        basicSalary = b;

    }
}

class Manager extends Employee{
    double allowance;

    public Manager(String name, String Designation, int yearsOfExperience, double basicSalary, double d2){
        super(Designation, Designation, yearsOfExperience, basicSalary);
        allowance = d2;
    }
}

class Clerk extends Employee{

    int overTimeHours;

    public Clerk(String s, String d, int y, double b, int over) {
        super(s, d, y, b);
        overTimeHours = over;
    }
}

class EmployeeManagement{
    public void addEmployee(Employee employee){
        System.out.println("Adding Employee");
        employee.name = "Sudhakar Pandey";
        employee.name = "Virat Kohli";
    }

    public void removeEmployee(Employee employee){
        System.out.println("Removing Employee");
        employee.name = null;
    }

    public void displayEmployeeDetail(Employee employee){
        System.out.println("Employee Details");
        System.out.println(employee.name);
        System.out.println(employee.Designation);
        System.out.println(employee.yearsOfExperience);
        System.out.println(employee.basicSalary);
    }

    public void calculateSalary(Employee employee){
        if(employee.Designation == "Manager" && employee.yearsOfExperience == 5){
            employee.basicSalary = 500000;
        }else{
            employee.basicSalary = 40000;
        }
    }

    public void calculateTotalSalary(Employee employee){
        
    }
}