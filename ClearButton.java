import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButton extends JButton implements ActionListener{
    private Calculator calc;
    ClearButton(String label,Calculator calc)
    {
        super(label);
        this.calc= calc;
        this.calc.add(this);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String AcText=((ClearButton)e.getSource()).getLabel();
        if(AcText.equals("AC")) this.calc.clearText();
        else if(AcText.equals("DEL")) this.calc.deleteToken();
        else this.calc.Evaluate();
    }
}
