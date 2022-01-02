import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import static java.lang.Class.forName;

public class ProductApp {
    private JLabel main;
    private JTextField txtname;
    private JTextField txtprice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtid;
    private JTextField txtqty;
    private JPanel Main;
    private JButton searchBtn;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProductApp");
        frame.setContentPane(new ProductApp().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public ProductApp() {

        connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, price,qty;

                name=txtname.getText();
                price=txtprice.getText();
                qty =txtqty.getText();
                try{
                    pst=con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Added!!!!");
                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();

                }catch (SQLException e1){
                      e1.printStackTrace();
                }
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id=txtid.getText();
                    pst=con.prepareStatement("select pname,price,qty from products where pid=?");
                    pst.setString(1,id);
                    ResultSet rs=pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String name=rs.getString(1);
                        String price=rs.getString(2);
                        String qty=rs.getString(3);

                        txtname.setText(name);
                        txtprice.setText(price);
                        txtqty.setText(qty);
                    }
                    else {
                        txtname.setText("");
                        txtprice.setText("");
                        txtqty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid ID");
                    }

                }catch (SQLException exp){
                    exp.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid ,name, price,qty;

                name=txtname.getText();
                price=txtprice.getText();
                qty =txtqty.getText();
                pid=txtid.getText();
                try {
                    pst=con.prepareStatement("update products set pname=?,price=?, qty=? where pid=?");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Update Complete!!");
                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();
                    txtid.setText("");

                }catch (SQLException exp1){
                    exp1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String did;
                did=txtid.getText();
                try {
                    pst=con.prepareStatement("delete from products where pid=?");
                    pst.setString(1,did);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Delete Item!!");
                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();
                    txtid.setText("");
                }catch (SQLException exp2){
                    exp2.printStackTrace();
                }
            }
        });
    }

    public void connect(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/products","root","");
            System.out.println("Succes");
        }
      catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}

