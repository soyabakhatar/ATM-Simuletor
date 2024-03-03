import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceEnquiry extends JFrame implements ActionListener {
    public static JLabel balanceLabel;
    public static JButton b2;
    String cardNumber;
    BalanceEnquiry(String cardNumber) {
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

        balanceLabel = new JLabel("");
        balanceLabel.setBounds(190,220,400,35);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(balanceLabel);

        b2 = new JButton("Back");
        b2.setBounds(170,380,150,25);
        b2.addActionListener(this);
        l3.add(b2);

        String balance;
        try {
            Connection con = DB.connect();
            String q = "SELECT amount FROM user WHERE card_number = '"+cardNumber+"'";
            PreparedStatement preparedStatement = con.prepareStatement(q);
            ResultSet rs = preparedStatement.executeQuery(q);

            while (rs.next()) {
                balance = rs.getString(1);
                balanceLabel.setText("Your Balance: ₹ " +balance);
            }
        }catch(Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new Transaction(cardNumber).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("1234567890123456");
    }
}
