package javaapplication43;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// ---------------------- INFORMATION FOR LOGGING / SIGNING --------------------
class info {
    String uname, pass;

    info(String uname, String pass) {
        this.uname = uname;
        this.pass = pass;
    }
}

// ---------------------- ACCOUNT WINDOW ----------------------
class AccountManager implements ActionListener {
    ArrayList<info> accounts = new ArrayList<>();
    JFrame frm;
    JTextField usertxt;
    JPasswordField passfield;
    JButton btnsign, btnlog;

    AccountManager() {
        frm = new JFrame("Jeepney Terminal Organizer");
        frm.setSize(2000, 1000);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.cyan);

        JLabel lbl1 = new JLabel("Welcome to");
        lbl1.setBounds(815, 10, 450, 150);
        lbl1.setFont(new Font("Georgia", Font.BOLD, 45));
        lbl1.setForeground(Color.blue);

        JLabel lbl2 = new JLabel("JTO");
        lbl2.setBounds(900, 55, 300, 150);
        lbl2.setFont(new Font("Georgia", Font.BOLD, 56));
        lbl2.setForeground(Color.blue);

        btnlog = new JButton("Log in");
        btnlog.setFont(new Font("Georgia", Font.BOLD, 45));
        btnlog.setBackground(Color.blue);
        btnlog.setForeground(Color.white);
        btnlog.setBounds(835, 450, 250, 90);

        btnsign = new JButton("Sign up");
        btnsign.setFont(new Font("Georgia", Font.BOLD, 45));
        btnsign.setBackground(Color.blue);
        btnsign.setForeground(Color.white);
        btnsign.setBounds(835, 650, 250, 90);

        mainPanel.add(lbl1);
        mainPanel.add(lbl2);
        mainPanel.add(btnlog);
        mainPanel.add(btnsign);

        frm.add(mainPanel);
        frm.setVisible(true);

        btnsign.addActionListener(this);
        btnlog.addActionListener(this);
    }

    void signUp() {
        JFrame signfrm = new JFrame("Sign Up");
        signfrm.setSize(600, 300);
        signfrm.setLocationRelativeTo(null);
        signfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel signPanel = new JPanel();
        signPanel.setLayout(null);
        signPanel.setBackground(Color.white);

        JLabel crtlbl = new JLabel("Create Account");
        crtlbl.setFont(new Font("Georgia", Font.BOLD, 18));
        crtlbl.setBounds(220, 20, 150, 30);

        JLabel userlbl = new JLabel("Enter Username:");
        userlbl.setBounds(110, 60, 130, 30);

        JLabel passlbl = new JLabel("Enter Password:");
        passlbl.setBounds(110, 100, 130, 30);

        usertxt = new JTextField();
        usertxt.setBounds(240, 60, 170, 30);

        passfield = new JPasswordField();
        passfield.setBounds(240, 100, 170, 30);

        JButton signBtn = new JButton("Sign up");
        signBtn.setBounds(240, 160, 110, 40);

        signPanel.add(crtlbl);
        signPanel.add(userlbl);
        signPanel.add(passlbl);
        signPanel.add(usertxt);
        signPanel.add(passfield);
        signPanel.add(signBtn);
        signfrm.add(signPanel);
        signfrm.setVisible(true);

        signBtn.addActionListener(e -> {
            String signName = usertxt.getText().trim();
            String signPass = new String(passfield.getPassword()).trim();
            if (signName.isEmpty() || signPass.isEmpty()) {
                JOptionPane.showMessageDialog(signfrm, "Please fill in all fields!");
            } else {
                accounts.add(new info(signName, signPass));
                JOptionPane.showMessageDialog(signfrm, "Account created successfully!");
                signfrm.dispose();
            }
        });
    }

    void logIn() {
        JFrame logfrm = new JFrame("Log In");
        logfrm.setSize(600, 300);
        logfrm.setLocationRelativeTo(null);
        logfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setBackground(Color.white);

        JLabel userlbl = new JLabel("Enter Username:");
        userlbl.setBounds(110, 60, 130, 30);

        JLabel passlbl = new JLabel("Enter Password:");
        passlbl.setBounds(110, 100, 130, 30);

        JTextField usertxt = new JTextField();
        usertxt.setBounds(240, 60, 170, 30);

        JPasswordField passfield = new JPasswordField();
        passfield.setBounds(240, 100, 170, 30);

        JButton logBtn = new JButton("Log in");
        logBtn.setBounds(240, 160, 110, 40);

        logPanel.add(userlbl);
        logPanel.add(passlbl);
        logPanel.add(usertxt);
        logPanel.add(passfield);
        logPanel.add(logBtn);
        logfrm.add(logPanel);
        logfrm.setVisible(true);

        logBtn.addActionListener(e -> {
            String lname = usertxt.getText().trim();
            String lpass = new String(passfield.getPassword()).trim();

            boolean found = false;
            for (info acc : accounts) {
                if (lname.equalsIgnoreCase(acc.uname) && lpass.equals(acc.pass)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(logfrm, "Login successful! Welcome " + lname);
                logfrm.dispose();
                new MainInterface();
            } else {
                JOptionPane.showMessageDialog(logfrm, "Invalid credentials!");
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnsign) signUp();
        else if (e.getSource() == btnlog) logIn();
    }
}

// ---------------------- MAIN INTERFACE ----------------------
class MainInterface implements ActionListener {
    CircularQueue q = new CircularQueue();
    JButton startBtn, exitBtn;
    JLabel lblHead;

    MainInterface() {
        mainFrame();
    }

    void mainFrame() {
        JFrame mainFrame = new JFrame("Main Interface");
        mainFrame.setSize(1950, 1000);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(null);
        main.setBackground(Color.CYAN);

        lblHead = new JLabel("JEEPNEY TERMINAL ORGANIZER");
        lblHead.setBounds(790, 50, 450, 50);
        lblHead.setFont(new Font("Arial", Font.BOLD, 21));

        startBtn = new JButton("START");
        startBtn.setBounds(880, 300, 150, 70);

        exitBtn = new JButton("EXIT");
        exitBtn.setBounds(880, 500, 150, 70);

        main.add(lblHead);
        main.add(startBtn);
        main.add(exitBtn);
        mainFrame.add(main);
        mainFrame.setVisible(true);

        startBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }

    void operation() {
        q.inputSize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) operation();
        else if (e.getSource() == exitBtn) System.exit(0);
    }
}

// ---------------------- CIRCULAR QUEUE ----------------------
class CircularQueue {
    int jeep[];
    int size;
    int front = -1, rear = -1;

    void inputSize() {
        String input = JOptionPane.showInputDialog(null, "Enter maximum number of jeepneys in queue:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                size = Integer.parseInt(input);
                jeep = new int[size];
                JOptionPane.showMessageDialog(null, "Queue size set to " + size);
                mainOperation();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
            }
        }
    }

    void mainOperation() {
        JFrame mainOp = new JFrame("Jeepney Terminal Organizer");
        mainOp.setSize(700, 500);
        mainOp.setLocationRelativeTo(null);
        mainOp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel qPanel = new JPanel();
        qPanel.setLayout(null);
        qPanel.setBackground(Color.white);

        JLabel lblQueue = new JLabel("Jeepney Terminal Organizer");
        lblQueue.setBounds(240, 30, 300, 20);

        JLabel lblAdd = new JLabel("Add Jeepney ID:");
        lblAdd.setBounds(170, 70, 100, 20);

        JTextField txtJpn = new JTextField();
        txtJpn.setBounds(270, 70, 200, 20);

        JButton btnAdd = new JButton("ENQUEUE");
        btnAdd.setBounds(100, 120, 120, 30);

        JButton btnRemove = new JButton("DISPATCH");
        btnRemove.setBounds(250, 120, 120, 30);

        JButton btnShow = new JButton("SHOW QUEUE");
        btnShow.setBounds(400, 120, 150, 30);

        JButton btnExit = new JButton("EXIT");
        btnExit.setBounds(280, 180, 100, 30);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBounds(150, 230, 400, 200);

        qPanel.add(lblQueue);
        qPanel.add(lblAdd);
        qPanel.add(txtJpn);
        qPanel.add(btnAdd);
        qPanel.add(btnRemove);
        qPanel.add(btnShow);
        qPanel.add(btnExit);
        qPanel.add(displayArea);
        mainOp.add(qPanel);
        mainOp.setVisible(true);

        btnAdd.addActionListener(e -> {
            if (txtJpn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(mainOp, "Enter a jeepney ID!");
                return;
            }
            int id = Integer.parseInt(txtJpn.getText().trim());
            enqueue(id);
            txtJpn.setText("");
            displayArea.setText(showQueue());
        });

        btnRemove.addActionListener(e -> {
            dequeue();
            displayArea.setText(showQueue());
        });

        btnShow.addActionListener(e -> displayArea.setText(showQueue()));

        btnExit.addActionListener(e -> mainOp.dispose());
    }

    void enqueue(int val) {
        if (isFull()) {
            JOptionPane.showMessageDialog(null, "Queue is full!");
            return;
        }
        if (front == -1) front = 0;
        rear = (rear + 1) % size;
        jeep[rear] = val;
        JOptionPane.showMessageDialog(null, "Jeepney ID " + val + " added!");
    }

    void dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "Queue is empty!");
            return;
        }
        int removed = jeep[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front + 1) % size;
        }
        JOptionPane.showMessageDialog(null, "Jeepney ID " + removed + " dispatched!");
    }

    String showQueue() {
        if (isEmpty()) return "Queue is empty!";
        StringBuilder sb = new StringBuilder("Current Jeepneys in Queue:\n");
        int i = front;
        while (true) {
            sb.append(jeep[i]).append(" ");
            if (i == rear) break;
            i = (i + 1) % size;
        }
        return sb.toString();
    }

    boolean isFull() {
        return (front == 0 && rear == size - 1) || (front == rear + 1);
    }

    boolean isEmpty() {
        return front == -1;
    }
}

// ---------------------- MAIN ----------------------
public class Main2 {
    public static void main(String[] args) {
        new AccountManager();
    }
}

