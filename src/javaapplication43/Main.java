package javaapplication44;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class info {
    String uname, pass;
    info(String uname, String pass) {
        this.uname = uname;
        this.pass = pass;
    }
}

class CircularQueue {
    private String[] queue;
    private int front;
    private int rear;
    private int maxSize;
    private int currentSize;
    
    public CircularQueue(int size) {
        maxSize = size;
        queue = new String[maxSize];
        front = -1;
        rear = -1;
        currentSize = 0;
    }
    
    public boolean isFull() {
        return currentSize == maxSize;
    }
    
    public boolean isEmpty() {
        return currentSize == 0;
    }
    
    public void enqueue(String jeepneyId) {
        if (isFull()) {
            throw new RuntimeException("Queue is full. Cannot add jeepney: " + jeepneyId);
        }
        if (isEmpty()) {
            front = 0;
        }
        rear = (rear + 1) % maxSize;
        queue[rear] = jeepneyId;
        currentSize++;
    }
    
    public String dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty. No jeepney to dispatch.");
        }
        String jeepneyId = queue[front];
        queue[front] = null;
        if (front == rear) {
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % maxSize;
        }
        currentSize--;
        return jeepneyId;
    }
    
    public String[] getQueue() {
        if (isEmpty()) return new String[0];
        String[] result = new String[currentSize];
        int index = 0;
        int i = front;
        while (index < currentSize) {
            result[index++] = queue[i];
            i = (i + 1) % maxSize;
        }
        return result;
    }
    
    public int getCurrentSize() {
        return currentSize;
    }
    
    public int getMaxSize() {
        return maxSize;
    }
}

class AccountManager implements ActionListener {
    ArrayList<info> accounts = new ArrayList<>();
    JFrame frm;
    JButton btnlog, btnsign;
    
    AccountManager() {
        accounts.add(new info("admin", "admin123"));
        accounts.add(new info("user", "pass"));
        
        frm = new JFrame("Jeepney Terminal Organizer");
        frm.setSize(1000, 600);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 191, 255));
        
        JLabel lbl1 = new JLabel("Welcome to");
        lbl1.setBounds(350, 50, 400, 80);
        lbl1.setFont(new Font("Georgia", Font.BOLD, 45));
        lbl1.setForeground(new Color(0, 102, 204));
        
        JLabel lbl2 = new JLabel("JTO");
        lbl2.setBounds(430, 120, 200, 80);
        lbl2.setFont(new Font("Georgia", Font.BOLD, 56));
        lbl2.setForeground(new Color(0, 102, 204));
        
        JLabel subtitle = new JLabel("Jeepney Terminal Organizer");
        subtitle.setBounds(330, 190, 400, 30);
        subtitle.setFont(new Font("Georgia", Font.PLAIN, 20));
        subtitle.setForeground(Color.DARK_GRAY);
        
        btnlog = new JButton("Log in");
        btnlog.setFont(new Font("Georgia", Font.BOLD, 35));
        btnlog.setBackground(new Color(52, 152, 219));
        btnlog.setForeground(Color.WHITE);
        btnlog.setBounds(370, 280, 250, 70);
        btnlog.setFocusPainted(false);
        btnlog.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnsign = new JButton("Sign up");
        btnsign.setFont(new Font("Georgia", Font.BOLD, 35));
        btnsign.setBackground(new Color(46, 204, 113));
        btnsign.setForeground(Color.WHITE);
        btnsign.setBounds(370, 380, 250, 70);
        btnsign.setFocusPainted(false);
        btnsign.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        mainPanel.add(lbl1);
        mainPanel.add(lbl2);
        mainPanel.add(subtitle);
        mainPanel.add(btnlog);
        mainPanel.add(btnsign);
        
        frm.add(mainPanel);
        frm.setVisible(true);
        
        btnsign.addActionListener(this);
        btnlog.addActionListener(this);
    }
    
    void signUp() {
        JFrame signfrm = new JFrame("Sign Up - JTO");
        signfrm.setSize(550, 350);
        signfrm.setLocationRelativeTo(frm);
        signfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel signPanel = new JPanel();
        signPanel.setLayout(null);
        signPanel.setBackground(Color.white);
        
        JLabel crtlbl = new JLabel("Create Account");
        crtlbl.setFont(new Font("Georgia", Font.BOLD, 22));
        crtlbl.setForeground(new Color(46, 204, 113));
        crtlbl.setBounds(180, 30, 200, 30);
        
        JLabel userlbl = new JLabel("Enter Username:");
        userlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        userlbl.setBounds(100, 90, 130, 30);
        
        JLabel passlbl = new JLabel("Enter Password:");
        passlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passlbl.setBounds(100, 140, 130, 30);
        
        JTextField usertxt = new JTextField();
        usertxt.setFont(new Font("Arial", Font.PLAIN, 14));
        usertxt.setBounds(230, 90, 200, 30);
        
        JPasswordField passfield = new JPasswordField();
        passfield.setFont(new Font("Arial", Font.PLAIN, 14));
        passfield.setBounds(230, 140, 200, 30);
        
        JButton signBtn = new JButton("Sign up");
        signBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signBtn.setBackground(new Color(46, 204, 113));
        signBtn.setForeground(Color.WHITE);
        signBtn.setBounds(210, 200, 120, 40);
        signBtn.setFocusPainted(false);
        signBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
                JOptionPane.showMessageDialog(signfrm, "Please fill in all fields!", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            } else if (signName.length() < 3) {
                JOptionPane.showMessageDialog(signfrm, "Username must be at least 3 characters!", "Invalid Username", JOptionPane.WARNING_MESSAGE);
            } else if (signPass.length() < 4) {
                JOptionPane.showMessageDialog(signfrm, "Password must be at least 4 characters!", "Invalid Password", JOptionPane.WARNING_MESSAGE);
            } else {
                boolean exists = false;
                for (info acc : accounts) {
                    if (acc.uname.equalsIgnoreCase(signName)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    JOptionPane.showMessageDialog(signfrm, "Username already exists!", "Duplicate Username", JOptionPane.ERROR_MESSAGE);
                } else {
                    accounts.add(new info(signName, signPass));
                    JOptionPane.showMessageDialog(signfrm, "Account created successfully!\nYou can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    signfrm.dispose();
                }
            }
        });
        
        passfield.addActionListener(e -> signBtn.doClick());
    }
    
    void logIn() {
        JFrame logfrm = new JFrame("Log In - JTO");
        logfrm.setSize(550, 350);
        logfrm.setLocationRelativeTo(frm);
        logfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setBackground(Color.white);
        
        JLabel titleLbl = new JLabel("Login to JTO");
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLbl.setForeground(new Color(52, 152, 219));
        titleLbl.setBounds(200, 30, 200, 30);
        
        JLabel userlbl = new JLabel("Enter Username:");
        userlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        userlbl.setBounds(100, 90, 130, 30);
        
        JLabel passlbl = new JLabel("Enter Password:");
        passlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passlbl.setBounds(100, 140, 130, 30);
        
        JTextField usertxt = new JTextField();
        usertxt.setFont(new Font("Arial", Font.PLAIN, 14));
        usertxt.setBounds(230, 90, 200, 30);
        
        JPasswordField passfield = new JPasswordField();
        passfield.setFont(new Font("Arial", Font.PLAIN, 14));
        passfield.setBounds(230, 140, 200, 30);
        
        JButton logBtn = new JButton("Log in");
        logBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logBtn.setBackground(new Color(52, 152, 219));
        logBtn.setForeground(Color.WHITE);
        logBtn.setBounds(210, 200, 120, 40);
        logBtn.setFocusPainted(false);
        logBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logPanel.add(titleLbl);
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
                JOptionPane.showMessageDialog(logfrm, "Login successful! Welcome " + lname, "Success", JOptionPane.INFORMATION_MESSAGE);
                logfrm.dispose();
                frm.dispose();
                new MainInterface(lname);
            } else {
                JOptionPane.showMessageDialog(logfrm, "Invalid credentials! Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passfield.setText("");
            }
        });
        
        passfield.addActionListener(e -> logBtn.doClick());
        usertxt.requestFocus();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnsign) signUp();
        else if (e.getSource() == btnlog) logIn();
    }
}

class MainInterface {
    String username;
    
    MainInterface(String username) {
        this.username = username;
        mainFrame();
    }
    
    void mainFrame() {
        JFrame mainFrame = new JFrame("JTO - Main Menu");
        mainFrame.setSize(900, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel main = new JPanel();
        main.setLayout(null);
        main.setBackground(new Color(240, 248, 255));
        
        JLabel lblWelcome = new JLabel("Welcome, " + username + "!");
        lblWelcome.setBounds(300, 50, 400, 40);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(0, 102, 204));
        
        JLabel lblHead = new JLabel("JEEPNEY TERMINAL ORGANIZER");
        lblHead.setBounds(230, 110, 500, 40);
        lblHead.setFont(new Font("Arial", Font.BOLD, 24));
        lblHead.setForeground(Color.DARK_GRAY);
        
        JButton startBtn = new JButton("START");
        startBtn.setFont(new Font("Georgia", Font.BOLD, 28));
        startBtn.setBackground(new Color(46, 204, 113));
        startBtn.setForeground(Color.WHITE);
        startBtn.setBounds(330, 300, 200, 60);
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton exitBtn = new JButton("LOGOUT");
        exitBtn.setFont(new Font("Georgia", Font.BOLD, 20));
        exitBtn.setBackground(new Color(231, 76, 60));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setBounds(350, 400, 160, 45);
        exitBtn.setFocusPainted(false);
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        main.add(lblWelcome);
        main.add(lblHead);
        main.add(startBtn);
        main.add(exitBtn);
        mainFrame.add(main);
        mainFrame.setVisible(true);
        
        startBtn.addActionListener(e -> {
            mainFrame.dispose();
            new JeepneyTerminalGUI(username);
        });
        
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                mainFrame.dispose();
                new AccountManager();
            }
        });
    }
}

class JeepneyTerminalGUI extends JFrame {
    private CircularQueue jeepneyQueue;
    private JTextField txtJeepneyId;
    private JTextArea txtDisplay, txtDispatched;
    private JButton btnAdd, btnDispatch, btnShow, btnClear, btnExit, btnFinish, btnLogout;
    private JLabel lblStatus, lblQueueInfo, lblUser;
    private List<String> dispatchedList = new ArrayList<>();
    private String username;
    
    public JeepneyTerminalGUI(String username) {
        this.username = username;
        setTitle("Jeepney Terminal System - JTO");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        int size = getQueueSize();
        if (size > 0) {
            jeepneyQueue = new CircularQueue(size);
            initializeComponents();
            setupLayout();
            attachListeners();
            updateDisplay();
            updateDispatchedDisplay();
            setVisible(true);
        }
    }
    
    private int getQueueSize() {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, "Enter maximum number of jeepneys in queue:", "Queue Size Configuration", JOptionPane.QUESTION_MESSAGE);
                if (input == null) {
                    dispose();
                    new MainInterface(username);
                    return -1;
                }
                int size = Integer.parseInt(input.trim());
                if (size <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number greater than 0.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                return size;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void initializeComponents() {
        lblUser = new JLabel("User: " + username);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUser.setForeground(Color.DARK_GRAY);
        
        txtJeepneyId = new JTextField(15);
        txtJeepneyId.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnAdd = new JButton("Add Jeepney");
        btnDispatch = new JButton("Dispatch");
        btnShow = new JButton("Show Queue");
        btnClear = new JButton("Clear Display");
        btnFinish = new JButton("Mark Finished");
        btnExit = new JButton("Main Menu");
        btnLogout = new JButton("Logout");
        
        styleButton(btnAdd, new Color(46, 204, 113));
        styleButton(btnDispatch, new Color(52, 152, 219));
        styleButton(btnShow, new Color(155, 89, 182));
        styleButton(btnClear, new Color(241, 196, 15));
        styleButton(btnFinish, new Color(127, 140, 141));
        styleButton(btnExit, new Color(52, 73, 94));
        styleButton(btnLogout, new Color(231, 76, 60));
        
        lblStatus = new JLabel("Welcome to Jeepney Terminal System!", JLabel.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        lblStatus.setForeground(Color.DARK_GRAY);
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        lblQueueInfo = new JLabel("Queue: 0/" + jeepneyQueue.getMaxSize(), JLabel.CENTER);
        lblQueueInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblQueueInfo.setForeground(new Color(0, 102, 204));
        lblQueueInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        txtDisplay = new JTextArea(13, 32);
        txtDisplay.setEditable(false);
        txtDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtDisplay.setLineWrap(true);
        txtDisplay.setWrapStyleWord(true);
        txtDisplay.setBackground(new Color(245, 245, 245));
        txtDisplay.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        txtDispatched = new JTextArea(13, 25);
        txtDispatched.setEditable(false);
        txtDispatched.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtDispatched.setLineWrap(true);
        txtDispatched.setWrapStyleWord(true);
        txtDispatched.setBackground(new Color(245, 245, 245));
        txtDispatched.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(135, 36));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("Jeepney Terminal Queue", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 21));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.add(lblUser);
        userPanel.setOpaque(false);
        
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(lblQueueInfo, BorderLayout.SOUTH);
        topPanel.add(userPanel, BorderLayout.NORTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel lblId = new JLabel("Jeepney ID:");
        lblId.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblId);
        inputPanel.add(txtJeepneyId);
        inputPanel.add(btnAdd);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Jeepney"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        buttonPanel.add(btnDispatch);
        buttonPanel.add(btnShow);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnFinish);
        
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        navPanel.add(btnExit);
        navPanel.add(btnLogout);
        
        JPanel midPanel = new JPanel(new BorderLayout(5, 5));
        midPanel.add(inputPanel, BorderLayout.NORTH);
        midPanel.add(buttonPanel, BorderLayout.CENTER);
        midPanel.add(navPanel, BorderLayout.SOUTH);
        
        JPanel queuePanel = new JPanel(new BorderLayout(8, 8));
        queuePanel.add(new JScrollPane(txtDisplay), BorderLayout.CENTER);
        queuePanel.setBorder(BorderFactory.createTitledBorder("Waiting Jeepneys"));
        
        JPanel dispatchedPanel = new JPanel(new BorderLayout(8, 8));
        dispatchedPanel.add(new JScrollPane(txtDispatched), BorderLayout.CENTER);
        dispatchedPanel.setBorder(BorderFactory.createTitledBorder("Jeepneys Still Driving"));
        
        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.add(queuePanel);
        listsPanel.add(dispatchedPanel);
        listsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(midPanel, BorderLayout.NORTH);
        centerPanel.add(listsPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);
    }
    
    private void attachListeners() {
        btnAdd.addActionListener(e -> addJeepney());
        btnDispatch.addActionListener(e -> dispatchJeepney());
        btnShow.addActionListener(e -> updateDisplay());
        btnClear.addActionListener(e -> {
            txtDisplay.setText("");
            lblStatus.setText("Display cleared.");
        });
        btnFinish.addActionListener(e -> markFinished());
        btnExit.addActionListener(e -> {
            dispose();
            new MainInterface(username);
        });
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(JeepneyTerminalGUI.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new AccountManager();
            }
        });
        txtJeepneyId.addActionListener(e -> addJeepney());
    }
    
    private void addJeepney() {
        try {
            String id = txtJeepneyId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Jeepney ID.", "Empty Input", JOptionPane.WARNING_MESSAGE);
                txtJeepneyId.requestFocus();
                return;
            }
            if (dispatchedList.contains(id) || containsInQueue(id)) {
                JOptionPane.showMessageDialog(this, "Jeepney ID already exists in the system.", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                txtJeepneyId.setText("");
                txtJeepneyId.requestFocus();
                return;
            }
            jeepneyQueue.enqueue(id);
            lblStatus.setText("Jeepney " + id + " added to the queue.");
            lblStatus.setForeground(new Color(46, 204, 113));
            txtJeepneyId.setText("");
            txtJeepneyId.requestFocus();
            updateQueueInfo();
            updateDisplay();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Queue Full", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText(ex.getMessage());
            lblStatus.setForeground(new Color(231, 76, 60));
        }
    }
    
    private boolean containsInQueue(String id) {
        String[] arr = jeepneyQueue.getQueue();
        for (String s : arr) {
            if (id.equals(s)) return true;
        }
        return false;
    }
    
    private void dispatchJeepney() {
        try {
            String dispatched = jeepneyQueue.dequeue();
            dispatchedList.add(dispatched);
            lblStatus.setText("Jeepney " + dispatched + " dispatched and is now driving!");
            lblStatus.setForeground(new Color(52, 152, 219));
            updateQueueInfo();
            updateDisplay();
            updateDispatchedDisplay();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
            lblStatus.setText(ex.getMessage());
            lblStatus.setForeground(new Color(241, 196, 15));
        }
    }
    
    private void markFinished() {
        if (dispatchedList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No jeepneys currently dispatched.", "No Dispatched Jeepneys", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String jeepneyId = JOptionPane.showInputDialog(this, "Enter Jeepney ID to mark as finished:", "Mark Finished", JOptionPane.QUESTION_MESSAGE);
        if (jeepneyId == null) return;
        jeepneyId = jeepneyId.trim();
        if (jeepneyId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Jeepney ID.", "Empty Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (dispatchedList.remove(jeepneyId)) {
            lblStatus.setText("Jeepney " + jeepneyId + " marked as finished.");
            lblStatus.setForeground(new Color(127, 140, 141));
            updateDispatchedDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Jeepney ID not found in dispatched list.", "Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateDisplay() {
        if (jeepneyQueue.isEmpty()) {
            txtDisplay.setText("===============================\n   No jeepneys in queue\n===============================\n");
            lblStatus.setText("Queue is empty.");
            lblStatus.setForeground(Color.GRAY);
            return;
        }
        String[] queue = jeepneyQueue.getQueue();
        String display = "===============================\n      CURRENT QUEUE STATUS\n===============================\n\n";
        for (int i = 0; i < queue.length; i++) {
            display = display + "  " + (i + 1) + ". Jeepney ID: " + queue[i] + "\n";
        }
        display = display + "\n===============================\n  Total: " + jeepneyQueue.getCurrentSize() + "/" + jeepneyQueue.getMaxSize() + " jeepneys\n===============================\n";
        txtDisplay.setText(display);
        lblStatus.setText("Queue displayed successfully.");
        lblStatus.setForeground(new Color(155, 89, 182));
    }
    
    private void updateDispatchedDisplay() {
        String text = "=======================\n  Jeepneys Still Driving\n=======================\n\n";
        for (int i = 0; i < dispatchedList.size(); i++) {
            text = text + "  " + (i + 1) + ". Jeepney ID: " + dispatchedList.get(i) + "\n";
        }
        if (dispatchedList.isEmpty()) {
            text = text + "  No jeepneys dispatched yet.\n";
        }
        text = text + "=======================\n";
        txtDispatched.setText(text);
    }
    
    private void updateQueueInfo() {
        lblQueueInfo.setText("Queue: " + jeepneyQueue.getCurrentSize() + "/" + jeepneyQueue.getMaxSize());
        double fillPercentage = (double) jeepneyQueue.getCurrentSize() / jeepneyQueue.getMaxSize();
        if (fillPercentage >= 0.9) {
            lblQueueInfo.setForeground(new Color(231, 76, 60));
        } else if (fillPercentage >= 0.7) {
            lblQueueInfo.setForeground(new Color(241, 196, 15));
        } else {
            lblQueueInfo.setForeground(new Color(46, 204, 113));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AccountManager();
        });
    }
}
