package controller;


import exceptions.*;
import repository.DataManager;
import model.*;

import java.util.List;

import static exceptions.ErrorMessages.*;

public class ClientController {
    private DataManager dataManager;

    public ClientController() throws IOElectricaException {
        dataManager = new DataManager();
    }

    private void validateClient(String name, String address, String id) throws ElectricaException {
        if (name != null && !name.trim().isEmpty() && address != null && !address.trim().isEmpty() && id != null && !id.trim().isEmpty()) {
            for (int i = 0; i < name.length(); i++) {
                if (!Character.isAlphabetic(name.charAt(i)) && !Character.isSpaceChar(name.charAt(i))) {
                    throw new InvalidCharacterException(ErrorMessages.INVALID_CHARACTER + name.charAt(i));
                }
            }
            return;
        } else {
            throw new EmptyParameterException(ErrorMessages.EMPTY_ADDRESS_OR_NAME);
        }
    }


    public void addClient(String name, String address, String id) throws ElectricaException {
        //validation
        validateClient(name, address, id);

        Client c = new Client(name, address, id);
        //uniqueness
        if (dataManager.isClientPresent(c)) {
            throw new DuplicateElement(ErrorMessages.DUPLICATE_CLIENT);
        }

        dataManager.addClient(c);
        dataManager.saveChanges();
    }

    public void addClientIndex(Client c, int year, int month, float toPay) throws ElectricaException {


        //validate client attributes
        validateClient(c.getName(), c.getAddress(), c.getIdClient());
        //check if client exist
        if (dataManager.isClientPresent(c)) {
            Issue issue = new Issue(c, year, month, toPay, 0);
            validateIssue(issue);
            //uniqueness
            if (dataManager.isIssuePresent(issue)) {
                throw new IssueException(MONTHLY_INDEX_ALREADY_EXISTS);
            }
            dataManager.addIssue(issue);
            dataManager.saveChanges();


        }
    }

        private void validateIssue (Issue issue) throws InvalidParameterExecption {
            if (issue.getMonth() < 1 || issue.getMonth() > 12) {
                throw new InvalidParameterExecption(ErrorMessages.INVALID_MONTH);
            }
            if (issue.getYear() < 1945 || issue.getYear() > 2050) {
                throw new InvalidParameterExecption(ErrorMessages.INVALID_YEAR);
            }
            if (issue.getToPay() < 0) {
                throw new InvalidParameterExecption(ErrorMessages.INVALID_MONEY_SUM);
            }
        }

        public String listIssue (String name, String address, String id) throws ElectricaException {

            Client client = new Client(name, address, id);
            if (!dataManager.isClientPresent(client)) {
                throw new IssueException(CLIENT_DOES_NOT_EXIST);

            }
            List<Issue> issues = dataManager.getClientIssues(client);
            for (Issue issue : issues) {
                name += String.format("year: %d, Month: %d, paid: %2.0f, Penalty: %2.0f\n", issue.getYear(), issue.getMonth(), issue.getPaid(), issue.getToPay());
            }
            return name;
        }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
