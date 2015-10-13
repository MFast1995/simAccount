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


public class EditUSD extends JFrameView{
	ArrayList<userAccnt> group = new ArrayList<userAccnt>();
	DecimalFormat df = new DecimalFormat("###########0.00");
	private JFrame usdframe;
	private JButton depositU;
	private JButton withdrawU;
	private JButton dismissU;
	private JLabel labelA = new JLabel("Available Funds:");
	private JLabel labelEU = new JLabel("Editable in USD:");
	private JTextField usdTextField = new JTextField();
	private JTextField usdTextFieldTwo = new JTextField();
	HandlerU handlerU = new HandlerU();
	JPanel usdPanel;
	String usdAmount;
	int userIndex;
	double amount = 0;
	
	//USD view
	public EditUSD(CalculatorModel model, CalculatorController controller, ArrayList<userAccnt> groupP, int userIndexP){
		super(model, controller);
		group = groupP;
		userIndex = userIndexP;
		String holder = group.get(userIndex).getFirstName() + " " + group.get(userIndex).getLastName() + " " + group.get(userIndex).getId();
		System.out.println("hell:" + holder);
		usdframe = new JFrame(holder);
		usdPanel = new JPanel();
		depositU = new JButton("Deposit");
		withdrawU = new JButton("Withdraw");
		dismissU = new JButton("Dismiss");
		depositU.addActionListener(handlerU);
		withdrawU.addActionListener(handlerU);
		dismissU.addActionListener(handlerU);
		usdAmount = df.format(group.get(userIndex).getAmount());
		usdTextField.setText(usdAmount);
		usdTextField.setEditable(false);
		usdTextFieldTwo.setText("0.00");
		usdTextFieldTwo.setEditable(true);
		usdTextFieldTwo.addActionListener(handlerU);
		labelA.setOpaque(true);
		labelEU.setOpaque(true);
		usdPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(usdPanel, BorderLayout.CENTER);
		usdPanel.add(labelA, null);
		usdPanel.add(usdTextField, null);
		usdPanel.add(labelEU, null);
		usdPanel.add(usdTextFieldTwo, null);
		usdPanel.add(depositU, null);
		usdPanel.add(withdrawU, null);
		usdPanel.add(dismissU, null);
		pack();
		usdframe.add(usdPanel, null);
		usdframe.pack();
		usdframe.setVisible(true);
	}
	
	//handler to listen to USD View
	class HandlerU implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == usdTextFieldTwo){
				String temp = usdTextFieldTwo.getText();
				amount = Double.parseDouble(temp);
				System.out.println(temp);
			}else if(ee.getSource() == depositU){
				if(amount > 1){
					((CalculatorController)getController()).operation("USDD", group, userIndex, amount, 0 , null);
					System.out.println("deposit USD");
				}
				usdAmount = "0.00";
				amount = 0;
				usdTextFieldTwo.setText(usdAmount);
			}else if(ee.getSource() == withdrawU){
				if(amount > 1 && amount <= group.get(userIndex).getAmount()){
					((CalculatorController)getController()).operation("USDW", group, userIndex, amount, 0 , null);
					System.out.println("withdraw USD");
				}else if (amount > group.get(userIndex).getAmount()){
					((CalculatorController)getController()).operation("USDWERR", group, userIndex, amount, 0 , null);
				}
				usdAmount = "0.00";
				amount = 0;
				usdTextFieldTwo.setText(usdAmount);
			}else if(ee.getSource() == dismissU){
				System.out.println("dismiss USD");
				usdframe.dispose();
			}
		}
	}
	
	//this updates the amount displayed
	public void modelChanged(ModelEvent event) {
		usdAmount = df.format(event.getAmount());
		usdTextField.setText(usdAmount);
	}
}
