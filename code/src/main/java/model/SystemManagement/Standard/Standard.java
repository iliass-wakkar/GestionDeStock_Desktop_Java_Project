package model.SystemManagement.Standard;

import java.util.ArrayList;
import java.util.UUID;

public class Standard {
    private String idStandard;
    private String name;
    private String description;
    private String reference;

    private ArrayList<Clause> clauses = new ArrayList<>();


    public Standard() {
        this.idStandard = UUID.randomUUID().toString();
        this.name = "unknown";
        this.description = "unknown";
        this.reference = "unknown";

    }

    public Standard(String idStandard, String name, String description, String reference, Process process, ArrayList<Clause> clauses) {
        this.idStandard = idStandard;
        this.name = name;
        this.description = description;
        this.reference = reference;

        this.clauses = clauses;
    }
    public void editStandard(Standard updatedStandard){
         this.setName(updatedStandard.getName());
         this.setDescription(updatedStandard.getDescription());
         this.setReference(updatedStandard.getReference());
    }



    public String getIdStandard() {
        return idStandard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }



    public ArrayList<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(ArrayList<Clause> clauses) {
        this.clauses = clauses;
    }

    @Override
    public String toString() {
        return "Standard{" +
                "id='" + idStandard + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reference='" + reference + '\'' +
                ", clauses=" + clauses +
                '}';
    }
}
