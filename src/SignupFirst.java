import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

import com.toedter.calendar.JDateChooser;

public class SignupFirst extends JFrame implements ActionListener {
    public static JLabel h1, h2, nameLabel, fatherNameLabel, dobLabel, genderLabel, addressLabel, emailLabel, cityLabel, stateLabel;
    public static JTextField nameField, fatherNameField, addressField, emailField, cityField, stateField;
    public static JRadioButton male, female, other;
    public static JDateChooser date;
    public static JButton next;
    public static int randomNumber;
    SignupFirst() {
        Random r = new Random();
        randomNumber =  r.nextInt(8999) + 1000;

        setSize(700, 700);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);

        //h1 heading
        h1 = new JLabel("Application Form No." + randomNumber);
        h1.setBounds(130, 20, 600, 50);
        h1.setFont(new Font("Arial", Font.BOLD, 35));
        add(h1);

        //h2 heading
        h2 = new JLabel("Page 1: Personal Details");
        h2.setBounds(250, 80, 300, 20);
        h2.setFont(new Font("Arial", Font.BOLD, 16));
        add(h2);

        //name label
        nameLabel = new JLabel("Name");
        nameLabel.setBounds(100, 120, 100, 25);
        add(nameLabel);

        //name input field
        nameField = new JTextField();
        nameField.setBounds(230, 120, 380, 25);
        add(nameField);

        //father name
        fatherNameLabel = new JLabel("Father Name");
        fatherNameLabel.setBounds(100, 180, 100, 25);
        add(fatherNameLabel);

        //father input field
        fatherNameField = new JTextField();
        fatherNameField.setBounds(230, 180, 380, 25);
        add(fatherNameField);

        //gender label
        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(100, 240, 100, 25);
        add(genderLabel);

        //gender radio male
        male = new JRadioButton("Male");
        male.setBounds(230, 240, 120, 25);
        add(male);

        //gender radio female
        female = new JRadioButton("Female");
        female.setBounds(350, 240, 120, 25);
        add(female);

        //gender radio other
        other = new JRadioButton("Other");
        other.setBounds(470, 240, 120, 25);
        add(other);

        //grouping radio
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);

        //dob
        dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(100, 300, 100, 25);
        add(dobLabel);

        //dob calender
        date = new JDateChooser();
        date.setBounds(230, 300, 380, 25);
        add(date);

        //email address
        emailLabel = new JLabel("Email Address:");
        emailLabel.setBounds(100, 360, 100, 25);
        add(emailLabel);

        //email input field
        emailField = new JTextField();
        emailField.setBounds(230, 360, 380, 25);
        add(emailField);

        //address label
        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(100, 420, 100, 25);
        add(addressLabel);

        //address input field
        addressField = new JTextField();
        addressField.setBounds(230, 420, 380, 25);
        add(addressField);

        //city label
        cityLabel = new JLabel("City:");
        cityLabel.setBounds(100, 480, 100, 25);
        add(cityLabel);

        //city input field
        cityField = new JTextField();
        cityField.setBounds(230, 480, 380, 25);
        add(cityField);

        //state label
        stateLabel = new JLabel("State:");
        stateLabel.setBounds(100, 540, 100, 25);
        add(stateLabel);

        //state input field
        stateField = new JTextField();
        stateField.setBounds(230, 540, 380, 25);
        add(stateField);

        //submit button
        next = new JButton("Next");
        next.setBounds(100, 600, 200, 25);
        next.addActionListener(this);
        add(next);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String fatherName = fatherNameField.getText();
        String gender = null;
        if(male.isSelected()) {
            gender = "Male";
        }else if(female.isSelected()) {
            gender = "Female";
        }else if(other.isSelected()) {
            gender = "Other";
        }
        String dob = ((JTextField) date.getDateEditor().getUiComponent()).getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();

        try {
            if(name.isEmpty() || fatherName.isEmpty() || dob.isEmpty() || email.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all details");
            }else {
                Connection con = DB.connect();

                //using notation
                String query = "INSERT INTO signUpFirst VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query);

                //inserting values
                preparedStatement.setString(1, String.valueOf(randomNumber));
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, fatherName);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, dob);
                preparedStatement.setString(6, email);
                preparedStatement.setString(7, address);
                preparedStatement.setString(8, city);
                preparedStatement.setString(9, state);

                //executing running statement
                preparedStatement.executeUpdate();

                //clearing all text field
                nameField.setText("");
                fatherNameField.setText("");
                ((JTextField) date.getDateEditor().getUiComponent()).setText("");
                emailField.setText("");
                addressField.setText("");
                cityField.setText("");
                stateField.setText("");

                dispose();
                //opening second form
                new SignupSecond(randomNumber);

                //closing connection
                con.close();
            }
        }catch(Exception err) {
            err.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new SignupFirst();
    }
}
