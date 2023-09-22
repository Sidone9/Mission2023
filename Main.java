import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Main{

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "#sudhakar6213";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Statement stmt = con.createStatement();
            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner in = new Scanner(System.in);
                System.out.println(" 1. Reserve a room ");
                System.out.println(" 2. View Reservations ");
                System.out.println(" 3. Get Room Number ");
                System.out.println(" 4. Update Reservation ");
                System.out.println(" 5. Delete Reservations ");
                System.out.println(" 0. Exit ");
                System.out.print("Select Option:");
                int choice = in.nextInt();
                switch(choice){
                    case 1: reserveRoom(con , in ,stmt);
                            break;
                    case 2: viewReservations(con, stmt);
                            break;
                    case 3: getRoomNumber(con, stmt, in);
                            break;
                    case 4: updateReservation(con,stmt,in);
                            break;
                    case 5: deleteReservation(con,stmt,in);
                            break;
                    case 0: exist();
                            in.close();
                            return;
                    default :
                        System.out.println("Invalid Choice. Try Agian !!");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException();
        }
    }

    private static void reserveRoom(Connection con, Scanner in, Statement stmt) throws  SQLException{
        try{
            System.out.print("Enter Guest Name:");
            String guestName = in.next();
            in.nextLine();
            System.out.print("Enter Room Number:");
            int roomNumber = in .nextInt();
            System.out.print("Enter Contact Number:");
            String contactnumber = in.next();

            String query = "insert into reservation(guest_name, room_number, contact_number) values (' " +guestName+ " '," +roomNumber+ ", ' " + contactnumber + " ')";
                int affectedrows = stmt.executeUpdate(query);
                if (affectedrows > 0) {
                    System.out.println("Reservation Successful");
                } else {
                    System.out.println("Reservation Failed!!");
                }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection con , Statement stmt) throws SQLException{
        String query = "select * from reservation;";
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("Current Reservations");
        System.out.println("+----------------+------------+---------------+-----------------+--------------------+");
        System.out.println("| Reservation ID | Guest      | Room Number   | Contact Number  |  Reservation Date  | ");
        System.out.println("+----------------+------------+---------------+-----------------+--------------------+");

        while(rs.next()){
            int reservationID = rs.getInt("reservation_ID");
            String guestName = rs.getString("guest_name");
            int roomNumber = rs.getInt("room_number");
            String contactNumber = rs.getString("contact_number");
            String reservationDate = rs.getString("reservation_date").toString();

            // format to display the reservation table like format.
            System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s | \n", reservationID, guestName, roomNumber, contactNumber, reservationDate);
        }

        System.out.println("+-----------------+------------+--------------+------------------+---------------------+---------------+");

    }

    private static void getRoomNumber(Connection con, Statement stmt, Scanner in) throws SQLException{
        try{
            System.out.print("Enter Reservation ID:");
            int reservationId = in.nextInt();
            System.out.print("Enter Guest Name:");
            String GuestName = in.next();

            String query = "select room_number from reservation where reservation_id = " + reservationId + "and guest_name = ' " + GuestName + "'";

            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()){
                int roomnumber = rs.getInt("room_number");
                System.out.print("Room Number for Reservation ID " + reservationId + " and Guest " + GuestName + " is: " + roomnumber);
            }else{
                System.out.print("Reservation not found for the given ID and Guest Name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection con, Statement stmt, Scanner in) throws SQLException{
        try{
            System.out.print("Enter reservation ID to update:");
            int reservationId = in.nextInt();
            in.nextLine(); // Consume the newline character.

            if(!reservationExist(con, reservationId, stmt)){
                System.out.print("Reservation Not Found for given ID.");
                return;
            }

            System.out.print("Enter the new guest Name:");
            String newguestName = in.nextLine();
            System.out.print("Enter new room number:");
            int newroomnumber = in.nextInt();
            System.out.print("Enter the contact number:");
            String newcontactnumber = in.next();

            String query = "update reservation set guest_name = ' " + newguestName + "'," + "room_number = " + newroomnumber + "," + "contact_number = '" + newcontactnumber + "' " + "where reservation_id = " + reservationId;
            int affectedrows = stmt.executeUpdate(query);

            if(affectedrows > 0){
                System.out.print("Reservation Updated Successfully!");
            }else{
                System.out.print("Reservation Update Failed!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection con , Statement stmt, Scanner in) throws SQLException {
        try{
            System.out.print("Enter reservation ID to Delete:");
            int reservationID = in.nextInt();

            if(!reservationExist(con, reservationID, stmt)){
                System.out.print("Reservation not found for the given ID.");
                return;
            }

            String query = "delete from reservation where reservation_id = " + reservationID;
            int affectedrows = stmt.executeUpdate(query);
            if(affectedrows > 0){
                System.out.print("Reservation deleted successfully!");
            }else{
                System.out.print("Reservation Deletion Failed.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean reservationExist(Connection con , int reservationId, Statement stmt) throws  SQLException{
        try{
            String query = "select reservation_id from reservation where reservation_id = " + reservationId;
            ResultSet rs = stmt.executeQuery(query);

            return rs.next(); // if there's a result , the reservation exists.
        }catch(SQLException e){
            e.printStackTrace();
            return false; // Handle database errors as needed
        }
    }

    public static void exist() throws InterruptedException{
        System.out.print("Exiting System");
        int i = 5;
        while(i != 0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

}