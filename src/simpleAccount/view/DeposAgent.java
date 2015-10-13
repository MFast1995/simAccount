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
import simpleAccount.view.EditEURO.HandlerE;

public class DeposAgent extends JFrameView {
	ArrayList <userAccnt> group = new ArrayList<userAccnt>();
	DecimalFormat df = new DecimalFormat("###########0.00");
	private JFrame depoframe;
	private JButton startDA;
	private JLabel labelA = new JLabel("Agent ID: ");
	private JLabel labelAA = new JLabel("Amount in $: ");
	private JLabel labelAAA = new JLabel("Operations per second");
	private JTextField depoTextField = new JTextField();
	private JTextField depoTextFieldTwo = new JTextField();
	private JTextField depoTextFieldThree = new JTextField();
	HandlerDA handlerDA = new HandlerDA();
	JPanel depoPanel;
	int userIndex;
	String agentID = null;
	double amount = 0;
	double opsec = 0;
	
	//depo agent view
	public DeposAgent(CalculatorModel model, CalculatorController controller, ArrayList<userAccnt> groupP, int userIndexP){
		super(model, controller);
		userIndex = userIndexP;
		group = groupP;
		depoframe = new JFrame("Start Deposit Agent");
		depoPanel = new JPanel();
		startDA = new JButton("START DEPOSITING");
		startDA.addActionListener(handlerDA);
		depoTextField.setEditable(true);
		depoTextField.addActionListener(handlerDA);
		depoTextFieldTwo.setText("0.00");
		depoTextFieldTwo.setEditable(true);
		depoTextFieldTwo.addActionListener(handlerDA);
		depoTextFieldThree.setText("0.0");
		depoTextFieldThree.setEditable(true);
		depoTextFieldThree.addActionListener(handlerDA);
		labelA.setOpaque(true);
		labelAA.setOpaque(true);
		labelAAA.setOpaque(true);
		depoPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(depoPanel, BorderLayout.CENTER);
		depoPanel.add(labelA, null);
		depoPanel.add(depoTextField, null);
		depoPanel.add(labelAA, null);
		depoPanel.add(depoTextFieldTwo, null);
		depoPanel.add(labelAAA, null);
		depoPanel.add(depoTextFieldThree, null);
		depoPanel.add(startDA, null);
		pack();
		depoframe.add(depoPanel, null);
		depoframe.pack();
		depoframe.setVisible(true);
	}
	
	//handles all the interactions in start Deposit Agent
	class HandlerDA implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == depoTextField){
				agentID = depoTextField.getText();
				System.out.println(agentID);
			}else if(ee.getSource() == depoTextFieldTwo){
				String temp = depoTextFieldTwo.getText();
				amount = Double.parseDouble(temp);
				System.out.println(amount);
			}else if(ee.getSource() == depoTextFieldThree){
				String temp = depoTextFieldThree.getText();
				opsec = Double.parseDouble(temp);
				System.out.println(opsec);
			}else if(ee.getSource() == startDA){
				if(agentID != null && (!agentID.equals("")) && amount >= .01 && opsec != 0){
					//start new withdraw thread
					((CalculatorController)getController()).operation("STARTDA", group, userIndex, amount, opsec, agentID);
					System.out.println("deposit agent start closing frame");
					depoframe.dispose();
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
