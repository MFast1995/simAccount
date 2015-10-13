package simpleAccount.view;
import simpleAccount.controller.Controller;
import simpleAccount.model.Model;

public interface View {
	Controller getController();
	void setController(Controller controller);
	Model getModel();
	void setModel(Model model);
}
