//assueme there are no negaytive numbers and that the input is a single string without spaces

public class Task1 {

    public static int findFirstOperatorBefore(int index, String term){
        for(int k = index-1; k >=0; k--){
            if(term.charAt(k)=='+' || term.charAt(k)=='+'){
                return k;
            }
        }
        return -1;
    }
    public static int findFirstOperatorAfter(int index, String term){
        for(int k = index+1; k < term.length(); k++){
            if(term.charAt(k)=='+' || term.charAt(k)=='+'){
                return k;
            }
        }
        return term.length();
    }

    public static boolean validEquation(String equation){

        //without spaces, y should be the virst character
        if ((equation.indexOf("y")!=0 && equation.indexOf("Y")!=0) || equation.indexOf("Y",1)!=-1 ||
                equation.indexOf("y",1)!=-1) {
            System.out.println("Invalid, missing or misplaced y variable");
            return false;
        }
        else if (equation.indexOf("=")!=1) {
            System.out.println("Invalid, missing '=' character");
            return false;
        }
        else if (equation.indexOf("x")==-1 && equation.indexOf("X")==-1) {
            System.out.println("Invalid, missing x variable");
            return false;
        }
        return true;
    }

    public static int termCalculator(String term){

        while(term.length()!=0){
            //check for brackets
            int start = term.indexOf('(');
            if(start>=0){
                int end = term.indexOf(')');
                if(end<0) {
                    System.out.println("bad term");
                    return 0;
                }
                String subTerm = term.substring(start+1,end);
                int subNum = termCalculator(subTerm);
                //term now point to a new term string where the bracketed section is replaced with a number
                term = term.substring(0, start)+Integer.toString(subNum)+term.substring(end+1);
            }
            else{
                //check for operation in order of importance(left more important than right, * and / more important than + and -)
                //if no operands, than all that remains is the final value
                int firstm = term.indexOf('*');
                int firstd = term.indexOf('/');
                int firstp = term.indexOf('+');
                int firstmin = term.indexOf('-');
                int indexBefore;
                int indexAfter;
                int operand1;
                int operand2;
                int ret;
                int first = -1;
                //I would do unsigned char in c but Im not as familiar with java
                int type = -1; //0=division, 1=multiplication, 2=subtraction, 3=addition
                if(firstm>=0 || firstd>=0 || firstp >= 0 || firstmin >= 0) {
                    if (firstm >= 0 || firstd >= 0) {
                        if (firstm >= 0 && firstd >= 0) {
                            if (firstm > firstd) {
                                //do division
                                type = 0;
                                first = firstd;
                                //to find the number we need the indexes
                                // of the closes operators
                            } else {
                                //do multiplicaiton
                                type = 1;
                                first = firstm;
                            }
                        } else if (firstm >= 0) {
                            //do multiplicaiton
                            type = 1;
                            first = firstm;
                        } else {
                            //do division
                            type = 0;
                            first = firstd;
                        }
                    } else {
                        if (firstp >= 0 || firstmin >= 0) {
                            if (firstp >= 0 && firstmin >= 0) {
                                if (firstp > firstmin) {
                                    //do minus
                                    type = 2;
                                    first = firstmin;
                                    //to find the number we need the indexes
                                    // of the closes operators
                                } else {
                                    //do plus
                                    type = 3;
                                    first = firstp;
                                }
                            } else if (firstp >= 0) {
                                //do plus
                                type = 3;
                                first = firstp;
                            } else {
                                //do minus
                                type = 2;
                                first = firstmin;
                            }
                        }
                    }
                    indexBefore = findFirstOperatorBefore(first, term);
                    indexAfter = findFirstOperatorAfter(first, term);
                    //assume there are no adjacent operators
                    operand1 = Integer.parseInt(term.substring(indexBefore + 1, first));
                    operand2 = Integer.parseInt(term.substring(first + 1, indexAfter));
                    //perform operation
                    ret = (type == 0) ? (operand1 / operand2) : (type == 1) ? (operand1 * operand2) : (type == 2) ? (operand1 - operand2) : (operand1 + operand2);
                    term = term.substring(0, indexBefore + 1) + Integer.toString(ret) + term.substring(indexAfter);
                }
                else {
                    //raiming string must be final value
                    int retTemp = Integer.parseInt(term);
                    //set length to zero
                    term = "";
                    return retTemp;
                }
            }
        }
        return -1;//something went wrong
    }
    public static int equationCalculator(String equation, int xVal) {
        //remove spaces from string
        String equationLocal = equation.replaceAll(" ", "");
        //System.out.println(equationLocal);
        if (!validEquation(equationLocal))
            return -1;
        //'=' character is must be the second character when there are no spaces
        String term = equationLocal.substring(2);
        if(term.indexOf('x')!=-1){
            term = term.replaceFirst("x",Integer.toString(xVal));
        }else{
            term = term.replaceFirst("X",Integer.toString(xVal));
        }
        //System.out.println(equationLocal);
        //String term = equation.substring()
        return termCalculator(term);
    }


    public static void main(String[] args) {
        //assume there are no spaces in the input
        System.out.println(equationCalculator("y  = 10*x",10));
    }
}


