import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Withdrawl extends JFrame implements ActionListener {
    JTextField t1,t2;
    JButton b1,b2,b3;
    JLabel l1,l2,l3,l4;
    String cardNumber;
    Withdrawl(String cardNumber) {
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

        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));

        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(160,220,400,20);
        l3.add(l1);

        l2.setBounds(160,280,400,20);
        l3.add(l2);

        t1.setBounds(160,300,330,30);
        l3.add(t1);

        b1.setBounds(350,360,150,25);
        l3.add(b1);

        b2.setBounds(350,400,150,25);
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
                        JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Withdraw");
                    }else{
                        Connection con = DB.connect();
                        String q = "SELECT amount FROM user WHERE card_number = '"+cardNumber+"'";
                        PreparedStatement preparedStatement = con.prepareStatement(q);
                        ResultSet rs = preparedStatement.executeQuery(q);
                        while (rs.next()) {
                            String dbAmount = rs.getString(1);
                            if (Long.parseLong(dbAmount) < Long.parseLong(amount)) {
                                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                            }else if(Long.parseLong(dbAmount) > Long.parseLong(amount)) {
                                String balance = String.valueOf(Long.parseLong(dbAmount) - Long.parseLong(amount));
                                String q2 = "UPDATE user SET amount = '"+balance+"' WHERE card_number = '"+cardNumber+"'";
                                PreparedStatement preparedStatement1 = con.prepareStatement(q2);
                                preparedStatement1.executeUpdate();

                                String q3 = "update signUpThird set amount = '"+balance+"' where card_number = '"+cardNumber+"' ";
                                PreparedStatement preparedStatement2 = con.prepareStatement(q3);
                                preparedStatement2.executeUpdate();

                                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Deposited Successfully");
                                setVisible(false);
                                new Transaction(cardNumber).setVisible(true);
                            }
                        }
                    }
            }catch(Exception err){
                err.printStackTrace();
                System.out.println("error: "+err);
            }
        }
        if (ae.getSource() == b2) {
            setVisible(false);
            new Transaction(cardNumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Withdrawl("1234567890123456");
    }
}
