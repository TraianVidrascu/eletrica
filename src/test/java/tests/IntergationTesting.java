package tests;

import controller.ClientController;
import exceptions.ElectricaException;
import exceptions.ErrorMessages;
import exceptions.IOElectricaException;
import junit.framework.TestCase;
import model.Client;
import model.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import repository.DataManager;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class IntergationTesting extends TestCase{

    public static final String ISSUE_LIST = "testingAVCKcyear: 2018, Month: 9, paid:  0, Penalty: 10";
    private final Client CLIENT = new Client("testingAVCKc", "testing123c", "testing123c");
    private final Issue ISSUE = new Issue(CLIENT,2018,9,10f,10f);;

    private ClientController controller;
    private DataManager dm;

    @Before
    public void setUp(){
        try {
            controller = new ClientController();
            dm = new DataManager("testclient.txt","testissue.txt");
            controller.setDataManager(dm);
            dm.resetIssueFile();
            dm.resetClientFile();
            if(!controller.getDataManager().isClientPresent(CLIENT)){
                controller.addClient(CLIENT.getName(),CLIENT.getAddress(),CLIENT.getAddress());
            }
            if(!controller.getDataManager().isIssuePresent(ISSUE)){
                controller.addClientIndex(CLIENT,ISSUE.getYear(),ISSUE.getMonth(),ISSUE.getToPay());
            }

        } catch (IOElectricaException e) {
            e.printStackTrace();
        } catch (ElectricaException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAdd() {
        dm.resetClientFile();
        try {
            controller.addClient(CLIENT.getName(), CLIENT.getAddress(), CLIENT.getIdClient());
            controller.addClient(CLIENT.getName(), CLIENT.getAddress(), CLIENT.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DUPLICATE_CLIENT);
        }
    }

    @Test
    public void testAddIndexStatementCoverage(){

        try {
            dm.resetIssueFile();
            controller.addClientIndex(CLIENT,ISSUE.getYear(),ISSUE.getMonth(),ISSUE.getToPay());
            Assert.assertTrue(dm.isIssuePresent(ISSUE));
        } catch (ElectricaException e) {
            fail();
        }
    }

    @Test
    public void testList(){
        try {
            String listIssue = controller.listIssue(CLIENT.getName(),CLIENT.getAddress(),CLIENT.getIdClient());
            Assert.assertEquals(listIssue.trim(), ISSUE_LIST.trim());
        } catch (ElectricaException e) {
            fail();
        }
    }


    @Test
    public void testBigBang(){
        testAdd();
        testAddIndexStatementCoverage();
        testList();
    }
}
