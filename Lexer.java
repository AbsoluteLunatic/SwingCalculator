import java.util.ArrayList;

public class Lexer {
    private Calculator calc;
private ArrayList<Token> compress(ArrayList<Token> tokens)
{
    int i,j = 0;
    for (i = 0; i < tokens.size(); i++)
    {
        if(tokens.get(i).getOpt()==Operator_Type.answer)
           tokens.set(i,this.calc.getAnswer());
    }
    ArrayList<Token> signedTokens = new ArrayList<Token>();
    i=1;
    if(tokens.get(0).getOpt()==Operator_Type.subtraction&&tokens.get(1).getOpt()==Operator_Type.non_operator)
    {
        signedTokens.add(tokens.get(1).negateInt());
        i++;
    }
    while(i < tokens.size()-1)
    {
        if(tokens.get(i).getOpt()==Operator_Type.subtraction&&tokens.get(i-1).getOpt()!=Operator_Type.non_operator)
        {
            if(tokens.get(i+1).getTokt()==Token_Type.floatNum)

                signedTokens.add(tokens.get(i+1).negateFloat());
            else
                signedTokens.add(tokens.get(i+1).negateInt());
            i++;
        }
        else signedTokens.add(tokens.get(i));
        i++;
    }
    ArrayList<Token> fixedTokens = new ArrayList<Token>(tokens.size());
    fixedTokens.add(signedTokens.get(0));
    i = 1;
    while(i<signedTokens.size())
    {
        if(signedTokens.get(i).getTokt()!=Token_Type.operator&&fixedTokens.get(j).getTokt()==Token_Type.integer)
        {
            fixedTokens.set(j,fixedTokens.get(j).addDigit(signedTokens.get(i).getInteger()));
        }
        else if(fixedTokens.get(j).getTokt()==Token_Type.floatNum)
        {
            fixedTokens.set(j,fixedTokens.get(j).addDecimalDigit(signedTokens.get(i).getInteger()));
        }
        else if(signedTokens.get(i).getOpt()==Operator_Type.float_point)
        {
            fixedTokens.set(j,fixedTokens.get(j).switchIntToFloat());
        }
        else
        {
            fixedTokens.add(signedTokens.get(i));
            j++;
        }
        i++;
    }
    return fixedTokens;
}
private Token evaluate(ArrayList<Token> tokens)
{
 if(tokens.size()==1)
     return tokens.remove(0);
 ArrayList<Token> newExp = new ArrayList<Token>();
 for(int i = 0; i<tokens.size();i++) {
     if (tokens.get(i).getOpt() == Operator_Type.open_parentheses) {
         int j = tokens.size() - 1;
         while (j > 0 && tokens.get(j).getOpt() != Operator_Type.close_parentheses)
             j--;
         ArrayList<Token> inPar = new ArrayList<Token>();
         for (int k = i + 1; k < j; k++)
             inPar.add(tokens.get(k));
         Token value = evaluate(inPar);
         for (int k = 0; k < tokens.size(); k++) {
             if (k == i) {
                 newExp.add(value);
                 k = j;
             } else
                 newExp.add(tokens.get(k));
         }
         return evaluate(newExp);
     }
 }
 for(int i = 0; i<tokens.size();i++)
     {
         if(tokens.get(i).getOpt() == Operator_Type.power)
         {
             Token num = tokens.get(i-1),pow = tokens.get(i+1);
             if(num.getTokt()==Token_Type.integer)
                 num=num.switchIntToFloat();
             if(pow.getTokt()==Token_Type.integer)
                 pow=num.switchIntToFloat();
             Token res = new Token(Math.pow(num.getFloatNum(),pow.getFloatNum()));
             for (int j = 0; j<tokens.size();j++)
             {
                 if(j==i-1)
                 {
                     newExp.add(res);
                     j+=2;
                 }
                 else
                     newExp.add(tokens.get(j));
             }
             return evaluate(newExp);
         }
         if(tokens.get(i).getOpt()==Operator_Type.power_e)
         {
             Token pow = tokens.get(i+1);
             if(pow.getTokt()==Token_Type.integer)
                 pow=pow.switchIntToFloat();
             Token res = new Token(Math.exp(pow.getFloatNum()));
             for (int j = 0; j<tokens.size(); j++)
                 if(j == i)
                 {
                     newExp.add(res);
                     j++;

                 }
                else
                    newExp.add(tokens.get(j));
                return evaluate(newExp);
         }
         if(tokens.get(i).getOpt()==Operator_Type.root)
         {
             Token root = tokens.get(i+1);
             if(root.getTokt()==Token_Type.integer)
                 root = root.switchIntToFloat();
             Token res = new Token(Math.sqrt(root.getFloatNum()));
             for (int j = 0; j<tokens.size(); j++)
                 if(j == i)
                 {
                     newExp.add(res);
                     j++;

                 }
                 else
                     newExp.add(tokens.get(j));
             return evaluate(newExp);
         }
         if(tokens.get(i).getOpt()==Operator_Type.logarithm)
         {
             Token log = tokens.get(i+1);
             if(log.getTokt()==Token_Type.integer)
                 log = log.switchIntToFloat();
             Token res = new Token(Math.log10(log.getFloatNum()));
             for (int j = 0; j<tokens.size(); j++)
                 if(j == i)
                 {
                     newExp.add(res);
                     j++;

                 }
                 else
                     newExp.add(tokens.get(j));
             return evaluate(newExp);
         }
         if(tokens.get(i).getOpt()==Operator_Type.lan)
         {
             Token lan = tokens.get(i+1);
             if(lan.getTokt()==Token_Type.integer)
                 lan = lan.switchIntToFloat();
             Token res = new Token(Math.log(lan.getFloatNum()));
             for (int j = 0; j<tokens.size(); j++)
                 if(j == i)
                 {
                     newExp.add(res);
                     j++;

                 }
                 else
                     newExp.add(tokens.get(j));
             return evaluate(newExp);
         }
     }
 for (int i = 0; i<tokens.size();i++)
 {
     if(tokens.get(i).getOpt()==Operator_Type.sine)
     {
         Token sin = tokens.get(i+1);
         if(sin.getTokt()==Token_Type.integer)
             sin = sin.switchIntToFloat();
         Token res = new Token(Math.sin(sin.getFloatNum()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
     if(tokens.get(i).getOpt()==Operator_Type.cosine)
     {
         Token cos = tokens.get(i+1);
         if(cos.getTokt()==Token_Type.integer)
             cos = cos.switchIntToFloat();
         Token res = new Token(Math.cos(cos.getFloatNum()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
     if(tokens.get(i).getOpt()==Operator_Type.tangent)
     {
         Token tan = tokens.get(i+1);
         if(tan.getTokt()==Token_Type.integer)
             tan = tan.switchIntToFloat();
         Token res = new Token(Math.tan(tan.getFloatNum()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
     if(tokens.get(i).getOpt()==Operator_Type.invert_sine)
     {
         Token arcsin = tokens.get(i+1);
         if(arcsin.getTokt()==Token_Type.integer)
             arcsin = arcsin.switchIntToFloat();
         Token res = new Token(Math.asin(arcsin.getFloatNum()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
     if(tokens.get(i).getOpt()==Operator_Type.invert_cosine)
     {
         Token arccos = tokens.get(i+1);
         if(arccos.getTokt()==Token_Type.integer)
             arccos = arccos.switchIntToFloat();
         Token res = new Token(Math.acos(arccos.getFloatNum()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
     if(tokens.get(i).getOpt()==Operator_Type.invert_tangent)
     {
         Token arctan = tokens.get(i+1);
         if(arctan.getTokt()==Token_Type.integer)
             arctan = arctan.switchIntToFloat();
         Token res = new Token(Math.atan(arctan.getFloatNum()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;
             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
 }
 for (int i = 1; i< tokens.size();i++)
 {
     if(tokens.get(i).getOpt()==Operator_Type.multiplication)
     {
         Token res = getResMul(tokens, i);
         for (int j = 0; j<tokens.size(); j++)
             if(j == i-1)
             {
                 newExp.add(res);
                 j+=2;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);

     }
     if(tokens.get(i).getOpt()==Operator_Type.division)
     {
         Token res = getResDiv(tokens, i);
         for (int j = 0; j<tokens.size(); j++)
             if(j == i-1)
             {
                 newExp.add(res);
                 j+=2;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);

     }
     if(tokens.get(i).getOpt()==Operator_Type.factorial)
     {
         Token factorial = tokens.get(i-1);
         Token res = new Token(factorial(factorial.getInteger()));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i-1)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
     if(tokens.get(i).getOpt()==Operator_Type.percentage)
     {
         Token percent = tokens.get(i-1);
         if(percent.getTokt()==Token_Type.integer)
             percent = percent.switchIntToFloat();
         Token res = new Token((percent.getFloatNum()/100));
         for (int j = 0; j<tokens.size(); j++)
             if(j == i)
             {
                 newExp.add(res);
                 j++;

             }
             else
                 newExp.add(tokens.get(j));
         return evaluate(newExp);
     }
 }
    for (int i = 1; i< tokens.size();i++)
    {
        if(tokens.get(i).getOpt()==Operator_Type.addition)
        {
            Token res = getResAdd(tokens, i);
            for (int j = 0; j<tokens.size(); j++)
                if(j == i-1)
                {
                    newExp.add(res);
                    j+=2;

                }
                else
                    newExp.add(tokens.get(j));
            return evaluate(newExp);

        }
        if(tokens.get(i).getOpt()==Operator_Type.subtraction)
        {
            Token res = getResSub(tokens, i);
            for (int j = 0; j<tokens.size(); j++)
                if(j == i-1)
                {
                    newExp.add(res);
                    j+=2;

                }
                else
                    newExp.add(tokens.get(j));
            return evaluate(newExp);
        }
    }

 return evaluate(newExp);
}

    private static Token getResMul(ArrayList<Token> tokens, int i) {
        Token num1 = tokens.get(i -1),num2 = tokens.get(i +1),res;
        if(num1.getTokt()!=num2.getTokt())
        {
            if(num1.getTokt()==Token_Type.integer)
                num1 = num1.switchIntToFloat();
            else
                num2 = num2.switchIntToFloat();
            res = new Token(num1.getFloatNum()* num2.getFloatNum());
        }
        else if(num1.getTokt()== Token_Type.integer)
            res = new Token(num1.getInteger()*num2.getInteger());
        else res = new Token(num1.getFloatNum()*num2.getFloatNum());
        return res;
    }

    private static Token getResSub(ArrayList<Token> tokens, int i) {
        Token num1 = tokens.get(i -1),num2 = tokens.get(i +1),res;
        if(num1.getTokt()!=num2.getTokt())
        {
            if(num1.getTokt()==Token_Type.integer)
                num1 = num1.switchIntToFloat();
            else
                num2 = num2.switchIntToFloat();
            res = new Token(num1.getFloatNum()- num2.getFloatNum());
        }
        else if(num1.getTokt()== Token_Type.integer)
            res = new Token(num1.getInteger()-num2.getInteger());
        else res = new Token(num1.getFloatNum()-num2.getFloatNum());
        return res;
    }

    private static Token getResDiv(ArrayList<Token> tokens, int i) {
        Token num1 = tokens.get(i -1),num2 = tokens.get(i +1),res;
            if(num1.getTokt()==Token_Type.integer)
                num1 = num1.switchIntToFloat();
             if(num2.getTokt()==Token_Type.integer)
                num2 = num2.switchIntToFloat();
            res = new Token(num1.getFloatNum()/ num2.getFloatNum());
        return res;
    }

    private static Token getResAdd(ArrayList<Token> tokens, int i) {
        Token num1 = tokens.get(i -1),num2 = tokens.get(i +1),res;
        if(num1.getTokt()!=num2.getTokt())
        {
            if(num1.getTokt()==Token_Type.integer)
                num1 = num1.switchIntToFloat();
            else
                num2 = num2.switchIntToFloat();
            res = new Token(num1.getFloatNum()+ num2.getFloatNum());
        }
        else if(num1.getTokt()== Token_Type.integer)
            res = new Token(num1.getInteger()+num2.getInteger());
        else res = new Token(num1.getFloatNum()+num2.getFloatNum());
        return res;
    }

    private int factorial(int n)
    {
        if (n==1||n==0)
        {
            return 1;
        }
        return n*factorial(n-1);

    }
    public Token calculate(ArrayList<Token> tokens,Calculator calc)
{
    this.calc = calc;
    if(tokens.isEmpty())
        return new Token(0);
    if(tokens.size() == 1)
        return tokens.remove(0);
    return this.evaluate(this.compress(tokens));
}

}
