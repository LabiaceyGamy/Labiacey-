import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {
    private JFrame frame;
    private JTextField textField;
    private String operator = "";
    private double firstNum = 0;
    private boolean isOperatorClicked = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.createCalculator();
        });
    }

    public void createCalculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(textField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("=")) {
                try {
                    double secondNum = Double.parseDouble(textField.getText());
                    double result = switch (operator) {
                        case "+" -> firstNum + secondNum;
                        case "-" -> firstNum - secondNum;
                        case "*" -> firstNum * secondNum;
                        case "/" -> secondNum != 0 ? firstNum / secondNum : Double.NaN;
                        default -> 0;
                    };
                    textField.setText(Double.isNaN(result) ? "Error" : String.valueOf(result));
                    isOperatorClicked = false;
                    operator = "";
                } catch (NumberFormatException ex) {
                    textField.setText("Error");
                }
            } else if ("0123456789.".contains(command)) {
                if (isOperatorClicked) {
                    textField.setText("");
                    isOperatorClicked = false;
                }
                textField.setText(textField.getText() + command);
            } else {
                try {
                    firstNum = Double.parseDouble(textField.getText());
                    operator = command;
                    isOperatorClicked = true;
                } catch (NumberFormatException ex) {
                    textField.setText("Error");
                }
            }
        }
    }
}
