import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame implements ActionListener {
    public static JLabel cardNoLabel, pinLabel, welcomeMessage;
    public static JButton signUpButton, loginButton, clearButton;
    public static JTextField cardNoField;
    public static JPasswordField pinField;
    LoginForm() {
        welcomeMessage = new JLabel("Welcome to Bank");
        welcomeMessage.setBounds(200, 20, 300, 50);
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 35));
        add(welcomeMessage);

        cardNoLabel = new JLabel("Card No.");
        cardNoLabel.setBounds(50, 115, 200, 20);
        cardNoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(cardNoLabel);

        cardNoField = new JTextField();
        cardNoField.setBounds(300, 110, 300, 28);
        add(cardNoField);

        pinLabel = new JLabel("PIN");
        pinLabel.setBounds(100, 175, 200, 20);
        pinLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(300, 170, 300, 28);
        add(pinField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 240, 290, 35);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.white);
        loginButton.addActionListener(this);
        add(loginButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(410, 240, 190, 35);
        clearButton.setFont(new Font("Arial", Font.BOLD, 15));
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.white);
        clearButton.addActionListener(this);
        add(clearButton);

        signUpButton = new JButton("Create Account");
        signUpButton.setBounds(410, 310, 190, 35);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 15));
        signUpButton.setBackground(Color.DARK_GRAY);
        signUpButton.setForeground(Color.white);
        signUpButton.addActionListener(this);
        add(signUpButton);

        setSize(700, 500);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        new LoginForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String cardNumber = cardNoField.getText();
            String pinNumber = pinField.getText();
            System.out.println(pinNumber);
            dispose();
            //validation
           try {
               Connection con =  DB.connect();
               String q  = "select * from user where card_number = '"+cardNumber+"' and pin_no = '"+pinNumber+"'";
               PreparedStatement preparedStatement = con.prepareStatement(q);
               ResultSet rs = preparedStatement.executeQuery(q);

               while (rs.next()) {
                   String cardN = rs.getString(2);
                   String pinN = rs.getString(3);

                   if(cardN.equalsIgnoreCase(cardNumber) && pinN.equalsIgnoreCase(pinNumber)) {
                        new Transaction(cardNumber);
                   }else {
                       JOptionPane.showMessageDialog(null, "Either card number or pin no wrong");
                   }
               }
           }catch(Exception err) {
               err.printStackTrace();
           }
        }
        if (e.getSource() == clearButton) {
            cardNoField.setText("");
            pinField.setText("");
        }
        if (e.getSource() == signUpButton) {
            dispose();
            new SignupFirst();
        }
    }
}
