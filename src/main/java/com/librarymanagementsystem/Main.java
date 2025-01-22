package com.librarymanagementsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Library library = new Library();

        List<LibraryItem> libraryItems = LibraryIO.loadItemsFromFile("itemlist.lms");
        for (LibraryItem item : libraryItems) {
            library.addItem(item);
        }

        List<User> users = LibraryIO.loadUserListFromFile("userlist.lms");
        for (User user : users) {
            library.addUser(user);
        }

        Map<String, String> borrowedItems = LibraryIO.loadBorrowedItemsFromFiles("borroweditems.lms");
        for (Map.Entry<String, String> borrowedItem : borrowedItems.entrySet()) {
            library.getBorrowedItems().put(borrowedItem.getKey(), borrowedItem.getValue());

        }

        System.out.println("Please find the list of all library items");
        library.getLibraryItems().forEach(item -> System.out.println(item.getTitle() + "\t" + item.getAuthor() + "\t" + item.getSerialNumber()));
        System.out.println("----------------------");

        System.out.println("Please find the list of all users");
        library.getUserList().forEach(user -> System.out.println(user.getName()));
        System.out.println("----------------------");

        System.out.println("Please find the list of all borrowed items from the library");
        library.getBorrowedItems().forEach((item, user) -> System.out.println(item + ":" + user));
        System.out.println("----------------------");

        boolean exit = false;
        L1:
        while (!exit) {
            System.out.println("Enter the main option ");
            System.out.println("1. Need to create a new item ");
            System.out.println("2. Need to create a new User ");
            System.out.println("3. User need to borrow an item ");
            System.out.println("4. User need to return an item ");
            System.out.println("5. Exit the application ");

            BufferedReader mainOption = new BufferedReader(new InputStreamReader(System.in));
            int mainOptionStr;
            try {
                mainOptionStr = Integer.parseInt(mainOption.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println(e.getMessage());
                continue;
            }



                if (mainOptionStr == 1) {
                    System.out.println("Which item do you need to create ?");
                    System.out.println("1. Book");
                    System.out.println("2. Magazine");

                    BufferedReader createItemType = new BufferedReader(new InputStreamReader(System.in));
                    int createItemTypeStr;
                    try {
                        createItemTypeStr = Integer.parseInt(createItemType.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (createItemTypeStr == 1) {
                        System.out.println("Please enter the book name you want to create");
                        String bookNameStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        System.out.println("Please enter the book author you want to create");
                        String bookAuthorStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        System.out.println("Please enter the book serial number you want to create");
                        String bookSerialNumberStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        for (LibraryItem li : library.getLibraryItems()) {
                            if (Objects.equals(li.getSerialNumber(), bookSerialNumberStr)) {
                                System.out.println("This serial number is already entered !");
                                continue L1;
                            }
                        }
                        LibraryItem createBook = new Book(bookNameStr, bookAuthorStr, bookSerialNumberStr);
                        library.addItem(createBook);

                    } else if (createItemTypeStr == 2) {
                        System.out.println("Please enter the magazine name you want to create");
                        String magazineNameStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        System.out.println("Please enter the magazine author you want to create");
                        String magazineAuthorStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        System.out.println("Please enter the magazine serial number you want to create");
                        String magazineSerialNumberStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        for (LibraryItem li : library.getLibraryItems()) {
                            if (Objects.equals(li.getSerialNumber(), magazineSerialNumberStr)) {
                                System.out.println("This serial number is already entered !");
                                continue L1;
                            }
                        }
                        LibraryItem createmagazine = new Book(magazineNameStr, magazineAuthorStr, magazineSerialNumberStr);
                        library.addItem(createmagazine);


                    }
                    else if (mainOptionStr == 2) {
                        System.out.println("Whsat is the name of the user ?");
                        String createUserName = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        for (User user: library.getUserList()
                        ) {
                            if(Objects.equals(user.getName(),createUserName)){
                                System.out.println("This user is already registered !");
                                continue L1;
                            }
                        }
                        User user = new User(createUserName);
                        library.addUser(user);






                    } else if (mainOptionStr == 5) {
                    exit = true;
                }


                LibraryIO.saveItemToFile(library.getLibraryItems(), "itemlist.lms");

            }
        }

    }
}
