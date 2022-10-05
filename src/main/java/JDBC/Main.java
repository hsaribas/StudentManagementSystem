package JDBC;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try{
            //variable for making the choice
            int choice = 0;
            //calling the student class
            student s = new student();
            do{
                System.out.println("WELCOME TO STUDENT RECORD SYSTEM\nPlease select the option from Menu\n 1 - Student Registration\n 2 - Password Update\n 3 - Delete Record\n 4 - Search For Any Student\n 5 - Show All Students\n 6 - Exit Application");
                //input for selecting the menu
                Scanner ch = new Scanner(System.in);
                choice = ch.nextInt();
                switch (choice){
                    case 1:
                        s.getStudentDetails();
                        s.saveStudent();
                        break;
                    case 2:
                        s.updatePassword();
                        break;
                    case 3:
                        s.deleteStudent();
                        break;
                    case 4:
                        s.searchStudent();
                        break;
                    case 5:
                        System.out.println("5 pressed");
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Please enter the correct choice");
                }
            }while(choice != 6);{
                System.out.println("Thanks for using the Application");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

//class for student record system
class student{
    private String name;
    private String email;
    private String password;
    private String country;
    private int marks;
    private int age;

    //making a method that takes user da==information
    public void getStudentDetails(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Student Name:");
        name = input.nextLine();
        System.out.print("Enter Student Email:");
        email = input.nextLine();
        System.out.print("Enter Student Password:");
        password = input.nextLine();
        System.out.print("Enter Student Country:");
        country = input.nextLine();
        System.out.print("Enter Student Total Marks:");
        marks = input.nextInt();
        System.out.print("Enter Student Age:");
        age = input.nextInt();
    }

    //making a method that takes the data from the user and save it into database
    public void saveStudent() throws SQLException {
        //calling the database connection class
        dbmsconnection db = new dbmsconnection();
        Connection con = db.getConnection("postgres","********");

        String sql = "Insert into students values (?,?,?,?,?,?);";

        //preparedStatements for getting the data from the students repeatedly
        PreparedStatement stmt = con.prepareStatement(sql);

        //saving the data to particular columns
        stmt.setString(1,name);
        stmt.setString(2,email);
        stmt.setString(3,password);
        stmt.setString(4,country);
        stmt.setInt(5,marks);
        stmt.setInt(6,age);

        //execute the statement
        stmt.executeUpdate();
        System.out.println("Data has been saved successfully!");
    }

    //method for updating the password
    public void updatePassword() throws SQLException {
        //calling the database connection class
        dbmsconnection db = new dbmsconnection();
        Connection con = db.getConnection("postgres","********");

        System.out.println("Please enter your email");
        Scanner input = new Scanner(System.in);
        String inputemail = input.nextLine();

        System.out.println("Enter new password");
        String newpass = input.nextLine();
        //writing the SQL query
        String sql = "update students set password = ? where email = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        //saving the data to particular columns
        stmt.setString(1,newpass);
        stmt.setString(2,inputemail);

        int i = stmt.executeUpdate();

        //checking if the email already exist in the database or not
        if(i > 0){
            System.out.println("Password has been updated successfully");
        }else{
            System.out.println("This Email doesn't exist in database");
        }
    }

    //making a method that delete the student record
    public void deleteStudent() throws SQLException {
        //calling the database connection class
        dbmsconnection db = new dbmsconnection();
        Connection con = db.getConnection("postgres","********");

        System.out.println("Please enter the student Email");
        Scanner input = new Scanner(System.in);
        String inputemail = input.nextLine();

        String sql = "delete from students where email = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,inputemail);

        int i = stmt.executeUpdate();

        //checking if the email already exist in the database or not
        if(i > 0){
            System.out.println("Record has been Deleted successfully");
        }else{
            System.out.println("This Email doesn't exist in database");
        }
    }

    //making a method to search for any particular student
    public void searchStudent() throws SQLException {
        //calling the database connection class
        dbmsconnection db = new dbmsconnection();
        Connection con = db.getConnection("postgres","********");

        System.out.println("Please enter the student Name");
        Scanner input = new Scanner(System.in);
        String inputname = input.nextLine();

        String sql = "select * from students where name = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,inputname);

        ResultSet rs = stmt.executeQuery();

        //if we are getting no record
        while(rs.next()) {
            //print all the columns
            System.out.print(rs.getString("name") + " ");
            System.out.print(rs.getString("email") + " ");
            System.out.print(rs.getString("password") + " ");
            System.out.print(rs.getString("country") + " ");
            System.out.print(rs.getString("marks") + " ");
            System.out.println(rs.getString("age") + " ");
        }
    }
}

//making a class that only contains the database connection
class dbmsconnection{
    //make a function to establish the db connection
    public Connection getConnection(String user, String pass){
        Connection conn = null;
        try{
            //load the driver
            Class.forName("org.postgresql.Driver");
            //setting up the connection
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/myconnection",user,pass);
            //condition of success
            if(conn != null){
                System.out.println("Connection established!");
            }else{
                System.out.println("Connection Failed");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }
}














