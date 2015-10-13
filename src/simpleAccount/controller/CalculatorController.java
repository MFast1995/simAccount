package simpleAccount.controller;
import java.text.DecimalFormat;
import java.util.ArrayList;

import simpleAccount.model.CalculatorModel;
import simpleAccount.model.userAccnt;
import simpleAccount.view.CalculatorView;
import simpleAccount.view.DAgent;
import simpleAccount.view.EditEURO;
import simpleAccount.view.EditUSD;
import simpleAccount.view.EditYUAN;
import simpleAccount.view.JFrameView;
import simpleAccount.view.WAgent;
import simpleAccount.view.WithAgent;
import simpleAccount.view.errWindow;
import simpleAccount.view.DeposAgent;


public class CalculatorController extends AbstractController {
	DecimalFormat df = new DecimalFormat("###########0.00");
	
	public CalculatorController(){
		setModel(new CalculatorModel());
		setView(new CalculatorView((CalculatorModel)getModel(), this));
		((JFrameView)getView()).setVisible(true);
	}
	
	//operation method tell what to do next middle man of view and model.
	public void operation(String option, ArrayList<userAccnt> group, int userIndex, double amount, double opsec, String agentID){
		if(option.equals("YUANWERR")){
			String print;
			String sendOver = df.format((amount - group.get(userIndex).getAmountYuan()));
			print = "Insufficient funds: amount to withdraw is " + sendOver + " greater than available funds: " + df.format(group.get(userIndex).getAmountYuan());
			setView(new errWindow((CalculatorModel)getModel(), this, print));
		}else if(option.equals("EUROWERR")){
			String print;
			String sendOver = df.format((amount - group.get(userIndex).getAmountEuro()));
			print = "Insufficient funds: amount to withdraw is " + sendOver + " greater than available funds: " + df.format(group.get(userIndex).getAmountEuro());
			setView(new errWindow((CalculatorModel)getModel(), this, print));
		}else if(option.equals("USDWERR")){
			String print;
			String sendOver = df.format((amount - group.get(userIndex).getAmount()));
			print = "Insufficient funds: amount to withdraw is " + sendOver + " greater than available funds: " + df.format(group.get(userIndex).getAmount());
			setView(new errWindow((CalculatorModel)getModel(), this, print));
		}else if(option.equals("YUANW")){
			((CalculatorModel)getModel()).yuanWithdraw(group, userIndex, amount);
		}else if(option.equals("YUAND")){
			((CalculatorModel)getModel()).yuanDeposit(group, userIndex, amount);
		}else if(option.equals("EUROW")){
			((CalculatorModel)getModel()).euroWithdraw(group, userIndex, amount);
		}else if(option.equals("EUROD")){
			((CalculatorModel)getModel()).euroDeposit(group, userIndex, amount);
		}else if(option.equals("USDW")){
			((CalculatorModel)getModel()).usdWithdraw(group, userIndex, amount);
		}else if(option.equals("USDD")){
			((CalculatorModel)getModel()).usdDeposit(group, userIndex, amount);
		}else if(option.equals("USD")){
			setView(new EditUSD((CalculatorModel)getModel(), this, group, userIndex));
		}else if(option.equals("EURO")){
			setView(new EditEURO((CalculatorModel)getModel(), this, group, userIndex));
		}else if(option.equals("YUAN")){
			setView(new EditYUAN((CalculatorModel)getModel(), this, group, userIndex));
		}else if(option.equals("DEPAG")){
			setView(new DeposAgent((CalculatorModel)getModel(), this, group, userIndex));
		}else if(option.equals("WITAG")){
			setView(new WithAgent((CalculatorModel)getModel(), this, group, userIndex));
		}else if(option.equals("AGENTERR")){
			String print = "To start an agent you have to have entered: \n - agentID \n - amount in USD(x>=.01) \n - operations per second";
			setView(new errWindow((CalculatorModel)getModel(), this, print));
		}else if(option.equals("STARTDA")){
			//start new deposit thread
			Runnable r = new DAgent((CalculatorModel)getModel(), this, group, userIndex, amount, opsec, agentID);
			new Thread(r).start();
		}else if(option.equals("STARTWA")){
			//start new withdraw thread
			Runnable rOne =new WAgent((CalculatorModel)getModel(), this, group, userIndex, amount, opsec, agentID);
			new Thread(rOne).start();
		}
	}
}
