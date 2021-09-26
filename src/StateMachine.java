import java.util.regex.Pattern;

class StateMachine {
    private int q = 10, d = 10, n = 10, v;
    private boolean c;

    String tick(String input) throws NoChangeException, BadInputException{
        if(!Pattern.compile("[qdncw]*").matcher(input).matches())
            throw new BadInputException("Unrecognized input");

        String output = lambda();
        delta(input);

        return output;
    }

    private String lambda() throws NoChangeException{
        StringBuilder output = new StringBuilder("{");
        if(c)
            produceChange(output);
        else
            produceCoffee(output);

        return output.toString();
    }

    private void delta(String input) throws NoChangeException{
        if(v/100 > 0) v = v%100;
        if(c && v>0){
            int[] change = change();
            if(change.length == 3){ //if we can calculate change
                q -= change[0];
                d -= change[1];
                n -= change[2];
                v = 0;
            }
        }
        c = false;
        processInput(input);
    }

    private void processInput(String input){
        for(char i : input.toCharArray())
            switch(i){
                case('q'):
                    q++;
                    v += 25;
                    break;
                case('d'):
                    d++;
                    v += 10;
                    break;
                case('n'):
                    n++;
                    v += 5;
                    break;
                case('c'): c = true;
            }
    }

    private int[] change() throws NoChangeException{
        int[] coins = {q, d, n};
        int[] values = {25, 10, 5};
        int[] change = new int[3];
        int tempV = v;

        for(int i = 0; i < change.length ; i++){
            while(tempV >= values[i] && coins[i] > 0){
                change[i]++;
                coins[i]--;
                tempV -= values[i];
            }
        }
        if(tempV != 0) throw new NoChangeException("Could not return change for your " + v + " cents");
        return change;
    }

    private void produceChange(StringBuilder output) throws NoChangeException{
        if(v == 0) {
            output.append("nothing}");
            return;
        }

        int[] change = change();
        String[] names = {"quarter", "dime", "nickel"};
        for(int i = 0; i < change.length; i++){
            if(change[i]!=0) {
                if (output.length() > 1) output.append(", ");

                if (change[i] == 1) output.append(names[i]);

                else output.append(change[i]).append(" ").append(names[i]).append("s");
            }
        }
        output.append("}");
    }

    private void produceCoffee(StringBuilder output){
        if (output.length() > 1) output.append(", ");
        if(v/100 > 0){
            if(v/100 != 1) output.append(v/100).append(" coffees");
            else output.append("coffee");
        }
        if(output.length() == 1) output.append("nothing");
        output.append("}");
    }
}
