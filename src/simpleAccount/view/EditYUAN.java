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


public class EditYUAN extends JFrameView{
	ArrayList<userAccnt> group = new ArrayList<userAccnt>();
	DecimalFormat df = new DecimalFormat("###########0.00");
	private JFrame yuanframe;
	private JButton depositY;
	private JButton withdrawY;
	private JButton dismissY;
	private JLabel labelA = new JLabel("Available Funds:");
	private JLabel labelEY = new JLabel("Editable in YUAN:");
	private JTextField yuanTextField = new JTextField();
	private JTextField yuanTextFieldTwo = new JTextField();
	HandlerY handlerY = new HandlerY();
	JPanel yuanPanel;
	String yuanAmount;
	int userIndex;
	double amount = 0;
	
	//Yuan View
	public EditYUAN(CalculatorModel model, CalculatorController controller, ArrayList<userAccnt> groupP, int userIndexP){
		super(model, controller);
		group = groupP;
		userIndex = userIndexP;
		String holder = group.get(userIndex).getFirstName() + " " + group.get(userIndex).getLastName() + " " + group.get(userIndex).getId();
		System.out.println("hell:" + holder);
		yuanframe = new JFrame(holder);
		yuanPanel = new JPanel();
		depositY = new JButton("Deposit");
		withdrawY = new JButton("Withdraw");
		dismissY = new JButton("Dismiss");
		depositY.addActionListener(handlerY);
		withdrawY.addActionListener(handlerY);
		dismissY.addActionListener(handlerY);
		yuanAmount = df.format(group.get(userIndex).getAmountYuan());
		yuanTextField.setText(yuanAmount);
		yuanTextField.setEditable(false);
		yuanTextFieldTwo.setText("0.00");
		yuanTextFieldTwo.setEditable(true);
		yuanTextFieldTwo.addActionListener(handlerY);
		labelA.setOpaque(true);
		labelEY.setOpaque(true);
		yuanPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(yuanPanel, BorderLayout.CENTER);
		yuanPanel.add(labelA, null);
		yuanPanel.add(yuanTextField, null);
		yuanPanel.add(labelEY, null);
		yuanPanel.add(yuanTextFieldTwo, null);
		yuanPanel.add(depositY, null);
		yuanPanel.add(withdrawY, null);
		yuanPanel.add(dismissY, null);
		pack();
		yuanframe.add(yuanPanel, null);
		yuanframe.pack();
		yuanframe.setVisible(true);
	}
	
	//handles all interaction with Yuan View
	class HandlerY implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == yuanTextFieldTwo){
				String temp = yuanTextFieldTwo.getText();
				amount = Double.parseDouble(temp);
				System.out.println(temp);
			}else if(ee.getSource() == depositY){
				if(amount > 1){
					((CalculatorController)getController()).operation("YUAND", group, userIndex, amount, 0 , null);
					System.out.println("deposit YUAN");
				}
				yuanAmount = "0.00";
				amount = 0;
				yuanTextFieldTwo.setText(yuanAmount);
			}else if(ee.getSource() == withdrawY){
				if(amount > 1 && amount <= group.get(userIndex).getAmountYuan()){
					((CalculatorController)getController()).operation("YUANW", group, userIndex, amount, 0, null);
					System.out.println("withdraw YUAN");
				}else if (amount > group.get(userIndex).getAmountYuan()){
					((CalculatorController)getController()).operation("YUANWERR", group, userIndex, amount, 0 , null);
				}
				yuanAmount = "0.00";
				amount = 0;
				yuanTextFieldTwo.setText(yuanAmount);
			}else if(ee.getSource() == dismissY){
				System.out.println("dismiss YUAN");
				yuanframe.dispose();
			}
		}
	}
	
	//updates the displayed amount
	public void modelChanged(ModelEvent event) {
		yuanAmount = df.format(event.getAmount()*6.23);
		yuanTextField.setText(yuanAmount);
	}
	
}
