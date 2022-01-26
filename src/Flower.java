import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Flower {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField textid;
    private JPanel Main;
    private JScrollPane table_1;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Flower Bouquet");
        frame.setContentPane(new Flower().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/flower", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }
    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from shop");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Flower() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flname,price,order;

                flname = textField1.getText();
                price = textField2.getText();
                order = textField3.getText();

                try {
                    pst = con.prepareStatement("insert into shop(flname,price,order_no)values(?,?,?)");
                    pst.setString(1, flname);
                    pst.setString(2, price);
                    pst.setString(3, order);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }


            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String ordrid = textid.getText();

                    pst = con.prepareStatement("select flname,price,order_no from shop where id = ?");
                    pst.setString(1, ordrid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String flname = rs.getString(1);
                        String price = rs.getString(2);
                        String order = rs.getString(3);

                        textField1.setText(flname);
                        textField2.setText(price);
                        textField3.setText(order);

                    }
                    else
                    {
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Bouquet order  No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderid,flname,price,order;
                flname = textField1.getText();
                price = textField2.getText();
                order = textField3.getText();
                orderid = textid.getText();

                try {
                    pst = con.prepareStatement("update shop set flname = ?,price = ?,order_no = ? where id = ?");
                    pst.setString(1, flname);
                    pst.setString(2, price);
                    pst.setString(3, order);
                    pst.setString(4, orderid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!!!!!");
                    table_load();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderid;
                orderid = textid.getText();

                try {
                    pst = con.prepareStatement("delete from shop  where id = ?");

                    pst.setString(1, orderid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }

}
