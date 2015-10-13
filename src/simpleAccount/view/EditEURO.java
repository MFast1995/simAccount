package simpleAccount.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import simpleAccount.controller.CalculatorController;
import simpleAccount.model.CalculatorModel;
import simpleAccount.model.ModelEvent;
import simpleAccount.model.userAccnt;


public class EditEURO extends JFrameView{
	ArrayList <userAccnt> group = new ArrayList<userAccnt>();
	DecimalFormat df = new DecimalFormat("###########0.00");
	private JFrame euroframe;
	private JButton depositE;
	private JButton withdrawE;
	private JButton dismissE;
	private JLabel labelA = new JLabel("Available Funds:");
	private JLabel labelEE = new JLabel("Editable in EURO:");
	private JTextField euroTextField = new JTextField();
	private JTextField euroTextFieldTwo = new JTextField();
	HandlerE handlerE = new HandlerE();
	JPanel euroPanel;
	private String euroAmount;
	int userIndex;
	double amount = 0;
	
	//Euro View
	public EditEURO(CalculatorModel model, CalculatorController controller, ArrayList<userAccnt> groupP, int userIndexP){
		super(model, controller);
		userIndex = userIndexP;
		group = groupP;
		String holder = group.get(userIndex).getFirstName() + " " + group.get(userIndex).getLastName() + " " + group.get(userIndex).getId();
		System.out.println("hell:" + holder);
		euroframe = new JFrame(holder);
		euroPanel = new JPanel();
		depositE = new JButton("Deposit");
		withdrawE = new JButton("Withdraw");
		dismissE = new JButton("Dismiss");
		depositE.addActionListener(handlerE);
		withdrawE.addActionListener(handlerE);
		dismissE.addActionListener(handlerE);
		euroAmount = df.format(group.get(userIndex).getAmountEuro());
		euroTextField.setText(euroAmount);
		euroTextField.setEditable(false);
		euroTextFieldTwo.setText("0.00");
		euroTextFieldTwo.setEditable(true);
		euroTextFieldTwo.addActionListener(handlerE);
		labelA.setOpaque(true);
		labelEE.setOpaque(true);
		euroPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(euroPanel, BorderLayout.CENTER);
		euroPanel.add(labelA, null);
		euroPanel.add(euroTextField, null);
		euroPanel.add(labelEE, null);
		euroPanel.add(euroTextFieldTwo, null);
		euroPanel.add(depositE, null);
		euroPanel.add(withdrawE, null);
		euroPanel.add(dismissE, null);
		pack();
		euroframe.add(euroPanel, null);
		euroframe.pack();
		euroframe.setVisible(true);
	}
	
	//handles all the interactions in edit euro
	class HandlerE implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == euroTextFieldTwo){
				String temp = euroTextFieldTwo.getText();
				amount = Double.parseDouble(temp);
				System.out.println(temp);
			}else if(ee.getSource() == depositE){
				if(amount > 1){
					((CalculatorController)getController()).operation("EUROD", group, userIndex, amount, 0, null);
					System.out.println("deposit EURO");
				}
				euroAmount = "0.00";
				amount = 0;
				euroTextFieldTwo.setText(euroAmount);
			}else if(ee.getSource() == withdrawE){
				if(amount > 1 && amount <= group.get(userIndex).getAmountEuro()){
					((CalculatorController)getController()).operation("EUROW", group, userIndex, amount, 0, null);
					System.out.println("withdraw EURO");
				}else if (amount > group.get(userIndex).getAmountEuro()){
					((CalculatorController)getController()).operation("EUROWERR", group, userIndex, amount, 0, null);
				}
				euroAmount = "0.00";
				amount = 0;
				euroTextFieldTwo.setText(euroAmount);
			}else if(ee.getSource() == dismissE){
				System.out.println("dismiss EURO");
				euroframe.dispose();
			}
		}
	}
	
	//this updates the amount displayed
	public void modelChanged(ModelEvent event) {
		euroAmount = df.format(event.getAmount()*.92);
		euroTextField.setText(euroAmount);
	}
	
}
