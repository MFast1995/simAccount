package simpleAccount.model;

import java.text.DecimalFormat;
import java.util.ArrayList;



//this is where the withdraws and deposits are performed. Which is then notifying the controller and the view.
public class CalculatorModel extends AbstractModel {
	
	DecimalFormat df = new DecimalFormat("###########0.00");
	
	//synchronized key word will allow for the wait() and notifyAll() functions.
	
	public synchronized void yuanDeposit(ArrayList<userAccnt> group, int userIndex, double amount){
		amount *= .16;
		double newAmount = group.get(userIndex).getAmount() + amount;
		String temp = df.format(newAmount);
		newAmount = Double.parseDouble(temp);
		//group.get(userIndex).setAmount(newAmount);
		ModelEvent me = new ModelEvent(this, 1, "", newAmount);
		notifyChanged(me);
		notifyAll();
	}
	
	public synchronized void yuanWithdraw(ArrayList<userAccnt> group, int userIndex, double amount){
		amount *= .16;
		try{
			while(amount > group.get(userIndex).getAmount()) wait();
			double newAmount = group.get(userIndex).getAmount() - amount;
			String temp = df.format(newAmount);
			newAmount = Double.parseDouble(temp);
			//group.get(userIndex).setAmount(newAmount);
			ModelEvent me = new ModelEvent(this, 1, "", newAmount);
			notifyChanged(me);
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void euroDeposit(ArrayList<userAccnt> group, int userIndex, double amount){
		amount *= 1.08;
		double newAmount = group.get(userIndex).getAmount() + amount;
		String temp = df.format(newAmount);
		newAmount = Double.parseDouble(temp);
		//group.get(userIndex).setAmount(newAmount);
		ModelEvent me = new ModelEvent(this, 1, "", newAmount);
		notifyChanged(me);
		notifyAll();
	}
	
	public synchronized void euroWithdraw(ArrayList<userAccnt> group, int userIndex, double amount){
		amount *= 1.08;
		try{
			while(amount > group.get(userIndex).getAmount()) wait();
			double newAmount = group.get(userIndex).getAmount() - amount;
			String temp = df.format(newAmount);
			newAmount = Double.parseDouble(temp);
			//group.get(userIndex).setAmount(newAmount);
			ModelEvent me = new ModelEvent(this, 1, "", newAmount);
			notifyChanged(me);
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void usdDeposit(ArrayList<userAccnt> group, int userIndex, double amount){
		double newAmount = group.get(userIndex).getAmount() + amount;
		String temp = df.format(newAmount);
		newAmount = Double.parseDouble(temp);
		//group.get(userIndex).setAmount(newAmount);
		ModelEvent me = new ModelEvent(this, 1, "", newAmount);
		notifyChanged(me);
		notifyAll();
	}
	
	public synchronized void usdWithdraw(ArrayList<userAccnt> group, int userIndex, double amount){
		try {
			while(amount > group.get(userIndex).getAmount()) wait();
			double newAmount = group.get(userIndex).getAmount() - amount;
			String temp = df.format(newAmount);
			newAmount = Double.parseDouble(temp);
			//group.get(userIndex).setAmount(newAmount);
			ModelEvent me = new ModelEvent(this, 1, "", newAmount);
			notifyChanged(me);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
