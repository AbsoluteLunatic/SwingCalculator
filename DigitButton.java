import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DigitButton extends JButton implements ActionListener {
    private Calculator calc;
    DigitButton(String label,Calculator calc)
    {
        super(label);
        this.calc= calc;
        this.calc.add(this);
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String Digit=((DigitButton)e.getSource()).getText();
        this.calc.addText(Digit);
        this.calc.addToken(new Token(Integer.parseInt(Digit)));

    }
}
