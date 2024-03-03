import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

public class SignupThird extends JFrame implements ActionListener {
    public static JLabel h2, accountTypeLabel, cardNumberLabel, cardInfo, cardNumberValue, pinLabel, pinInfo, initialAmountLabel;
    public static JTextField pinField, initialAmountField;
    public static JRadioButton savingBtn, fixedBtn, currentBtn, recurringBtn;
    public static JCheckBox declaration;
    public static JButton submitButton, cancelButton;
//    public static long cardNumber =
    public static int random;
    SignupThird(int random) {
        this.random = random;
        setSize(700, 700);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);

        h2 = new JLabel("Page 3: Account Details");
        h2.setBounds(250, 40, 300, 20);
        h2.setFont(new Font("Arial", Font.BOLD, 16));
        add(h2);

        accountTypeLabel = new JLabel("Account Type");
        accountTypeLabel.setBounds(100, 100, 100, 25);
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(accountTypeLabel);

        savingBtn = new JRadioButton("Saving Account");
        savingBtn.setBounds(100, 140, 200, 25);
        add(savingBtn);

        fixedBtn = new JRadioButton("Fixed Deposit Account");
        fixedBtn.setBounds(400, 140, 200, 25);
        add(fixedBtn);

        currentBtn = new JRadioButton("Current Account");
        currentBtn.setBounds(100, 180, 200, 25);
        add(currentBtn);

        recurringBtn = new JRadioButton("Recurring Deposit Account");
        recurringBtn.setBounds(400, 180, 200, 25);
        add(recurringBtn);

        //grouping radio
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(savingBtn);
        genderGroup.add(fixedBtn);
        genderGroup.add(currentBtn);
        genderGroup.add(recurringBtn);

        cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setBounds(100, 240, 200, 25);
        cardNumberLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(cardNumberLabel);

        cardInfo = new JLabel("Your card number");
        cardInfo.setBounds(100, 260, 200, 25);
        cardInfo.setFont(new Font("Arial", Font.BOLD, 10));
        add(cardInfo);

        cardNumberValue = new JLabel("XXXX-XXXX-XXXX-1234");
        cardNumberValue.setBounds(280, 240, 300, 25);
        cardNumberValue.setFont(new Font("Arial", Font.BOLD, 18));
        add(cardNumberValue);

        pinLabel = new JLabel("Pin");
        pinLabel.setBounds(100, 320, 200, 25);
        pinLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(pinLabel);

        pinInfo = new JLabel("Set your PIN");
        pinInfo.setBounds(100, 340, 200, 25);
        pinInfo.setFont(new Font("Arial", Font.BOLD, 10));
        add(pinInfo);

        pinField = new JTextField();
        pinField.setBounds(260, 320, 300, 25);
        add(pinField);

        initialAmountLabel = new JLabel("Initial Amount:");
        initialAmountLabel.setBounds(100, 400, 200, 25);
        initialAmountLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(initialAmountLabel);

        initialAmountField = new JTextField();
        initialAmountField.setBounds(260, 400, 300, 25);
        add(initialAmountField);

        declaration = new JCheckBox("I herebelly declare that all details are correct");
        declaration.setBounds(100, 460, 500, 25);
        add(declaration);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, 520, 100, 25);
        cancelButton.addActionListener(this);
        add(cancelButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(470, 520, 100, 25);
        submitButton.addActionListener(this);
        add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton) {
            System.out.println("Clicked");
            dispose();
            new LoginForm();
        }
        if (e.getSource() == submitButton) {
            String accountType = null;
            Random r = new Random();
            String cardNumber = "" + Math.abs((r.nextLong()%90000000L) + 5040936000000000L);
            String pinNumber = pinField.getText();
            long initialAmount = Long.parseLong(initialAmountField.getText());
            if(savingBtn.isSelected()) {
                accountType = "Saving";
            }else if(fixedBtn.isSelected()) {
                accountType = "Fixed";
            }else if(currentBtn.isSelected()) {
                accountType = "Current";
            }else if(recurringBtn.isSelected()) {
                accountType = "Recurring";
            }

            JOptionPane.showMessageDialog(null, "Card Number: " + cardNumber + "\n Pin: " + pinNumber);
           try {
               Connection con = DB.connect();
               String query1 = "INSERT INTO signUpThird VALUES (?, ?, ?, ?, ?)";
               String query2 = "INSERT INTO user VALUES (?, ?, ?, ?)";
               PreparedStatement preparedStatement1 = con.prepareStatement(query1);
               preparedStatement1.setString(1, String.valueOf(random));
               preparedStatement1.setString(2, accountType);
               preparedStatement1.setString(3, cardNumber);
               preparedStatement1.setString(4, pinNumber);
               preparedStatement1.setLong(5, initialAmount);

               PreparedStatement preparedStatement2 = con.prepareStatement(query2);
               preparedStatement2.setString(1, String.valueOf(random));
               preparedStatement2.setString(2, cardNumber);
               preparedStatement2.setString(3, pinNumber);
               preparedStatement2.setLong(4, initialAmount);

               preparedStatement1.executeUpdate();
               preparedStatement2.executeUpdate();

               con.close();
               dispose();
               new Transaction(cardNumber);
           }catch(Exception err) {
               err.printStackTrace();
           }
        }
    }
    public static void main(String[] args) {
        new SignupThird(0);
    }

}
