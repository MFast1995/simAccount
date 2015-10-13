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
import simpleAccount.view.DeposAgent.HandlerDA;

public class WithAgent extends JFrameView{
	ArrayList <userAccnt> group = new ArrayList<userAccnt>();
	DecimalFormat df = new DecimalFormat("###########0.00");
	private JFrame withframe;
	private JButton startWA;
	private JLabel labelA = new JLabel("Agent ID: ");
	private JLabel labelAA = new JLabel("Amount in $: ");
	private JLabel labelAAA = new JLabel("Operations per second");
	private JTextField withTextField = new JTextField();
	private JTextField withTextFieldTwo = new JTextField();
	private JTextField withTextFieldThree = new JTextField();
	HandlerWA handlerWA = new HandlerWA();
	JPanel withPanel;
	int userIndex;
	String agentID = null;
	double amount = 0;
	double opsec = 0;
	
	//with agent view
	public WithAgent(CalculatorModel model, CalculatorController controller, ArrayList<userAccnt> groupP, int userIndexP){
		super(model, controller);
		userIndex = userIndexP;
		group = groupP;
		withframe = new JFrame("Start Withdraw Agent");
		withPanel = new JPanel();
		startWA = new JButton("START WITHDRAWING");
		startWA.addActionListener(handlerWA);
		withTextField.setEditable(true);
		withTextField.addActionListener(handlerWA);
		withTextFieldTwo.setText("0.00");
		withTextFieldTwo.setEditable(true);
		withTextFieldTwo.addActionListener(handlerWA);
		withTextFieldThree.setText("0.0");
		withTextFieldThree.setEditable(true);
		withTextFieldThree.addActionListener(handlerWA);
		labelA.setOpaque(true);
		labelAA.setOpaque(true);
		labelAAA.setOpaque(true);
		withPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(withPanel, BorderLayout.CENTER);
		withPanel.add(labelA, null);
		withPanel.add(withTextField, null);
		withPanel.add(labelAA, null);
		withPanel.add(withTextFieldTwo, null);
		withPanel.add(labelAAA, null);
		withPanel.add(withTextFieldThree, null);
		withPanel.add(startWA, null);
		pack();
		withframe.add(withPanel, null);
		withframe.pack();
		withframe.setVisible(true);
	}
	
	//handles all the interactions in start withdraw Agent
	class HandlerWA implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == withTextField){
				agentID = withTextField.getText();
				System.out.println(agentID);
			}else if(ee.getSource() == withTextFieldTwo){
				String temp = withTextFieldTwo.getText();
				amount = Double.parseDouble(temp);
				System.out.println(amount);
			}else if(ee.getSource() == withTextFieldThree){
				String temp = withTextFieldThree.getText();
				opsec = Double.parseDouble(temp);
				System.out.println(opsec);
			}else if(ee.getSource() == startWA){
				if(agentID != null && (!agentID.equals("")) && amount >= .01 && opsec != 0){//call wAgent if deposit amount is not null, 0, and at least $0.01
					//start new withdraw thread
					((CalculatorController)getController()).operation("STARTWA", group, userIndex, amount, opsec, agentID);
					System.out.println("withdraw agent start closing frame");
					withframe.dispose();
				}else {
					//send error
					((CalculatorController)getController()).operation("AGENTERR", group, userIndex, amount, opsec , agentID);
					System.out.println("send agent error");
				}
			}
		}
	}
	
	public void modelChanged(ModelEvent event) {
		
	}
	
}

