package model;

// Define class
public class Menu {
    // Define attributes
    private String header;
    private String[] menuArr;

    // Define constructors
    public Menu(String header) {
        this.header = header;
    }

    public Menu(String header, String[] menuArr) {
        this.header = header;
        this.menuArr = menuArr;
    }

    // Define getter methods 
    public String getHeader() {
        return this.header;
    }

    public String[] getMenuArr() {
        return this.menuArr;
    }

    // Define setter methods
    public void setHeader(String header) {
        this.header = header;
    }

    public void setMenuArr(String[] menuArr) {
        this.menuArr = menuArr;
    }

    // Define method to get formatted header
    public void getFormattedHeader() {
        System.out.println("");
        System.out.println("*".repeat(this.header.length() + 1));
        System.out.println(this.header);
        System.out.println("*".repeat(this.header.length() + 1));
    }

    public void getFormattedHeader(int menuArrItem) {
        System.out.println("");
        System.out.println("*".repeat(this.menuArr[menuArrItem].length() + 1));
        System.out.println(this.menuArr[menuArrItem]);
        System.out.println("*".repeat(this.menuArr[menuArrItem].length() + 1));
    }

    // Define method to get formatted menu
    public void getFormattedMenu(boolean goBackOption) {
        this.getFormattedHeader();

        System.out.println("Choose the option:\n");
        for (int i = 1; i <= this.menuArr.length; i++) {
            System.out.printf("%d. %s\n", i, menuArr[i - 1]);
        }

        if (goBackOption) {
            System.out.println("");
            System.out.println("(Go back: -1)");
            System.out.println("");
        }

        System.out.println("0. Exit\n");
    }
}
