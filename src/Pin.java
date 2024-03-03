import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Pin extends JFrame implements ActionListener {
    JPasswordField t1,t2;
    JButton b1,b2;
    JLabel l1,l2,l3;
    String cardNumber;
    Pin(String cardNumber) {
        this.cardNumber = cardNumber;
        setSize(900, 700);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/atm.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(900, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l4 = new JLabel(i3);
        l4.setBounds(0, 0, 900, 700);
        add(l4);

        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);

        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);

        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);

        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));

        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));

        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");

        b1.addActionListener(this);
        b2.addActionListener(this);


        l1.setBounds(260,220,800,35);
        l4.add(l1);

        l2.setBounds(160,260,150,35);
        l4.add(l2);

        l3.setBounds(160,300,200,35);
        l4.add(l3);

        t1.setBounds(330,260,180,25);
        l4.add(t1);

        t2.setBounds(330,300,180,25);
        l4.add(t2);

        b1.setBounds(360,380,150,25);
        l4.add(b1);

        b2.setBounds(170,380,150,25);
        l4.add(b2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String npin = t1.getText();
            String rpin = t2.getText();

            if(!npin.equals(rpin)){
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
            } else if (t1.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Enter New PIN");
            }
            else if (t2.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Re-Enter new PIN");
            }else {
               try {
                   Connection con = DB.connect();
                   String q1 = "update user set pin_no = '"+rpin+"' where card_number = '"+cardNumber+"' ";
                   String q2 = "update signUpThird set pin_no = '"+rpin+"' where card_number = '"+cardNumber+"' ";

                   PreparedStatement preparedStatement1 = con.prepareStatement(q1);
                   preparedStatement1.executeUpdate(q1);

                   PreparedStatement preparedStatement2 = con.prepareStatement(q2);
                   preparedStatement2.executeUpdate(q2);

                   JOptionPane.showMessageDialog(null, "PIN changed successfully");
                   setVisible(false);
                   new Transaction(cardNumber).setVisible(true);
               }catch(Exception err) {
                   err.printStackTrace();
               }
            }
        }
        if(ae.getSource()==b2){
            setVisible(false);
            new Transaction(cardNumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Pin("1234567890123456");
    }
}
