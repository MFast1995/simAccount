package simpleAccount.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import simpleAccount.controller.CalculatorController;
import simpleAccount.model.CalculatorModel;
import simpleAccount.model.ModelEvent;


//errWindow to send an error message to the user

public class errWindow extends JFrameView{
	private JButton dismissERR;
	private JLabel labelERR;
	private JFrame errFrame;
	HandlerERR handlerERR = new HandlerERR();
	private JPanel errPanel;
	
	public errWindow(CalculatorModel model, CalculatorController controller, String print){
		super(model, controller);
		errFrame = new JFrame("ERROR");
		labelERR = new JLabel(print);
		dismissERR = new JButton("Dismiss");
		dismissERR.addActionListener(handlerERR);
		errPanel = new JPanel();
		errPanel.setLayout(new GridLayout(7, 4, 10, 10));
		this.getContentPane().add(errPanel, BorderLayout.CENTER);
		errPanel.add(labelERR, null);
		errPanel.add(dismissERR, null);
		pack();
		errFrame.add(errPanel);
		errFrame.pack();
		errFrame.setVisible(true);
	}
	class HandlerERR implements ActionListener {
		public void actionPerformed(ActionEvent ee) {
			if(ee.getSource() == dismissERR){
				errFrame.dispose();
			}
		}
	}
	
	public void modelChanged(ModelEvent event) {
		//do nothing
	}
}
