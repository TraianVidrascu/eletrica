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

@RunWith(JUnit4.class)
public class Lab4Testing extends TestCase {
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
            dm.resetIssueFile();
            dm.resetClientFile();
            controller.setDataManager(dm);
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

    public void testAdd() {
        dm.resetClientFile();
        try {
            controller.addClient(CLIENT.getName(), CLIENT.getAddress(), CLIENT.getIdClient());
            controller.addClient(CLIENT.getName(), CLIENT.getAddress(), CLIENT.getIdClient());
            controller.addClient(CLIENT.getName(), CLIENT.getAddress(), CLIENT.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DUPLICATE_CLIENT);
        }
    }


    public void testAddIndexStatementCoverage(){

        try {
            dm.resetIssueFile();
            dm.resetIssueFile();
            controller.addClientIndex(CLIENT,ISSUE.getYear(),ISSUE.getMonth(),ISSUE.getToPay());
            Assert.assertTrue(dm.isIssuePresent(ISSUE));
        } catch (ElectricaException e) {
            fail();
        }
    }

    public void testList(){
        try {
            String listIssue = controller.listIssue(CLIENT.getName(),CLIENT.getAddress(),CLIENT.getIdClient());
            Assert.assertEquals(listIssue.trim(), ISSUE_LIST.trim());
        } catch (ElectricaException e) {
            fail();
        }
    }

    @Test
    public void unitTestA(){
        testAdd();
    }
    @Test
    public void intregrationTestB(){
        testAdd();
        testAddIndexStatementCoverage();
    }
    @Test
    public void intregrationTestC(){
        testAdd();
        testAddIndexStatementCoverage();
        testList();
    }
}
