package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.IOElectricaException;
import model.*;

public class DataManager {
    private  String fileClient = "client.txt";
    private String issueFile = "issue.txt";
    public static final String LINE_SEPARATOR = "line.separator";
    private List<Client> clients;
    private List<Issue> issues;

    public DataManager() throws IOElectricaException {
        clients = new ArrayList<>();
        issues = new ArrayList<>();

        loadClient();
        loadIssues();
    }
    public DataManager(String fileClient,String issueFile) throws IOElectricaException {
        this.fileClient = fileClient;
        this.issueFile = issueFile;
        clients = new ArrayList<>();
        issues = new ArrayList<>();

        loadClient();
        loadIssues();
    }

    public void resetClientFile(){
        clients.clear();
        clearFile(fileClient);

    }

    public void clearFile(String file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void resetIssueFile()
    {
        issues.clear();
        clearFile(issueFile);
    }

    private void loadClient() throws IOElectricaException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileClient));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.replace("\n", "").replace("\r", "").split(",");
                String name = words[0];
                String address = words[1];
                String id = words[2];
                clients.add(new Client(name, address, id));
            }
            br.close();
        } catch (Exception ex) {
            throw new IOElectricaException(ex);
        }
    }

    private void loadIssues() throws IOElectricaException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(issueFile));
            String line;
            Boolean b = true;
            String name = "";
            String address = "";
            String id = "";
            String sYear;
            String sMonth;
            String sToPay;
            String sPaid;

            while ((line = br.readLine()) != null) {
                if (b) {
                    String[] words = line.replace("\n", "").replace("\r", "").split(",");
                    name = words[0];
                    address = words[1];
                    id = words[2];
                    b = false;
                } else {
                    String[] words = line.replace("\n", "").replace("\r", "").split(",");
                    sYear = words[0].trim();
                    sMonth = words[1].trim();
                    sToPay = words[2].trim();
                    sPaid = words[3].trim();

                    issues.add(new Issue(
                            new Client(name, address, id),
                            Integer.parseInt(sYear), Integer.parseInt(sMonth), Float.parseFloat(sToPay), Float.parseFloat(sPaid)));
                    b = true;
                }
            }
            br.close();
        } catch (Exception ex) {
            throw new IOElectricaException(ex);
        }
    }

    public boolean isClientPresent(Client client) {
        return this.clients.contains(client);
    }

    public boolean isIssuePresent(Issue issue) {
        return this.issues.contains(issue);
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void addIssue(Issue issue) {
        this.issues.add(issue);
    }

    public List<Issue> getClientIssues(Client client) {
        return this.issues.stream().filter(issue -> issue.getClient().equals(client)).collect(Collectors.toList());
    }

    public void saveChanges() throws IOElectricaException {
        try {
            File fClient = new File(fileClient);
            File fIssue = new File(issueFile);
            FileWriter pwClient = new FileWriter(fClient.getAbsolutePath());
            FileWriter pwIssue = new FileWriter(fIssue.getAbsolutePath());
            String s;
            try (BufferedWriter bwc = new BufferedWriter(pwClient)) {
                s = "";
                for (Iterator<Client> i = clients.iterator(); i.hasNext(); ) {
                    Client c = i.next();
                    s += c.toString() + System.getProperty(LINE_SEPARATOR);
                }
                bwc.write(s);
            }
            try (BufferedWriter bwi = new BufferedWriter(pwIssue)) {
                s = "";
                for (Iterator<Issue> i = issues.iterator(); i.hasNext(); ) {
                    Issue iss = i.next();
                    s += iss.toString() + System.getProperty(LINE_SEPARATOR);
                }
                bwi.write(s);
            }
        } catch (Exception ex) {
            throw new IOElectricaException(ex);
        }
    }

    public List<Issue> getInvoicesList() {
        return issues;
    }

    public List<Client> getClientsList() {
        return clients;
    }
}
