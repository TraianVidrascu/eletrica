package tests;

import controller.ClientController;
import exceptions.ElectricaException;
import exceptions.ErrorMessages;
import junit.framework.TestCase;
import model.Client;
import model.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.*;
import repository.DataManager;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class CoverageTest  extends TestCase{
    private final Client STATEMENT_CLIENT = new Client("statement", "statement", "statement");
    private final Client INVALID_CLIENT = new Client(null, "statement", "statement");
    private final Client INVALID_CLIENT_CHARACTER = new Client("123", "statement", "statement");
    private final Issue STATEMENT_ISSUE = new Issue(STATEMENT_CLIENT,2018,9,10f,10f);
    private final Issue PRESENT_ISSUE = new Issue(STATEMENT_CLIENT,2018,9,10f,10f);
    private final Issue INVALID_ISSUE_YEAR = new Issue(STATEMENT_CLIENT,3500,9,10f,10f);
    private final Issue INVALID_ISSUE_MONTH = new Issue(STATEMENT_CLIENT,2018,90,10f,10f);
    private final Issue INVALID_ISSUE_MONEY_SUM = new Issue(STATEMENT_CLIENT,2018,9,-10f,10f);

    @Mock
    DataManager mockedDataManager;

    @Captor
    ArgumentCaptor<Issue> captor;

    @InjectMocks
    private ClientController sut;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        doReturn(true).when(mockedDataManager).isClientPresent(STATEMENT_CLIENT);
        doReturn(false).when(mockedDataManager).isIssuePresent(Mockito.any(Issue.class));
    }
    @Test
    public void testAddIndexStatementCoverage(){

        try {
            sut.addClientIndex(STATEMENT_CLIENT,STATEMENT_ISSUE.getYear(),STATEMENT_ISSUE.getMonth(),STATEMENT_ISSUE.getToPay());
            verify(mockedDataManager,times(1)).isIssuePresent(captor.capture());
            verify(mockedDataManager,times(1)).addIssue(captor.capture());
            verify(mockedDataManager,times(1)).saveChanges();

            List<Issue> arguments = captor.getAllValues();
            arguments.forEach(issue -> Assert.assertEquals(issue,STATEMENT_ISSUE));
        } catch (ElectricaException e) {
            fail();
        }
    }

    @Test
    public void testAddIndexInvalidClient(){
        try {
            sut.addClientIndex(INVALID_CLIENT,STATEMENT_ISSUE.getYear(),STATEMENT_ISSUE.getMonth(),STATEMENT_ISSUE.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }

    @Test
    public void testAddIndexInvalidClientCharacter(){
        try {
            sut.addClientIndex(INVALID_CLIENT_CHARACTER,STATEMENT_ISSUE.getYear(),STATEMENT_ISSUE.getMonth(),STATEMENT_ISSUE.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_CHARACTER+"1");
        }
    }

    @Test
    public void testAddIndexInvalidIssueYear(){
        try {
            sut.addClientIndex(STATEMENT_CLIENT,INVALID_ISSUE_YEAR.getYear(),INVALID_ISSUE_YEAR.getMonth(),INVALID_ISSUE_YEAR.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_YEAR);
        }
    }

    @Test
    public void testAddIndexInvalidIssueMonth(){
        try {
            sut.addClientIndex(STATEMENT_CLIENT,INVALID_ISSUE_MONTH.getYear(),INVALID_ISSUE_MONTH.getMonth(),INVALID_ISSUE_MONTH.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_MONTH);
        }
    }

    @Test
    public void testAddIndexInvalidIssueToPay(){
        try {
            sut.addClientIndex(STATEMENT_CLIENT,INVALID_ISSUE_MONEY_SUM.getYear(),INVALID_ISSUE_MONEY_SUM.getMonth(),INVALID_ISSUE_MONEY_SUM.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.INVALID_MONEY_SUM);
        }
    }

    @Test
    public void testAddIndexInvalidPresentIssue(){
        doReturn(true).when(mockedDataManager).isIssuePresent(Mockito.any(Issue.class));

        try {
            sut.addClientIndex(STATEMENT_CLIENT,PRESENT_ISSUE.getYear(),PRESENT_ISSUE.getMonth(),PRESENT_ISSUE.getToPay());
            fail();
        } catch (ElectricaException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.MONTHLY_INDEX_ALREADY_EXISTS);
        }
    }
}
