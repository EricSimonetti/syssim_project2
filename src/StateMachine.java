import java.util.regex.Pattern;

class StateMachine {
    private int q = 10, d = 10, n = 10, v = 0;
    private boolean c = false;

    String tick(String input){
        if(!Pattern.compile("[qdncw]*").matcher(input).matches()) return "Unrecognized input";

        String output = lambda();
        delta();

        if(!input.contains("w")) processInput(input); //you cant be waiting for the machine to process and also input

        return output;
    }

    private String lambda(){
        if(c)
            return produceChange();
        return produceCoffee();
    }

    private void delta(){
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

    private int[] change(){
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
        if(tempV != 0) return new int[0];
        return change;
    }

    private String produceChange(){
        if(v == 0) return "{nothing}";
        int[] change = change();
        if(change.length != 3) return "Could not make change! Sorry ):";

        String[] names = {"quarter", "dime", "nickel"};
        StringBuilder output = new StringBuilder("{");
        for(int i = 0; i < change.length; i++){
            if(change[i]!=0) {
                if (output.length() > 1) output.append(", ");

                if (change[i] == 1) output.append(names[i]);

                else output.append(change[i]).append(" ").append(names[i]).append("s");
            }
        }
        return output.append("}").toString();
    }

    private String produceCoffee(){
        if(v/100 > 0)
            return "{" + (v/100 == 1? "coffee}": v/100 + " coffees}");
        return "{nothing}";
    }
}
