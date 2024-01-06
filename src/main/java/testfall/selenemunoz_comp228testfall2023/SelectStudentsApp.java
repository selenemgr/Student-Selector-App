package testfall.selenemunoz_comp228testfall2023;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.sql.*;

public class SelectStudentsApp extends Application {

    /*-------- Global Elements Declaration --------*/

    // Labels
    private Label enterCityLabel;
    private Label messageLabel;


    // TextFields
    private TextField enterCityTextField;

    // Labels
    private TableView<Student> studentTableView;

    /*----------------*/


    /*-------- Create connection with database --------*/

    // Connection Object //
    private Connection connection;

    // Method to connect to database //
    private void setConnectionToDatabase(){
        String databaseURL = "jdbc:mysql://localhost:3306/SeleneMunoz_COMP228TestFall2023";
        String username = "root";
        String password = "root";

        try{
            connection = DriverManager.getConnection(databaseURL,username, password);
            System.out.println("Connection made successfully to the database.");
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    /*----------------*/

    /*-------- Validation User's Input Methods --------*/

    // User's Input Validation Method // - Checks if user's input is empty or blank
    private Boolean userInputValidation(String inputValue){
        if(inputValue == null || inputValue.isEmpty() || inputValue.isBlank()){
            messageLabel.setText("Please enter a city");
            return false;
        }
        return true;
    }
    /*----------------*/

    // Record Validation with City // - Check if students' record exist with City
    private Boolean checkRecordExist(String city){
        try{
            PreparedStatement selectStudentWhereCityStatement = connection.prepareStatement("select * from students where city=?");
            selectStudentWhereCityStatement.setString(1, city);
            ResultSet resultSet = selectStudentWhereCityStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return false;
    }
    /*----------------*/

    /*-------- Database Interactions Methods --------*/

    // Search Student Method // - Search students info based on city input and add it to tableview
    private void searchStudentbyCity(){
        // Observable List - Store data from database
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
        try {
            String cityInput = enterCityTextField.getText();
            // Input Validation
            if (userInputValidation(cityInput)) {
                if (checkRecordExist(cityInput)) {
                    // Get student by city query
                    PreparedStatement selectProductWhereCityStatement = connection.prepareStatement("select * from students where city=?");
                    selectProductWhereCityStatement.setString(1, cityInput);
                    ResultSet resultSet = selectProductWhereCityStatement.executeQuery();

                    // Store resultset data into productObservableList
                    while (resultSet.next()) {
                        int studentId = resultSet.getInt("studentID");
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String address = resultSet.getString("address");
                        String city = resultSet.getString("city");
                        String province = resultSet.getString("province");
                        String postalCode = resultSet.getString("postalCode");

                        // Create and Add Product object into productObservableList
                        studentObservableList.add(new Student(studentId, firstName, lastName, address, city, province, postalCode));
                    }

                    // Link productObservableList into studentTableView
                    studentTableView.setItems(studentObservableList);

                    // Clear message label
                    messageLabel.setText("");
                } else {
                    messageLabel.setText("Student Records with City [ " + cityInput + " ] is not available");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    /*----------------*/


    @Override
    public void start(Stage stage) throws Exception {

        /*-------- Elements Declaration --------*/

        // Labels //
        enterCityLabel = new Label("Enter City: ");

        messageLabel = new Label();

        // TextFields //
        enterCityTextField = new TextField();

        // Buttons //
        Button searchButton = new Button("Search");
        searchButton.setOnAction(actionEvent -> searchStudentbyCity());

        /*==== Table View ====*/
        // Initialization //
        studentTableView = new TableView<>();

        //Table Structure - Columns //
        TableColumn<Student, Integer> idColumn = new TableColumn<>("Student ID");
        TableColumn<Student, String> firstNameColumn = new TableColumn<>("First Name");
        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
        TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
        TableColumn<Student, String> cityColumn = new TableColumn<>("City");
        TableColumn<Student, String> provinceColumn = new TableColumn<>("Province");
        TableColumn<Student, String> postalCodeColumn = new TableColumn<>("Postal Code");

        // Table Structure - Rows //
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));


        // Add Columns into Table //
        studentTableView.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, addressColumn, cityColumn, provinceColumn, postalCodeColumn);

        /*----------------*/


        /*-------- Layout manager --------*/

        // Create connection with database //
        setConnectionToDatabase();

        // VBox Root //
        VBox root = new VBox(5, enterCityLabel, enterCityTextField, searchButton ,messageLabel, studentTableView);
        root.setPadding(new Insets(10,10,10,10));

        // Scene //
        Scene scene = new Scene(root, 600, 400);
        // Image //
        Image iconImage = new Image("file:.\\src\\main\\java\\testfall\\selenemunoz_comp228testfall2023\\studentLogo.png");

        // Stage //
        stage.getIcons().add(iconImage);
        stage.setScene(scene);
        stage.setTitle("Search Student");

        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaxWidth(600);
        stage.setMaxHeight(400);

        stage.show();

        /*----------------*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
