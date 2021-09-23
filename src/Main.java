import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        StateMachine sm = new StateMachine();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a combination of {nickel, dime, quarter, cancel, wait}, with commands n, d, q, c, w");
        System.out.println("Enter \"quit\" to end simulation.");

        for(String inputString = input.next(); !inputString.equals("quit"); inputString = input.next())
            System.out.println(sm.tick(inputString));
    }
}
