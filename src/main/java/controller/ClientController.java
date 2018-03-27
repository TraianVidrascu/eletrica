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
                if ((!Character.isUpperCase(name.charAt(i))) && (!Character.isLowerCase(name.charAt(i))) && (!Character.isSpaceChar(name.charAt(i)))) {
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
        if (year > 0) {
            if (month > 0) {
                if (toPay >= 0) {
                    //validate client attributes
                    validateClient(c.getName(), c.getAddress(), c.getIdClient());
                    //check if client exist
                    if (dataManager.isClientPresent(c)) {
                        Issue issue = new Issue(c, year, month, toPay, 0);
                        //uniqueness
                        if (dataManager.isIssuePresent(issue)) {
                            throw new IssueException(MONTHLY_INDEX_ALREADY_EXISTS);
                        }
                        dataManager.addIssue(issue);
                        dataManager.saveChanges();
                    } else {
                        throw new IssueException(CLIENT_DOES_NOT_EXIST);
                    }

                } else {
                    throw new IssueException(INVALID_MONEY_SUM);
                }
            } else {
                throw new IssueException(INVALID_MONTH);
            }
        } else {
            throw new IssueException(INVALID_YEAR);
        }
    }

    public String listIssue(String name, String address, String id) {
        List<Issue> issues = dataManager.getClientIssues(new Client(name,address,id));
        for (Issue issue : issues) {
            name += String.format("year: %d, Month: %d, paid: %2.0f, Penalty: %2.0f\n", issue.getYear(), issue.getMonth(), issue.getPaid(), issue.getToPay());
        }
        return name;
    }

}
