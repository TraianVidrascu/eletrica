package tests;

import controller.ClientController;
import exceptions.ElectricaException;
import exceptions.ErrorMessages;
import junit.framework.TestCase;
import model.Client;
import model.Issue;
import org.junit.Assert;
import org.junit.Test;
import repository.DataManager;

import java.util.OptionalInt;

public class AppTest extends TestCase {
    private ClientController ctrl;
    private DataManager dm;
    private Client client;
    private Client unsavedClient;
    private Client invalidClient;
    private Client indexClinet;
    private Client issueListClinet;
    private Client noIssuesClient;
    private Issue validIssue;
    private Issue issue1;
    private Issue issue2;
    private Issue issue3;
    private Issue invalidIssue;
    private Issue invalidYearIssue;
    private Issue invalidToPayIssue;

    public void setUp() throws Exception {
        client = new Client("test", "test", "test");
        issueListClinet = new Client("testissue", "testissue", "testissue");
        noIssuesClient = new Client("noIssuesClient", "noIssuesClient", "noIssuesClient");
        unsavedClient = new Client("testunsaved", "testunsaved", "testunsaved");
        indexClinet = new Client("idxtest", "idxtest", "idxtest");
        invalidClient = new Client("test123", "test123", "test");
        invalidClient = new Client("test123", "test123", "test");
        validIssue = new Issue(indexClinet, 2018, 9, 100f, 0f);
        issue1 = new Issue(issueListClinet, 2018, 9, 100f, 0f);
        issue2 = new Issue(issueListClinet, 2018, 10, 100f, 0f);
        issue3 = new Issue(issueListClinet, 2018, 11, 100f, 0f);
        invalidIssue = new Issue(indexClinet, 2018, 13, 100f, 0f);
        invalidYearIssue = new Issue(indexClinet, 1850, 9, 100f, 0f);
        invalidToPayIssue = new Issue(indexClinet, 2018, 9, -2f, 0f);
        ctrl = new ClientController();
        dm = new DataManager("testclient.txt","testissue.txt");
        ctrl.setDataManager(dm);
        dm.resetIssueFile();
        dm.resetClientFile();
        try {
            ctrl.addClient(indexClinet.getName(), indexClinet.getAddress(), indexClinet.getIdClient());
        } catch (ElectricaException e) {
        }
        try {
            ctrl.addClient(issueListClinet.getName(), issueListClinet.getAddress(), issueListClinet.getIdClient());
            ctrl.addClientIndex(issue1.getClient(), issue1.getYear(), issue1.getMonth(), issue1.getToPay());
            ctrl.addClientIndex(issue2.getClient(), issue2.getYear(), issue2.getMonth(), issue2.getToPay());
            ctrl.addClientIndex(issue3.getClient(), issue3.getYear(), issue3.getMonth(), issue3.getToPay());
        } catch (ElectricaException e) {

        }
        try {
            ctrl.addClient(noIssuesClient.getName(), noIssuesClient.getAddress(), noIssuesClient.getIdClient());

        } catch (ElectricaException e) {

        }
        super.setUp();

    }

    @Test
    public void testAdd() {
        try {
            ctrl.addClient(client.getName(), client.getAddress(), client.getIdClient());
            ctrl.addClient(client.getName(), client.getAddress(), client.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DUPLICATE_CLIENT);
        }
    }

    @Test
    public void testAddInvalidNameClient() {
        try {
            ctrl.addClient(invalidClient.getName(), invalidClient.getAddress(), invalidClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.INVALID_CHARACTER + "1");
        }
    }

    @Test
    public void testAddNullNameClient() {
        try {
            ctrl.addClient(null, invalidClient.getAddress(), invalidClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddWhitespaceNameClient() {
        try {
            ctrl.addClient("    ", invalidClient.getAddress(), invalidClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddNullAdressClient() {
        try {
            ctrl.addClient("test", null, invalidClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddWhitespaceAdressClient() {
        try {
            ctrl.addClient("test", "     ", invalidClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddNullId() {
        try {
            ctrl.addClient(client.getName(), client.getAddress(), null);
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddWhitespaceId() {
        try {
            ctrl.addClient(client.getName(), client.getAddress(), "   ");
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddIndex() {
        try {
            ctrl.addClientIndex(validIssue.getClient(), validIssue.getYear(), validIssue.getMonth(), validIssue.getToPay());
            ctrl.addClientIndex(validIssue.getClient(), validIssue.getYear(), validIssue.getMonth(), validIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.MONTHLY_INDEX_ALREADY_EXISTS);
        }
    }


    @Test
    public void testAddIndexToInvalidClient() {
        try {
            ctrl.addClientIndex(invalidClient, validIssue.getYear(), validIssue.getMonth(), validIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            OptionalInt charachter = invalidClient.getName().chars().filter(value -> value >= 48 && value <= 57).findFirst();
            String invalidCharacter = new String();
            if (charachter.isPresent()) {
                invalidCharacter = Character.toString((char) charachter.getAsInt());
            } else {
                fail();
            }
            Assert.assertEquals(e.getMessage().trim(), ErrorMessages.INVALID_CHARACTER + invalidCharacter);
        }
    }



    @Test
    public void testInvalidYearIndex() {
        try {
            ctrl.addClientIndex(invalidYearIssue.getClient(), invalidYearIssue.getYear(), invalidYearIssue.getMonth(), invalidYearIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_YEAR);
        }
        invalidYearIssue.setYear(2060);
        try {
            ctrl.addClientIndex(invalidYearIssue.getClient(), invalidYearIssue.getYear(), invalidYearIssue.getMonth(), invalidYearIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_YEAR);
        }
    }

    @Test
    public void testInvalidToPayIndex() {
        try {
            ctrl.addClientIndex(invalidToPayIssue.getClient(), invalidToPayIssue.getYear(), invalidToPayIssue.getMonth(), invalidToPayIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_MONEY_SUM);
        }
    }

    @Test
    public void testListIssues() {
        String result = null;
        try {
            result = ctrl.listIssue(issueListClinet.getName(), issueListClinet.getAddress(), issueListClinet.getIdClient());
        } catch (ElectricaException e) {
            e.printStackTrace();
        }
        if (result == null) {
            fail();
        }
        assertEquals(3, result.split("\n").length);
    }

    @Test
    public void testListIssuesNoIssues() {
        String result = null;
        try {
            result = ctrl.listIssue(noIssuesClient.getName(), noIssuesClient.getAddress(), noIssuesClient.getIdClient());
        } catch (ElectricaException e) {
            e.printStackTrace();
        }
        if (result == null) {
            fail();
        }
        assertEquals(true, result.contains(noIssuesClient.getName()));
    }

    @Test
    public void testListIssuesUnsavedClient() {
        try {
            ctrl.listIssue(unsavedClient.getName(), unsavedClient.getAddress(), unsavedClient.getIdClient());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.CLIENT_DOES_NOT_EXIST);
        }

    }

    @Test
    public void testAddClientIssueWhiteBox() {
        ctrl.getDataManager().resetClientFile();
        ctrl.getDataManager().resetIssueFile();
        try {
            ctrl.addClient(client.getName(), client.getAddress(), client.getIdClient());
        } catch (ElectricaException e) {
            e.printStackTrace();
        }

        try {
            ctrl.addClientIndex(client,issue1.getYear(),issue1.getMonth(),issue1.getToPay());
        } catch (ElectricaException e) {
            fail();
        }
    }

    @Test
    public void testInvalidMonthIndex() {
        try {
            ctrl.addClientIndex(invalidIssue.getClient(), invalidIssue.getYear(), invalidIssue.getMonth(), invalidIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_MONTH);
        }
        invalidIssue.setMonth(-1);
        try {
            ctrl.addClientIndex(invalidIssue.getClient(), invalidIssue.getYear(), invalidIssue.getMonth(), invalidIssue.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_MONTH);
        }
    }
}