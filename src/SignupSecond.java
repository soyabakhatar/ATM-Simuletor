import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignupSecond extends JFrame implements ActionListener {
    public static JLabel formNumber, h2, religionLabel, categoryLabel, educationLabel, incomeLabel, occupationLabel, panLabel, phoneLabel, adharLabel;
    public static JTextField occupationField, panField, adharField, phoneField;
    public static JComboBox religionCombo, categoryCombo, incomeCombo, educationCombo;
    public static JButton next;
    public static int random;
    SignupSecond(int random) {
        this.random = random;
        setSize(700, 700);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);

        formNumber = new JLabel("Form No. " + random);
        formNumber.setBounds(550, 20, 100, 25);
        add(formNumber);

        //h2 heading
        h2 = new JLabel("Page 2: Additional Details");
        h2.setBounds(250, 60, 300, 20);
        h2.setFont(new Font("Arial", Font.BOLD, 16));
        add(h2);

        //religion label
        religionLabel = new JLabel("Religion");
        religionLabel.setBounds(100, 120, 100, 25);
        add(religionLabel);

        //religion field
        String religionList[] = {"Hindu", "Muslim", "Sikh"};
        religionCombo = new JComboBox(religionList);
        religionCombo.setBounds(230, 120, 380, 25);
        religionCombo.setBackground(Color.WHITE);
        add(religionCombo);

        //category label
        categoryLabel = new JLabel("Category");
        categoryLabel.setBounds(100, 180, 100, 25);
        add(categoryLabel);

        //category field
        String categoryList[] = {"General", "OBC", "SC", "ST"};
        categoryCombo = new JComboBox(categoryList);
        categoryCombo.setBounds(230, 180, 380, 25);
        categoryCombo.setBackground(Color.WHITE);
        add(categoryCombo);

        //Income label
        incomeLabel = new JLabel("Income");
        incomeLabel.setBounds(100, 240, 100, 25);
        add(incomeLabel);

        //income field
        String incomeList[] = {"< 100000", "< 200000", "< 300000", "> 500000"};
        incomeCombo = new JComboBox(incomeList);
        incomeCombo.setBounds(230, 240, 380, 25);
        incomeCombo.setBackground(Color.WHITE);
        add(incomeCombo);

        //education
        educationLabel = new JLabel("Education");
        educationLabel.setBounds(100, 300, 100, 25);
        add(educationLabel);

        //education field
        String educationList[] = {"10th", "12th", "Under Graduate", "Post Graduate"};
        educationCombo = new JComboBox(educationList);
        educationCombo.setBounds(230, 300, 380, 25);
        educationCombo.setBackground(Color.WHITE);
        add(educationCombo);

        //occupation
        occupationLabel = new JLabel("Occupation");
        occupationLabel.setBounds(100, 360, 100, 25);
        add(occupationLabel);

        //occupation field
        occupationField = new JTextField();
        occupationField.setBounds(230, 360, 380, 25);
        add(occupationField);

        //pan label
        panLabel = new JLabel("Pan Number:");
        panLabel.setBounds(100, 420, 100, 25);
        add(panLabel);

        //pan input field
        panField = new JTextField();
        panField.setBounds(230, 420, 380, 25);
        add(panField);

        //adhar label
        adharLabel = new JLabel("Adhaar Number:");
        adharLabel.setBounds(100, 480, 100, 25);
        add(adharLabel);

        //adhar input field
        adharField = new JTextField();
        adharField.setBounds(230, 480, 380, 25);
        add(adharField);

        //phone label
        phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(100, 540, 100, 25);
        add(phoneLabel);

        //phone input field
        phoneField = new JTextField();
        phoneField.setBounds(230, 540, 380, 25);
        add(phoneField);

        //submit button
        next = new JButton("Next");
        next.setBounds(100, 600, 200, 25);
        next.addActionListener(this);
        add(next);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String religion = (String) religionCombo.getSelectedItem();
        String category = (String) categoryCombo.getSelectedItem();
        String income = (String) incomeCombo.getSelectedItem();
        String education = (String) educationCombo.getSelectedItem();
        String occupation = occupationField.getText();
        String panNumber = panField.getText();
        String adharNumber = adharField.getText();
        String phoneNumber = phoneField.getText();

        try {
            Connection con = DB.connect();
            String query = "INSERT INTO signUpSecond VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(random));
            preparedStatement.setString(2, religion);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, income);
            preparedStatement.setString(5, education);
            preparedStatement.setString(6, occupation);
            preparedStatement.setString(7, panNumber);
            preparedStatement.setString(8, adharNumber);
            preparedStatement.setString(9, phoneNumber);

            preparedStatement.executeUpdate();
            con.close();

            dispose();
            new SignupThird(random);
        }catch(Exception err) {
            err.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SignupSecond(1234);
    }
}
