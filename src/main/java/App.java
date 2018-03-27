

import exceptions.IOElectricaException;
import controller.ClientController;
import ui.ElectricaUI;

public class App {
	public static void main(String[] args) {

		ClientController ctrl = null;
		try {
			ctrl = new ClientController();
		} catch (IOElectricaException e) {
			e.printStackTrace();
		}

		ElectricaUI console = new ElectricaUI(ctrl);
		console.run();
	}
}
