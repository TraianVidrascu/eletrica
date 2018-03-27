import controller.ClientController;
import exceptions.DuplicateElement;
import exceptions.ElectricaException;
import junit.framework.TestCase;
import junit.framework.TestResult;
import model.Client;
import org.junit.Assert;
import org.junit.Test;

public class AppTest extends TestCase {
    private ClientController ctrl;
    private Client client;
    private Client invalidClient;

    public void setUp() throws Exception {
        client = new Client("test","test","test");
        invalidClient = new Client("test123","test123","test");
        ctrl = new ClientController();

        super.setUp();

    }
    @Test
    public void testAdd(){
        try {
            ctrl.addClient(client.getName(),client.getAddress(),client.getIdClient());
            ctrl.addClient(client.getName(),client.getAddress(),client.getIdClient());
            fail();
        }catch (ElectricaException e){

        }
    }
    @Test
    public void testAddInvalidClient(){
        try {
            ctrl.addClient(invalidClient.getName(),invalidClient.getAddress(),invalidClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
        }
    }
}