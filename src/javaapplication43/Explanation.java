package explanation;

// Import all the GUI (Swing) libraries we need to make windows, buttons, text fields, etc.
import javax.swing.*;
// Import AWT libraries for colors, fonts, layouts, and cursor styles
import java.awt.*;
// Import event listeners so we can detect when users click buttons or press Enter
import java.awt.event.*;
// Import ArrayList and List to store collections of objects (like accounts, jeepneys)
import java.util.ArrayList;
import java.util.List;

// ==================== USER ACCOUNT CLASS ====================
// This is a simple class to store a username and password for each user
// Think of it like a "record" or "profile" for each person who can log in
class info {
    // These are the two pieces of information we store for each account
    String uname, pass;  // uname = username, pass = password
    
    // This is the constructor (the function that runs when you create a new info object)
    // It takes two parameters: the username and password you want to save
    info(String uname, String pass) {
        this.uname = uname;  // "this.uname" means "this object's username field"
        this.pass = pass;    // "this.pass" means "this object's password field"
    }
}

// ==================== CIRCULAR QUEUE CLASS ====================
// This class implements a circular queue data structure
// A queue is like a line at the store: first person in line is first to be served (FIFO = First In First Out)
// "Circular" means when we reach the end of the array, we wrap back to the beginning
// We use this to manage jeepneys waiting in line to be dispatched
class CircularQueue {
    // Private variables - only this class can access them directly
    private String[] queue;      // This array stores the jeepney IDs that are waiting
    private int front;           // This points to the position of the FIRST jeepney in line
    private int rear;            // This points to the position of the LAST jeepney in line
    private int maxSize;         // The maximum number of jeepneys that can wait (the queue capacity)
    private int currentSize;     // How many jeepneys are currently waiting in the queue
    
    // Constructor: This runs when we create a new CircularQueue
    // The "size" parameter tells us how big to make the queue
    public CircularQueue(int size) {
        maxSize = size;                  // Save the maximum size
        queue = new String[maxSize];     // Create an array with that many slots
        front = -1;                      // -1 means "queue is empty" (no front yet)
        rear = -1;                       // -1 means "queue is empty" (no rear yet)
        currentSize = 0;                 // Start with 0 jeepneys in the queue
    }
    
    // This method checks if the queue is completely full
    // Returns true if we've reached maximum capacity, false otherwise
    public boolean isFull() {
        return currentSize == maxSize;   // If current equals max, we're full!
    }
    
    // This method checks if the queue is empty
    // Returns true if there are no jeepneys waiting, false otherwise
    public boolean isEmpty() {
        return currentSize == 0;         // If current is 0, nobody is waiting
    }
    
    // This method adds a new jeepney to the END of the queue
    // "enqueue" is the technical term for "add to queue"
    public void enqueue(String jeepneyId) {
        // First, check if the queue is full - if so, we can't add more
        if (isFull()) {
            // Throw an error message that will be caught and shown to the user
            throw new RuntimeException("Queue is full. Cannot add jeepney: " + jeepneyId);
        }
        
        // If the queue was empty, set front to 0 (the first position)
        if (isEmpty()) {
            front = 0;
        }
        
        // Move rear forward by 1, but wrap around if we reach the end
        // The % (modulo) operator does the wrapping: (4+1)%5=0, so position 4 wraps to 0
        rear = (rear + 1) % maxSize;
        
        // Put the jeepney ID at the rear position
        queue[rear] = jeepneyId;
        
        // Increase the count of jeepneys in queue
        currentSize++;
    }
    
    // This method removes and returns the jeepney at the FRONT of the queue
    // "dequeue" is the technical term for "remove from queue"
    public String dequeue() {
        // First, check if the queue is empty - if so, we can't remove anything
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty. No jeepney to dispatch.");
        }
        
        // Get the jeepney ID at the front position
        String jeepneyId = queue[front];
        
        // Clear that position (good practice, not strictly necessary)
        queue[front] = null;
        
        // Check if this was the last jeepney in the queue
        if (front == rear) {
            // If front and rear were at the same position, the queue is now empty
            front = -1;  // Reset to "empty" state
            rear = -1;   // Reset to "empty" state
        } else {
            // Move front forward by 1, wrapping around if needed
            front = (front + 1) % maxSize;
        }
        
        // Decrease the count of jeepneys in queue
        currentSize--;
        
        // Return the jeepney ID we removed
        return jeepneyId;
    }
    
    // This method returns an array of all jeepney IDs currently in the queue
    // We use this to display the queue to the user
    public String[] getQueue() {
        // If the queue is empty, return an empty array
        if (isEmpty()) return new String[0];
        
        // Create a new array to hold the results (sized exactly to current queue size)
        String[] result = new String[currentSize];
        
        // Start at position 0 in the result array
        int index = 0;
        
        // Start at the front of the queue
        int i = front;
        
        // Loop through all items in the queue
        while (index < currentSize) {
            // Copy the item at position i into the result array
            result[index++] = queue[i];
            
            // Move to the next position, wrapping around if needed
            i = (i + 1) % maxSize;
        }
        
        // Return the array of jeepney IDs
        return result;
    }
    
    // Simple getter: returns how many jeepneys are currently waiting
    public int getCurrentSize() {
        return currentSize;
    }
    
    // Simple getter: returns the maximum capacity of the queue
    public int getMaxSize() {
        return maxSize;
    }
}

// ==================== ACCOUNT MANAGER (LOGIN/SIGNUP SCREEN) ====================
// This class creates the first screen users see: the welcome screen with login/signup buttons
// It also handles creating accounts and checking login credentials
class AccountManager implements ActionListener {
    // ArrayList to store all user accounts (both admin and regular users)
    ArrayList<info> accounts = new ArrayList<>();
    
    // The main window (frame) for this screen
    JFrame frm;
    
    // The two main buttons: one for logging in, one for signing up
    JButton btnlog, btnsign;
    
    // Constructor: This runs when we create a new AccountManager object
    AccountManager() {
        // Add two default accounts for testing/demo purposes
        accounts.add(new info("admin", "admin123"));  // Admin account
        accounts.add(new info("user", "pass"));       // Regular user account
        
        // Create the main window with a title
        frm = new JFrame("Jeepney Terminal Organizer");
        
        // Set the window size (width=1000 pixels, height=600 pixels)
        frm.setSize(1000, 600);
        
        // Center the window on the screen (null means "center it")
        frm.setLocationRelativeTo(null);
        
        // Make the program exit completely when this window is closed
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a panel to hold all our components (labels, buttons, etc.)
        JPanel mainPanel = new JPanel();
        
        // Use absolute positioning (null layout) so we can place things exactly where we want
        mainPanel.setLayout(null);
        
        // Set the background color to a nice light blue
        mainPanel.setBackground(new Color(0, 191, 255));
        
        // Create the "Welcome to" label
        JLabel lbl1 = new JLabel("Welcome to");
        
        // Position it: x=350, y=50, width=400, height=80
        lbl1.setBounds(350, 50, 400, 80);
        
        // Make the font large and bold (Georgia font, bold style, 45 point size)
        lbl1.setFont(new Font("Georgia", Font.BOLD, 45));
        
        // Set the text color to a darker blue
        lbl1.setForeground(new Color(0, 102, 204));
        
        // Create the "JTO" label (the main title/logo)
        JLabel lbl2 = new JLabel("JTO");
        lbl2.setBounds(430, 120, 200, 80);
        lbl2.setFont(new Font("Georgia", Font.BOLD, 56));  // Even bigger font!
        lbl2.setForeground(new Color(0, 102, 204));
        
        // Create the subtitle that explains what JTO stands for
        JLabel subtitle = new JLabel("Jeepney Terminal Organizer");
        subtitle.setBounds(330, 190, 400, 30);
        subtitle.setFont(new Font("Georgia", Font.PLAIN, 20));  // Smaller, non-bold
        subtitle.setForeground(Color.DARK_GRAY);
        
        // Create the "Log in" button
        btnlog = new JButton("Log in");
        btnlog.setFont(new Font("Georgia", Font.BOLD, 35));
        btnlog.setBackground(new Color(52, 152, 219));  // Blue background
        btnlog.setForeground(Color.WHITE);               // White text
        btnlog.setBounds(370, 280, 250, 70);            // Position and size
        btnlog.setFocusPainted(false);                  // Remove the focus border
        btnlog.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        
        // Create the "Sign up" button
        btnsign = new JButton("Sign up");
        btnsign.setFont(new Font("Georgia", Font.BOLD, 35));
        btnsign.setBackground(new Color(46, 204, 113));  // Green background
        btnsign.setForeground(Color.WHITE);
        btnsign.setBounds(370, 380, 250, 70);
        btnsign.setFocusPainted(false);
        btnsign.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add all the labels and buttons to the panel
        mainPanel.add(lbl1);
        mainPanel.add(lbl2);
        mainPanel.add(subtitle);
        mainPanel.add(btnlog);
        mainPanel.add(btnsign);
        
        // Add the panel to the frame (window)
        frm.add(mainPanel);
        
        // Make the window visible
        frm.setVisible(true);
        
        // Register this class as the listener for button clicks
        // When a button is clicked, the actionPerformed method (below) will be called
        btnsign.addActionListener(this);
        btnlog.addActionListener(this);
    }
    
    // This method is called when the "Sign up" button is clicked
    void signUp() {
        // Create a new window for the sign-up form
        JFrame signfrm = new JFrame("Sign Up - JTO");
        signfrm.setSize(550, 350);
        signfrm.setLocationRelativeTo(frm);  // Center it on the main window
        
        // DISPOSE_ON_CLOSE means only this window closes, not the whole app
        signfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create a panel for the sign-up form
        JPanel signPanel = new JPanel();
        signPanel.setLayout(null);           // Absolute positioning again
        signPanel.setBackground(Color.white); // White background
        
        // Create the "Create Account" title label
        JLabel crtlbl = new JLabel("Create Account");
        crtlbl.setFont(new Font("Georgia", Font.BOLD, 22));
        crtlbl.setForeground(new Color(46, 204, 113));  // Green color to match button
        crtlbl.setBounds(180, 30, 200, 30);
        
        // Create the "Enter Username:" label
        JLabel userlbl = new JLabel("Enter Username:");
        userlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        userlbl.setBounds(100, 90, 130, 30);
        
        // Create the "Enter Password:" label
        JLabel passlbl = new JLabel("Enter Password:");
        passlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passlbl.setBounds(100, 140, 130, 30);
        
        // Create the text field where the user types their username
        JTextField usertxt = new JTextField();
        usertxt.setFont(new Font("Arial", Font.PLAIN, 14));
        usertxt.setBounds(230, 90, 200, 30);
        
        // Create the password field (text is hidden with dots or asterisks)
        JPasswordField passfield = new JPasswordField();
        passfield.setFont(new Font("Arial", Font.PLAIN, 14));
        passfield.setBounds(230, 140, 200, 30);
        
        // Create the "Sign up" button for this form
        JButton signBtn = new JButton("Sign up");
        signBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signBtn.setBackground(new Color(46, 204, 113));
        signBtn.setForeground(Color.WHITE);
        signBtn.setBounds(210, 200, 120, 40);
        signBtn.setFocusPainted(false);
        signBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add all components to the sign-up panel
        signPanel.add(crtlbl);
        signPanel.add(userlbl);
        signPanel.add(passlbl);
        signPanel.add(usertxt);
        signPanel.add(passfield);
        signPanel.add(signBtn);
        
        // Add the panel to the sign-up window
        signfrm.add(signPanel);
        
        // Show the sign-up window
        signfrm.setVisible(true);
        
        // Add a listener to the sign-up button
        // This uses a "lambda expression" (the "e ->" part) which is a short way to write a function
        signBtn.addActionListener(e -> {
            // Get the text the user entered, and trim any extra spaces
            String signName = usertxt.getText().trim();
            
            // Get the password (convert from char array to String, then trim)
            String signPass = new String(passfield.getPassword()).trim();
            
            // Check if either field is empty
            if (signName.isEmpty() || signPass.isEmpty()) {
                // Show an error message popup
                JOptionPane.showMessageDialog(signfrm, "Please fill in all fields!", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            } 
            // Check if username is too short
            else if (signName.length() < 3) {
                JOptionPane.showMessageDialog(signfrm, "Username must be at least 3 characters!", "Invalid Username", JOptionPane.WARNING_MESSAGE);
            } 
            // Check if password is too short
            else if (signPass.length() < 4) {
                JOptionPane.showMessageDialog(signfrm, "Password must be at least 4 characters!", "Invalid Password", JOptionPane.WARNING_MESSAGE);
            } 
            // If all checks pass, proceed to create the account
            else {
                // Check if the username already exists
                boolean exists = false;
                
                // Loop through all existing accounts
                for (info acc : accounts) {
                    // Compare usernames (ignoring uppercase/lowercase differences)
                    if (acc.uname.equalsIgnoreCase(signName)) {
                        exists = true;  // Username is already taken
                        break;          // Stop searching
                    }
                }
                
                // If username is already taken, show error
                if (exists) {
                    JOptionPane.showMessageDialog(signfrm, "Username already exists!", "Duplicate Username", JOptionPane.ERROR_MESSAGE);
                } 
                // Otherwise, create the new account
                else {
                    // Add the new account to our list
                    accounts.add(new info(signName, signPass));
                    
                    // Show success message
                    JOptionPane.showMessageDialog(signfrm, "Account created successfully!\nYou can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Close the sign-up window
                    signfrm.dispose();
                }
            }
        });
        
        // Also trigger the sign-up button when user presses Enter in the password field
        // This is a convenience feature so users don't have to click the button
        passfield.addActionListener(e -> signBtn.doClick());
    }
    
    // This method is called when the "Log in" button is clicked
    void logIn() {
        // Create a new window for the login form
        JFrame logfrm = new JFrame("Log In - JTO");
        logfrm.setSize(550, 350);
        logfrm.setLocationRelativeTo(frm);
        logfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create a panel for the login form
        JPanel logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setBackground(Color.white);
        
        // Create the "Login to JTO" title
        JLabel titleLbl = new JLabel("Login to JTO");
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLbl.setForeground(new Color(52, 152, 219));  // Blue to match login button
        titleLbl.setBounds(200, 30, 200, 30);
        
        // Create the username label
        JLabel userlbl = new JLabel("Enter Username:");
        userlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        userlbl.setBounds(100, 90, 130, 30);
        
        // Create the password label
        JLabel passlbl = new JLabel("Enter Password:");
        passlbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passlbl.setBounds(100, 140, 130, 30);
        
        // Create the username text field
        JTextField usertxt = new JTextField();
        usertxt.setFont(new Font("Arial", Font.PLAIN, 14));
        usertxt.setBounds(230, 90, 200, 30);
        
        // Create the password field
        JPasswordField passfield = new JPasswordField();
        passfield.setFont(new Font("Arial", Font.PLAIN, 14));
        passfield.setBounds(230, 140, 200, 30);
        
        // Create the "Log in" button for this form
        JButton logBtn = new JButton("Log in");
        logBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logBtn.setBackground(new Color(52, 152, 219));
        logBtn.setForeground(Color.WHITE);
        logBtn.setBounds(210, 200, 120, 40);
        logBtn.setFocusPainted(false);
        logBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add all components to the login panel
        logPanel.add(titleLbl);
        logPanel.add(userlbl);
        logPanel.add(passlbl);
        logPanel.add(usertxt);
        logPanel.add(passfield);
        logPanel.add(logBtn);
        
        // Add the panel to the login window
        logfrm.add(logPanel);
        
        // Show the login window
        logfrm.setVisible(true);
        
        // Add a listener to the login button
        logBtn.addActionListener(e -> {
            // Get the username and password entered by the user
            String lname = usertxt.getText().trim();
            String lpass = new String(passfield.getPassword()).trim();
            
            // Flag to track if we find a matching account
            boolean found = false;
            
            // Loop through all accounts to check if credentials match
            for (info acc : accounts) {
                // Check if username AND password match (ignoring case for username)
                if (lname.equalsIgnoreCase(acc.uname) && lpass.equals(acc.pass)) {
                    found = true;  // Match found!
                    break;         // Stop searching
                }
            }
            
            // If we found a matching account
            if (found) {
                // Show success message
                JOptionPane.showMessageDialog(logfrm, "Login successful! Welcome " + lname, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Close the login window
                logfrm.dispose();
                
                // Close the welcome window
                frm.dispose();
                
                // Open the main menu with the username
                new MainInterface(lname);
            } 
            // If credentials don't match any account
            else {
                // Show error message
                JOptionPane.showMessageDialog(logfrm, "Invalid credentials! Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                
                // Clear the password field so user can try again
                passfield.setText("");
            }
        });
        
        // Also trigger login when user presses Enter in the password field
        passfield.addActionListener(e -> logBtn.doClick());
        
        // Put the cursor in the username field automatically
        usertxt.requestFocus();
    }
    
    // This method is called automatically when any button we're listening to is clicked
    // The "ActionEvent e" parameter tells us which button was clicked
    public void actionPerformed(ActionEvent e) {
        // Check which button was the source of the event
        if (e.getSource() == btnsign) {
            // If it was the sign-up button, call the signUp method
            signUp();
        } 
        else if (e.getSource() == btnlog) {
            // If it was the login button, call the logIn method
            logIn();
        }
    }
}

// ==================== MAIN INTERFACE (MAIN MENU) ====================
// This class creates the main menu screen that appears after successful login
// It shows a welcome message and two buttons: START and LOGOUT
class MainInterface {
    // Store the username of the logged-in user
    String username;
    
    // Constructor: takes the username as a parameter
    MainInterface(String username) {
        this.username = username;  // Save the username
        mainFrame();               // Call the method to build the interface
    }
    
    // This method creates and displays the main menu
    void mainFrame() {
        // Create the main menu window
        JFrame mainFrame = new JFrame("JTO - Main Menu");
        mainFrame.setSize(900, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a panel for the main menu
        JPanel main = new JPanel();
        main.setLayout(null);
        main.setBackground(new Color(240, 248, 255));  // Very light blue background
        
        // Create the welcome message with the user's name
        JLabel lblWelcome = new JLabel("Welcome, " + username + "!");
        lblWelcome.setBounds(300, 50, 400, 40);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(0, 102, 204));
        
        // Create the "JEEPNEY TERMINAL ORGANIZER" header
        JLabel lblHead = new JLabel("JEEPNEY TERMINAL ORGANIZER");
        lblHead.setBounds(230, 110, 500, 40);
        lblHead.setFont(new Font("Arial", Font.BOLD, 24));
        lblHead.setForeground(Color.DARK_GRAY);
        
        // Create the big green "START" button
        JButton startBtn = new JButton("START");
        startBtn.setFont(new Font("Georgia", Font.BOLD, 28));
        startBtn.setBackground(new Color(46, 204, 113));  // Green
        startBtn.setForeground(Color.WHITE);
        startBtn.setBounds(330, 300, 200, 60);
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create the red "LOGOUT" button
        JButton exitBtn = new JButton("LOGOUT");
        exitBtn.setFont(new Font("Georgia", Font.BOLD, 20));
        exitBtn.setBackground(new Color(231, 76, 60));  // Red
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setBounds(350, 400, 160, 45);
        exitBtn.setFocusPainted(false);
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add all components to the panel
        main.add(lblWelcome);
        main.add(lblHead);
        main.add(startBtn);
        main.add(exitBtn);
        
        // Add the panel to the window
        mainFrame.add(main);
        
        // Show the window
        mainFrame.setVisible(true);
        
        // Add listener to the START button
        startBtn.addActionListener(e -> {
            // Close the main menu
            mainFrame.dispose();
            
            // Open the terminal/queue management system
            new JeepneyTerminalGUI(username);
        });
        
        // Add listener to the LOGOUT button
        exitBtn.addActionListener(e -> {
            // Show a confirmation dialog to make sure they want to logout
            int confirm = JOptionPane.showConfirmDialog(
                mainFrame, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION
            );
            
            // If they clicked YES
            if (confirm == JOptionPane.YES_OPTION) {
                // Close the main menu
                mainFrame.dispose();
                
                // Go back to the login/welcome screen
                new AccountManager();
            }
            // If they clicked NO, do nothing (dialog just closes)
        });
    }
}

// ==================== JEEPNEY TERMINAL GUI (THE MAIN SYSTEM) ====================
// This is the main terminal system where users manage the jeepney queue
// It shows two lists: jeepneys waiting in queue, and jeepneys that have been dispatched
class JeepneyTerminalGUI extends JFrame {
    // The circular queue that stores jeepneys waiting to be dispatched
    private CircularQueue jeepneyQueue;
    
    // Text field where user types the jeepney ID to add
    private JTextField txtJeepneyId;
    
    // Two text areas: one shows the waiting queue, one shows dispatched jeepneys
    private JTextArea txtDisplay, txtDispatched;
    
    // All the buttons for various actions
    private JButton btnAdd, btnDispatch, btnShow, btnClear, btnExit, btnFinish, btnLogout;
    
    // Labels for status messages and queue information
    private JLabel lblStatus, lblQueueInfo, lblUser;
    
    // ArrayList to store IDs of jeepneys that have been dispatched
    private List<String> dispatchedList = new ArrayList<>();
    
    // Store the username of the current user
    private String username;
    
    // Constructor: creates and sets up the entire terminal interface
    public JeepneyTerminalGUI(String username) {
        this.username = username;  // Save the username
        
        // Set window properties
        setTitle("Jeepney Terminal System - JTO");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center on screen
        setResizable(false);          // Don't allow resizing (keeps layout intact)
        
        // Ask the user how big they want the queue to be
        int size = getQueueSize();
        
        // If they entered a valid size (greater than 0)
        if (size > 0) {
            // Create the queue with that size
            jeepneyQueue = new CircularQueue(size);
            
            // Initialize all the GUI components (buttons, labels, text areas)
            initializeComponents();
            
            // Arrange all components in the window
            setupLayout();
            
            // Connect buttons to their functions
            attachListeners();
            
            // Show the initial (empty) queue display
            updateDisplay();
            
            // Show the initial (empty) dispatched list
            updateDispatchedDisplay();
            
            // Make the window visible
            setVisible(true);
        }
        // If size is -1 or less (user canceled), the window won't be shown
    }
    
    // This method asks the user to enter the queue size
    // It keeps asking until they enter a valid number or cancel
    private int getQueueSize() {
        // Loop forever (we'll break out when we get valid input or cancel)
        while (true) {
            try {
                // Show an input dialog asking for the queue size
                String input = JOptionPane.showInputDialog(
                    null,  // No parent window (centered on screen)
                    "Enter maximum number of jeepneys in queue:",
                    "Queue Size Configuration",
                    JOptionPane.QUESTION_MESSAGE
                );
                
                // If user clicked Cancel or closed the dialog
                if (input == null) {
                    // Close this window
                    dispose();
                    
                    // Go back to the main menu
                    new MainInterface(username);
                    
                    // Return -1 to indicate cancellation
                    return -1;
                }
                
                // Try to convert the input string to an integer
                int size = Integer.parseInt(input.trim());
                
                // Check if the number is valid (must be positive)
                if (size <= 0) {
                    // Show error message
                    JOptionPane.showMessageDialog(
                        null,
                        "Please enter a positive number greater than 0.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    // Go back to the start of the loop to ask again
                    continue;
                }
                
                // If we get here, the size is valid - return it
                return size;
                
            } catch (NumberFormatException e) {
                // If the input wasn't a number, show error message
                JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
                );
                // Loop continues to ask again
            }
        }
    }
    
    // This method creates all the GUI components (buttons, labels, text fields)
    private void initializeComponents() {
        // Create label showing the current user
        lblUser = new JLabel("User: " + username);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUser.setForeground(Color.DARK_GRAY);
        
        // Create text field for entering jeepney ID
        txtJeepneyId = new JTextField(15);  // 15 columns wide
        txtJeepneyId.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Create all the buttons
        btnAdd = new JButton("Add Jeepney");
        btnDispatch = new JButton("Dispatch");
        btnShow = new JButton("Show Queue");
        btnClear = new JButton("Clear Display");
        btnFinish = new JButton("Mark Finished");
        btnExit = new JButton("Main Menu");
        btnLogout = new JButton("Logout");
        
        // Apply styling to each button (colors, fonts, etc.)
        styleButton(btnAdd, new Color(46, 204, 113));       // Green
        styleButton(btnDispatch, new Color(52, 152, 219));  // Blue
        styleButton(btnShow, new Color(155, 89, 182));      // Purple
        styleButton(btnClear, new Color(241, 196, 15));     // Yellow
        styleButton(btnFinish, new Color(127, 140, 141));   // Gray
        styleButton(btnExit, new Color(52, 73, 94));        // Dark gray
        styleButton(btnLogout, new Color(231, 76, 60));     // Red
        
        // Create status label (shows messages at the bottom)
        lblStatus = new JLabel("Welcome to Jeepney Terminal System!", JLabel.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        lblStatus.setForeground(Color.DARK_GRAY);
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Create queue info label (shows "Queue: X/Y" at the top)
        lblQueueInfo = new JLabel("Queue: 0/" + jeepneyQueue.getMaxSize(), JLabel.CENTER);
        lblQueueInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblQueueInfo.setForeground(new Color(0, 102, 204));
        lblQueueInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Create text area for displaying the queue
        txtDisplay = new JTextArea(13, 32);  // 13 rows, 32 columns
        txtDisplay.setEditable(false);       // User can't type in it
        txtDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));  // Monospaced for alignment
        txtDisplay.setLineWrap(true);        // Wrap long lines
        txtDisplay.setWrapStyleWord(true);   // Wrap at word boundaries
        txtDisplay.setBackground(new Color(245, 245, 245));
        txtDisplay.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Create text area for displaying dispatched jeepneys
        txtDispatched = new JTextArea(13, 25);
        txtDispatched.setEditable(false);
        txtDispatched.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtDispatched.setLineWrap(true);
        txtDispatched.setWrapStyleWord(true);
        txtDispatched.setBackground(new Color(245, 245, 245));
        txtDispatched.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    }
    
    // Helper method to apply consistent styling to buttons
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);         // Set background color
        button.setForeground(Color.WHITE);   // White text
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);       // Remove focus border
        button.setBorderPainted(false);      // Remove button border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Hand cursor on hover
        button.setPreferredSize(new Dimension(135, 36));   // Standard size
    }
    
    // This method arranges all components in the window using layout managers
    private void setupLayout() {
        // Use BorderLayout for the main frame (divides into 5 regions)
        setLayout(new BorderLayout(10, 10));  // 10 pixel gaps
        
        // === TOP PANEL (title and queue info) ===
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Create the title label
        JLabel lblTitle = new JLabel("Jeepney Terminal Queue", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 21));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        // Create a panel for the user label (top right corner)
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.add(lblUser);
        userPanel.setOpaque(false);  // Transparent background
        
        // Add components to top panel
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(lblQueueInfo, BorderLayout.SOUTH);
        topPanel.add(userPanel, BorderLayout.NORTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        
        // === INPUT PANEL (for adding jeepneys) ===
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel lblId = new JLabel("Jeepney ID:");
        lblId.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblId);
        inputPanel.add(txtJeepneyId);
        inputPanel.add(btnAdd);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Jeepney"));
        
        // === BUTTON PANEL (main action buttons) ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        buttonPanel.add(btnDispatch);
        buttonPanel.add(btnShow);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnFinish);
        
        // === NAVIGATION PANEL (Main Menu and Logout buttons) ===
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        navPanel.add(btnExit);
        navPanel.add(btnLogout);
        
        // === MIDDLE PANEL (combines input, buttons, and navigation) ===
        JPanel midPanel = new JPanel(new BorderLayout(5, 5));
        midPanel.add(inputPanel, BorderLayout.NORTH);
        midPanel.add(buttonPanel, BorderLayout.CENTER);
        midPanel.add(navPanel, BorderLayout.SOUTH);
        
        // === QUEUE PANEL (waiting jeepneys list) ===
        JPanel queuePanel = new JPanel(new BorderLayout(8, 8));
        queuePanel.add(new JScrollPane(txtDisplay), BorderLayout.CENTER);
        queuePanel.setBorder(BorderFactory.createTitledBorder("Waiting Jeepneys"));
        
        // === DISPATCHED PANEL (driving jeepneys list) ===
        JPanel dispatchedPanel = new JPanel(new BorderLayout(8, 8));
        dispatchedPanel.add(new JScrollPane(txtDispatched), BorderLayout.CENTER);
        dispatchedPanel.setBorder(BorderFactory.createTitledBorder("Jeepneys Still Driving"));
        
        // === LISTS PANEL (side by side: queue and dispatched) ===
        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.add(queuePanel);
        listsPanel.add(dispatchedPanel);
        listsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // === CENTER PANEL (combines middle section and lists) ===
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(midPanel, BorderLayout.NORTH);
        centerPanel.add(listsPanel, BorderLayout.CENTER);
        
        // Add all main panels to the frame
        add(topPanel, BorderLayout.NORTH);      // Top: title and queue info
        add(centerPanel, BorderLayout.CENTER);   // Center: everything else
        add(lblStatus, BorderLayout.SOUTH);      // Bottom: status message
    }
    
    // This method connects each button to its corresponding function
    private void attachListeners() {
        // When Add button is clicked, call addJeepney()
        btnAdd.addActionListener(e -> addJeepney());
        
        // When Dispatch button is clicked, call dispatchJeepney()
        btnDispatch.addActionListener(e -> dispatchJeepney());
        
        // When Show Queue button is clicked, call updateDisplay()
        btnShow.addActionListener(e -> updateDisplay());
        
        // When Clear button is clicked, clear the display
        btnClear.addActionListener(e -> {
            txtDisplay.setText("");
            lblStatus.setText("Display cleared.");
        });
        
        // When Mark Finished button is clicked, call markFinished()
        btnFinish.addActionListener(e -> markFinished());
        
        // When Main Menu button is clicked, go back to main menu
        btnExit.addActionListener(e -> {
            dispose();                    // Close this window
            new MainInterface(username);  // Open main menu
        });
        
        // When Logout button is clicked, confirm and then logout
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                JeepneyTerminalGUI.this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();              // Close this window
                new AccountManager();   // Go back to login screen
            }
        });
        
        // Also trigger Add when user presses Enter in the text field
        txtJeepneyId.addActionListener(e -> addJeepney());
    }
    
    // This method adds a jeepney to the queue
    private void addJeepney() {
        try {
            // Get the ID entered by the user
            String id = txtJeepneyId.getText().trim();
            
            // Check if the field is empty
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Jeepney ID.",
                    "Empty Input",
                    JOptionPane.WARNING_MESSAGE
                );
                txtJeepneyId.requestFocus();  // Put cursor back in the field
                return;
            }
            
            // Check if this ID is already in use (either in queue or dispatched)
            if (dispatchedList.contains(id) || containsInQueue(id)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Jeepney ID already exists in the system.",
                    "Duplicate ID",
                    JOptionPane.WARNING_MESSAGE
                );
                txtJeepneyId.setText("");     // Clear the field
                txtJeepneyId.requestFocus();  // Put cursor back
                return;
            }
            
            // Add the jeepney to the queue
            jeepneyQueue.enqueue(id);
            
            // Update the status message (green color for success)
            lblStatus.setText("Jeepney " + id + " added to the queue.");
            lblStatus.setForeground(new Color(46, 204, 113));
            
            // Clear the input field
            txtJeepneyId.setText("");
            
            // Put cursor back in the field for next entry
            txtJeepneyId.requestFocus();
            
            // Update the queue info label (Queue: X/Y)
            updateQueueInfo();
            
            // Refresh the display to show the new jeepney
            updateDisplay();
            
        } catch (RuntimeException ex) {
            // If the queue is full, show error message
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Queue Full",
                JOptionPane.ERROR_MESSAGE
            );
            
            // Update status with error (red color)
            lblStatus.setText(ex.getMessage());
            lblStatus.setForeground(new Color(231, 76, 60));
        }
    }
    
    // Helper method: checks if an ID is already in the queue
    private boolean containsInQueue(String id) {
        // Get all jeepneys currently in the queue
        String[] arr = jeepneyQueue.getQueue();
        
        // Loop through and check each one
        for (String s : arr) {
            if (id.equals(s)) {
                return true;  // Found it!
            }
        }
        
        return false;  // Not found
    }
    
    // This method dispatches (removes from queue and marks as "driving")
    private void dispatchJeepney() {
        try {
            // Remove the jeepney from the front of the queue
            String dispatched = jeepneyQueue.dequeue();
            
            // Add it to the list of dispatched jeepneys
            dispatchedList.add(dispatched);
            
            // Update status message (blue color)
            lblStatus.setText("Jeepney " + dispatched + " dispatched and is now driving!");
            lblStatus.setForeground(new Color(52, 152, 219));
            
            // Update queue info (Queue: X/Y)
            updateQueueInfo();
            
            // Refresh both displays
            updateDisplay();
            updateDispatchedDisplay();
            
        } catch (RuntimeException ex) {
            // If the queue is empty, show error
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Queue Empty",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Update status with warning (yellow/orange color)
            lblStatus.setText(ex.getMessage());
            lblStatus.setForeground(new Color(241, 196, 15));
        }
    }
    
    // This method marks a dispatched jeepney as finished (removes from "driving" list)
    private void markFinished() {
        // Check if there are any dispatched jeepneys
        if (dispatchedList.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No jeepneys currently dispatched.",
                "No Dispatched Jeepneys",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        // Ask the user which jeepney ID to mark as finished
        String jeepneyId = JOptionPane.showInputDialog(
            this,
            "Enter Jeepney ID to mark as finished:",
            "Mark Finished",
            JOptionPane.QUESTION_MESSAGE
        );
        
        // If user clicked Cancel, do nothing
        if (jeepneyId == null) return;
        
        // Trim spaces from input
        jeepneyId = jeepneyId.trim();
        
        // Check if input is empty
        if (jeepneyId.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid Jeepney ID.",
                "Empty Input",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Try to remove the jeepney from the dispatched list
        if (dispatchedList.remove(jeepneyId)) {
            // If successful, update status (gray color)
            lblStatus.setText("Jeepney " + jeepneyId + " marked as finished.");
            lblStatus.setForeground(new Color(127, 140, 141));
            
            // Refresh the dispatched display
            updateDispatchedDisplay();
        } else {
            // If ID not found in list
            JOptionPane.showMessageDialog(
                this,
                "Jeepney ID not found in dispatched list.",
                "Not Found",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    // This method updates the waiting queue display
    private void updateDisplay() {
        // Check if queue is empty
        if (jeepneyQueue.isEmpty()) {
            // Show "empty" message
            txtDisplay.setText("===============================\n   No jeepneys in queue\n===============================\n");
            lblStatus.setText("Queue is empty.");
            lblStatus.setForeground(Color.GRAY);
            return;
        }
        
        // Get all jeepneys in the queue
        String[] queue = jeepneyQueue.getQueue();
        
        // Build the display text (using string concatenation)
        String display = "===============================\n";
        display = display + "      CURRENT QUEUE STATUS\n";
        display = display + "===============================\n\n";
        
        // Add each jeepney to the display (numbered list)
        for (int i = 0; i < queue.length; i++) {
            display = display + "  " + (i + 1) + ". Jeepney ID: " + queue[i] + "\n";
        }
        
        // Add footer with total count
        display = display + "\n===============================\n";
        display = display + "  Total: " + jeepneyQueue.getCurrentSize() + "/" + jeepneyQueue.getMaxSize() + " jeepneys\n";
        display = display + "===============================\n";
        
        // Put the text in the display area
        txtDisplay.setText(display);
        
        // Update status message (purple color)
        lblStatus.setText("Queue displayed successfully.");
        lblStatus.setForeground(new Color(155, 89, 182));
    }
    
    // This method updates the dispatched jeepneys display
    private void updateDispatchedDisplay() {
        // Build the display text
        String text = "=======================\n";
        text = text + "  Jeepneys Still Driving\n";
        text = text + "=======================\n\n";
        
        // Add each dispatched jeepney (numbered list)
        for (int i = 0; i < dispatchedList.size(); i++) {
            text = text + "  " + (i + 1) + ". Jeepney ID: " + dispatchedList.get(i) + "\n";
        }
        
        // If no dispatched jeepneys, show message
        if (dispatchedList.isEmpty()) {
            text = text + "  No jeepneys dispatched yet.\n";
        }
        
        // Add footer
        text = text + "=======================\n";
        
        // Put the text in the display area
        txtDispatched.setText(text);
    }
    
    // This method updates the queue info label and its color
    private void updateQueueInfo() {
        // Update the text (Queue: X/Y format)
        lblQueueInfo.setText("Queue: " + jeepneyQueue.getCurrentSize() + "/" + jeepneyQueue.getMaxSize());
        
        // Calculate how full the queue is (as a percentage)
        double fillPercentage = (double) jeepneyQueue.getCurrentSize() / jeepneyQueue.getMaxSize();
        
        // Change color based on how full it is
        if (fillPercentage >= 0.9) {
            // 90% or more full: RED (warning!)
            lblQueueInfo.setForeground(new Color(231, 76, 60));
        } else if (fillPercentage >= 0.7) {
            // 70-89% full: YELLOW (caution)
            lblQueueInfo.setForeground(new Color(241, 196, 15));
        } else {
            // Less than 70% full: GREEN (all good)
            lblQueueInfo.setForeground(new Color(46, 204, 113));
        }
    }
}

// ==================== MAIN CLASS (PROGRAM ENTRY POINT) ====================
// This is where the program starts running
public class Explanation {
    // The main method - Java looks for this to start the program
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures GUI runs on the correct thread
        // This is a best practice for Swing applications
        SwingUtilities.invokeLater(() -> {
            // Create and show the login/welcome screen
            new AccountManager();
        });
    }
}
