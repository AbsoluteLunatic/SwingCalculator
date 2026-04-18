import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Calculator extends JFrame {

    private JTextField display;
    private JPanel buttons;
    private Token answer = new Token(0);
    private ArrayList<Token> tokenStream;
    private Lexer lex;
    public Calculator(String frameText)
    {
        super(frameText);

        this.setTitle("Calculator");
        this.display=new JTextField("0");
        this.display.setBounds(0,0, 200,100);
        this.display.setEditable(false);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.lex = new Lexer();
        this.buttons = new JPanel();
        this.buttons.setLayout(new GridLayout(6, 6));
        String[] labels = {
                "sin", "cos","tan","sin⁻¹","cos⁻¹","tan⁻¹",
                "^","log","√","eˣ","ln","!",
                "7", "8", "9", "DEL", "AC","%",
                "4", "5", "6", "*", "÷","π",
                "1", "2", "3", "+", "-","e",
                "0", ".", "(", ")", "=","Ans"
        };
        for (String label : labels) {
            switch (label) {
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> this.buttons.add(new DigitButton(label, this));
                case "AC", "DEL", "=" -> this.buttons.add(new ClearButton(label, this));
                default -> this.buttons.add(new OperatorButton(label, this));
            }
        }
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        this.add(this.buttons, BorderLayout.CENTER);
        this.setVisible(true);
        this.tokenStream = new ArrayList<Token>();
    }

    public void addText(String text) {
       if (this.answer.getTokt() == Token_Type.floatNum && Objects.equals(this.display.getText(), String.valueOf(this.answer.getFloatNum())))
                this.display.setText(text);
            else if (this.answer.getTokt() == Token_Type.integer && Objects.equals(this.display.getText(), String.valueOf(this.answer.getInteger())))
                this.display.setText(text);
         else if (!this.display.getText().equals("0")) {
               String temp = this.display.getText().concat(text); this.display.setText(temp);

           }
        else
            this.display.setText(text);
    }

    public void clearText()
    {
        this.display.setText("0");
        this.tokenStream.clear();
    }
    private void delete(Token tok)
    {
        if(this.display.getText().length()>1||!this.display.getText().equals("0")) {
            int len = 1;
            if(tok.getTokt()==Token_Type.operator)
                len = switch (tok.getOpt()) {
                    case power_e, lan -> 2;
                    case sine, cosine, tangent, logarithm, answer -> 3;
                    case invert_sine, invert_cosine, invert_tangent -> 5;
                    default -> len;
                };
            if(this.display.getText().length()==1)
                this.display.setText("0");
            else
                this.display.setText(this.display.getText().substring(0,this.display.getText().length()-len));
            if(this.display.getText().isEmpty())
                this.display.setText("0");
             }
    }
    public void addToken(Token token)
    {
        this.tokenStream.add(token);
    }
    public void deleteToken()
    {
        if(!this.tokenStream.isEmpty()) {
           Token tok = this.tokenStream.remove(this.tokenStream.size() - 1);
           this.delete(tok);
        }
    }
    public void Evaluate()
    {
        this.answer = lex.calculate(this.tokenStream,this);
        this.clearText();
        if(this.answer.getTokt()==Token_Type.integer)
          this.addText(String.valueOf(this.answer.getInteger()));
        else this.addText(String.valueOf(this.answer.getFloatNum()));
    }

    public Token getAnswer() {
        return this.answer;
    }
}
