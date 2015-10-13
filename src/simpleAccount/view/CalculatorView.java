package simpleAccount.view;
import javax.swing.*; 


import simpleAccount.controller.CalculatorController;
import simpleAccount.model.CalculatorModel;
import simpleAccount.model.ModelEvent;
import simpleAccount.model.userAccnt;

import java.awt.*; 
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;


public class CalculatorView extends JFrameView {
	static public ArrayList<userAccnt> group = new ArrayList<userAccnt>(); 
	private JComboBox groupCB;
	private JButton EditInUSD;
	private JButton EditInEuro;
	private JButton EditInYuan;
	private JButton DepositAgent;
	private JButton WithdrawAgent;
	private JButton save;
	private JButton exit;
	Handler handler = new Handler();
	public int userIndex = 0;
	public static String file;
	private static int userCount = 0;
	
	//main view
	public CalculatorView(CalculatorModel model, CalculatorController controller) { 
		super(model, controller);
		groupCB = new JComboBox();
		for(userAccnt u: group){
			String temp = u.toCBString();
			groupCB.addItem(temp);
		}
		groupCB.addActionListener(handler);
		System.out.println(group.get(userIndex).toCBString());
		System.out.println("hello test"); 
		JPanel buttonPanel = new JPanel();
		EditInUSD = new JButton("Edit In USD");
		EditInEuro = new JButton("Edit In Euro");
		EditInYuan = new JButton("Edit in Yuan");
		save = new JButton("SAVE");
		exit = new JButton("EXIT");
		DepositAgent = new JButton("Create Deposit Agent");
		WithdrawAgent = new JButton("Create Withdraw Agent");
		EditInUSD.addActionListener(handler);
		EditInEuro.addActionListener(handler);
		EditInYuan.addActionListener(handler);
		save.addActionListener(handler);
		exit.addActionListener(handler);
		WithdrawAgent.addActionListener(handler);
		DepositAgent.addActionListener(handler);
		buttonPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.add(groupCB, null);
		buttonPanel.add(EditInUSD, null);
		buttonPanel.add(EditInEuro, null);
		buttonPanel.add(EditInYuan, null);
		buttonPanel.add(DepositAgent, null);
		buttonPanel.add(WithdrawAgent, null);
		buttonPanel.add(save, null);
		buttonPanel.add(exit, null);
		
		pack();
	}
	
	 //save the amount thats updated to the user 
	public void modelChanged(ModelEvent event) {
		group.get(userIndex).setAmount(event.getAmount());
	}
	
	 //class for event handling 
	class Handler implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == groupCB){
				userIndex = groupCB.getSelectedIndex();
				System.out.println(group.get(userIndex).toCBString());
			}else if(e.getSource() == EditInUSD){
				((CalculatorController)getController()).operation("USD", group, userIndex, 0, 0, null);
				System.out.println("usd");
			}else if(e.getSource() == EditInEuro){
				((CalculatorController)getController()).operation("EURO", group, userIndex, 0, 0, null);
				System.out.println("euro");
			}else if(e.getSource() == EditInYuan){
				((CalculatorController)getController()).operation("YUAN", group, userIndex, 0, 0, null);
				System.out.println("yuan");
			}else if(e.getSource() == save){
				System.out.println("save");
				try {
					writetofile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(e.getSource() == exit){
				System.out.println("save and exit");
				try {
					writetofile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}else if(e.getSource() == WithdrawAgent){
				//new withdraw agent view
				((CalculatorController)getController()).operation("WITAG", group, userIndex, 0, 0, null);
			}else if(e.getSource() == DepositAgent){
				//new deposit agent view
				((CalculatorController)getController()).operation("DEPAG", group, userIndex, 0, 0, null);
			}
	    } 
	}
	
	public static void main(String [] args) { 
		//read from file passed as the argument
		userAccnt single;
		BufferedReader br = null;
		file = args[0];
		try {
			br = new BufferedReader(new FileReader(file));
			String current;
			while((current = br.readLine()) != null){
				String [] input = current.split("		");
				single = new userAccnt(input[0], input[1], Integer.parseInt(input[2]), Float.parseFloat(input[3]));
				group.add(single);
				userCount++;
			}
		}catch(IOException E){
			System.out.println("argument file not found!");
		}
		//sort inputed group
		Collections.sort(group);
		//print sorted group
		for(userAccnt u: group){
			System.out.println(u.toString());
		}
		//print argument
		System.out.println(args[0]);
		new CalculatorController(); 
	}
	
	//write to file method
	public void writetofile() throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));
		for (userAccnt u : group){
	        pw.println(u.toString());
	    }
	    pw.close();
	}
}
