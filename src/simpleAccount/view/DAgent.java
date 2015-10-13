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

public class DAgent extends JFrameView implements Runnable {
	private static final long serialVersionUID = 1L;
	ArrayList <userAccnt> group = new ArrayList<userAccnt>();
	DecimalFormat df = new DecimalFormat("###########0.00");
	private JFrame dframe;
	private JButton stopDA;
	private JButton dismissDA;
	private JLabel labelA = new JLabel("Amount in $: ");
	private JLabel labelAA = new JLabel("State: ");
	private JLabel labelAAA = new JLabel("Amount in $ transferred: ");
	private JLabel labelAAAA = new JLabel("Operations per second: ");
	private JTextField dTextField = new JTextField();
	private JTextField dTextFieldTwo = new JTextField();
	private JTextField dTextFieldThree = new JTextField();
	private JTextField dTextFieldFour = new JTextField();
	HandlerDA handlerDA = new HandlerDA();
	JPanel dPanel;
	int userIndex;
	String agentID = null;
	double amount = 0;
	double opsec = 0;
	String state = "running";
	int count = 0;
	//depo agent view
	public DAgent(CalculatorModel model, CalculatorController controller, ArrayList<userAccnt> groupP, int userIndexP, double amountP, double opsecP, String agentIDP){
		super(model, controller);
		userIndex = userIndexP;
		group = groupP;
		String handler = "Deposit Window Agent: " + agentID +" For Account: " + groupP.get(userIndexP).getId();
		dframe = new JFrame(handler);
		dPanel = new JPanel();
		stopDA = new JButton("STOP");
		stopDA.addActionListener(handlerDA);
		dismissDA = new JButton("DISMISS");
		dismissDA.setEnabled(false);
		dismissDA.addActionListener(handlerDA);
		amount = amountP;
		opsec = opsecP;
		agentID = agentIDP;
		String temp =  df.format(amount);
		dTextField.setText(temp);
		dTextField.setEditable(false);
		dTextFieldTwo.setText(state);
		dTextFieldTwo.setEditable(false);
		dTextFieldThree.setText("0.0");
		dTextFieldThree.setEditable(false);
		temp =  df.format(opsec);
		dTextFieldFour.setText(temp);
		dTextFieldFour.setEditable(false);
		labelA.setOpaque(true);
		labelAA.setOpaque(true);
		labelAAA.setOpaque(true);
		labelAAAA.setOpaque(true);
		dPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(dPanel, BorderLayout.CENTER);
		dPanel.add(labelA, null);
		dPanel.add(dTextField, null);
		dPanel.add(labelAA, null);
		dPanel.add(dTextFieldTwo, null);
		dPanel.add(labelAAA, null);
		dPanel.add(dTextFieldThree, null);
		dPanel.add(labelAAAA, null);
		dPanel.add(dTextFieldFour, null);
		dPanel.add(stopDA, null);
		dPanel.add(dismissDA, null);
		pack();
		dframe.add(dPanel, null);
		dframe.pack();
		dframe.setVisible(true);
	}
	
	//handles all the interactions in start Deposit Agent
	class HandlerDA implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == stopDA){
				state = "stopped";
				System.out.println("stop");
				dismissDA.setEnabled(true);
				dTextFieldTwo.setText(state);
			}else if(ee.getSource() == dismissDA){
				System.out.println("deposit agent closing");
				dframe.dispose();
			}
		}
	}
	
	public void modelChanged(ModelEvent event) {
		//String Amount = df.format(event.getAmount());
		//dTextField.setText(Amount);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("state from dep: " + state);
		while(state.equals("running")){
			try {
				Thread.sleep((long)(1000/opsec)); //pause 1 second / ops per second
				count ++;
				((CalculatorController)getController()).operation("USDD", group, userIndex, amount, opsec , agentID);
				double amountTrans = count * amount;
				String temp = df.format(amountTrans);
				dTextFieldThree.setText(temp);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
}
