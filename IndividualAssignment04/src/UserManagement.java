import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManagement {
    private static final String FILENAME = "users.txt";
    private static ArrayList<User> users = new ArrayList<>();

    public void run() {
        loadUsers();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("User Management");
            System.out.println("1. Add a user");
            System.out.println("2. List users");
            System.out.println("3. Update a user");
            System.out.println("4. Delete a user");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    listUsers();
                    break;
                case 3:
                    updateUser(scanner);
                    break;
                case 4:
                    deleteUser(scanner);
                    break;
            }
        } while (choice != 5);

        saveUsers();
    }

    private static void loadUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] chunks = line.split(",");
                if (chunks.length == 2) {
                    String username = chunks[0];
                    String email = chunks[1];
                    users.add(new User(username, email));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Loading users: " + e.toString());
        }
    }

    private static void saveUsers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME));
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getEmail());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Saving users: " + e.getMessage());
        }
    }

    private static void addUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        users.add(new User(username, email));
    }

    private static void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (int i = 0; i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i).getUsername() + " (" + users.get(i).getEmail() + ")");
            }
        }
    }

    private static void updateUser(Scanner scanner) {
        listUsers();
        if (users.isEmpty()) {
            return;
        }

        System.out.print("Enter the user number to update: ");
        int userNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the new username: ");
        String newUsername = scanner.next();
        scanner.nextLine();
        System.out.print("Enter the new email: ");
        String newEmail = scanner.nextLine();
        User updatedUser = new User(newUsername, newEmail);
        users.set(userNumber - 1, updatedUser);
    }

    private static void deleteUser(Scanner scanner) {
        listUsers();
        if (users.isEmpty()) {
            return;
        }

        System.out.print("Enter the user number to delete: ");
        int userNumber = scanner.nextInt();
        users.remove(userNumber - 1);
    }
}
