package modelPackage;

/**
 * Stores personal data of a Person. Information does not have to be present. It is possible
 * for all get methods
 * to return null, make sure to check for this.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class PersonalInformation {  //TODO: refine personal information

    private String initials;
    private String dateOfBirth;
    private String gender;
    private String healthInsuranceNumber;
    private String country;
    private String address;
    private String telephoneNumber;
    private String anamnesis;
    private String civilStatus;
    private String helpQuestion;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getHelpQuestion() {
        return helpQuestion;
    }

    public void setHelpQuestion(String helpQuestion) {
        this.helpQuestion = helpQuestion;
    }

}
