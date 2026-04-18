import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorButton extends JButton implements ActionListener{
    private Calculator calc;

    OperatorButton(String label,Calculator calc)
    {
        super(label);
        this.calc= calc;
        this.calc.add(this);
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String Operator=((OperatorButton)e.getSource()).getLabel();
        this.calc.addText(Operator);
        switch (Operator) {
            case "+" -> this.calc.addToken(new Token(Operator_Type.addition));
            case "-" -> this.calc.addToken(new Token(Operator_Type.subtraction));
            case "*" -> this.calc.addToken(new Token(Operator_Type.multiplication));
            case "÷" -> this.calc.addToken(new Token(Operator_Type.division));
            case "%" -> this.calc.addToken(new Token(Operator_Type.percentage));
            case "^" -> this.calc.addToken(new Token(Operator_Type.power));
            case "√" -> this.calc.addToken(new Token(Operator_Type.root));
            case "log" -> this.calc.addToken(new Token(Operator_Type.logarithm));
            case "eˣ" -> this.calc.addToken(new Token(Operator_Type.power_e));
            case "ln" -> this.calc.addToken(new Token(Operator_Type.lan));
            case "(" -> this.calc.addToken(new Token(Operator_Type.open_parentheses));
            case ")" -> this.calc.addToken(new Token(Operator_Type.close_parentheses));
            case "!" -> this.calc.addToken(new Token(Operator_Type.factorial));
            case "." -> this.calc.addToken(new Token(Operator_Type.float_point));
            case "Ans" -> this.calc.addToken(new Token(Operator_Type.answer));
            case "e" -> this.calc.addToken(new Token(Math.exp(1)));
            case "π" -> this.calc.addToken(new Token(Math.PI));
            case "sin" -> this.calc.addToken(new Token(Operator_Type.sine));
            case "sin⁻¹" -> this.calc.addToken(new Token(Operator_Type.invert_sine));
            case "cos" -> this.calc.addToken(new Token(Operator_Type.cosine));
            case "cos⁻¹" -> this.calc.addToken(new Token(Operator_Type.invert_cosine));
            case "tan" -> this.calc.addToken(new Token(Operator_Type.tangent));
            case "tan⁻¹" -> this.calc.addToken(new Token(Operator_Type.invert_tangent));
        }

    }
}
