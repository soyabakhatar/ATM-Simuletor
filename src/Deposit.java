import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Deposit extends JFrame implements ActionListener {
    JTextField t1,t2;
    JButton b1,b2,b3;
    JLabel l1,l2,l3;
    String cardNumber;
    Deposit(String cardNumber) {
        this.cardNumber = cardNumber;
        setSize(900, 700);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/atm.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(900, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 900, 700);
        add(l3);

        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));

        b1 = new JButton("DEPOSIT");
        b2 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190,220,400,35);
        l3.add(l1);

        t1.setBounds(190,260,320,30);
        l3.add(t1);

        b1.setBounds(350,388,150,25);
        l3.add(b1);

        b2.setBounds(350,333,150,25);
        l3.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       if (ae.getSource() == b1) {
           try{
               String amount = t1.getText();
                   if(t1.getText().equals("")){
                       JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Deposit");
                   }else{
                       Connection con = DB.connect();
                       String q = "SELECT amount FROM user WHERE card_number = '"+cardNumber+"'";
                       PreparedStatement preparedStatement = con.prepareStatement(q);
                       ResultSet rs = preparedStatement.executeQuery(q);
                       while (rs.next()) {
                           String dbAmount = rs.getString(1);
                           String totalAmount = String.valueOf(Long.parseLong(dbAmount) + Long.parseLong(amount));
                           String q2 = "UPDATE user SET amount = '"+totalAmount+"' WHERE card_number = '"+cardNumber+"'";
                           PreparedStatement preparedStatement1 = con.prepareStatement(q2);
                           preparedStatement1.executeUpdate();

                           String q3 = "update signUpThird set amount = '"+totalAmount+"' where card_number = '"+cardNumber+"' ";
                           PreparedStatement preparedStatement2 = con.prepareStatement(q3);
                           preparedStatement2.executeUpdate();
                       }
                       JOptionPane.showMessageDialog(null, "Rs. "+amount+" Deposited Successfully");
                       setVisible(false);
                       new Transaction(cardNumber).setVisible(true);
                   }
           }catch(Exception e){
               e.printStackTrace();
           }
       }
       if(ae.getSource()==b2){
           setVisible(false);
           new Transaction(cardNumber).setVisible(true);
       }
    }

    public static void main(String[] args) {
        new Deposit("1234567890123456");
    }
}
