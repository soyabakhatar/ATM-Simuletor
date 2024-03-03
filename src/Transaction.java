import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transaction extends JFrame implements ActionListener {
    JLabel l1;
    JButton b1,b2,b3,b4,b5,b6,b7;
    String cardNumber;
    Transaction(String cardNumber) {
        this.cardNumber = cardNumber;
        setSize(900, 700);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/atm.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(900, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);

        JLabel l2 = new JLabel(i3);
        l2.setBounds(0, 0, 900, 700);
        add(l2);

        l1 = new JLabel("Please Select Your Transaction");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(235,220,700,20);
        l2.add(l1);

        b1 = new JButton("DEPOSIT");
        b2 = new JButton("CASH WITHDRAWL");
        b5 = new JButton("PIN CHANGE");
        b6 = new JButton("BALANCE ENQUIRY");
        b7 = new JButton("EXIT");

        b1.setBounds(370,314,150,30);
        l2.add(b1);

        b2.setBounds(155,314,150,30);
        l2.add(b2);

        b5.setBounds(155,359,150,30);
        l2.add(b5);

        b6.setBounds(370,359,150,30);
        l2.add(b6);

        b7.setBounds(370,402,150,30);
        l2.add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==b1){
            dispose();
            new Deposit(cardNumber);
        }else if(ae.getSource()==b2){
            setVisible(false);
            new Withdrawl(cardNumber).setVisible(true);
        }else if(ae.getSource()==b5){
            setVisible(false);
            new Pin(cardNumber).setVisible(true);
        }else if(ae.getSource()==b6){
            this.setVisible(false);
            new BalanceEnquiry(cardNumber).setVisible(true);
        }else if(ae.getSource()==b7){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Transaction("1234567890123456");
    }
}
