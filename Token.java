public class Token {
    private Token_Type tokt;
private Operator_Type opt;
private int integer;
private double floatNum;

    public Token(Operator_Type opt) {
        this.opt = opt;
        this.tokt=Token_Type.operator;
        this.integer=0;
        this.floatNum=0;
    }

    public Token(int integer) {
        this.integer = integer;
        this.tokt = Token_Type.integer;
        this.floatNum = 0;
        this.opt = Operator_Type.non_operator;
    }

    public Token(double floatNum) {
        this.floatNum = floatNum;
        this.tokt=Token_Type.floatNum;
        this.integer = 0;
        this.opt = Operator_Type.non_operator;
    }
    public Token addDigit(int digit)
    {
        this.integer*=10;
        if(this.integer>0)
        this.integer+=digit;
        else this.integer-=digit;
        return this;
    }
    public Token addDecimalDigit(int digit)
    {
       this.floatNum = Double.parseDouble(String.valueOf(this.floatNum).concat(String.valueOf(digit)));
       return this;
    }
    public Token negateInt()
    {
        this.integer*=-1;
        return this;
    }
    public Token negateFloat()
    {
        this.floatNum*=-1;
        return this;
    }
public Token switchIntToFloat()
{
    this.floatNum = (double)this.integer;
    this.integer = 0;
    this.tokt = Token_Type.floatNum;
    return this;
}
    public Token_Type getTokt() {
        return this.tokt;
    }

    public Operator_Type getOpt() {
        return this.opt;
    }

    public int getInteger() {
        return this.integer;
    }

    public double getFloatNum() {
        return this.floatNum;
    }
}
