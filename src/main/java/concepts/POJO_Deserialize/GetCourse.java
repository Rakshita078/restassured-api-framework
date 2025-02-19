package concepts.POJO_Deserialize;

public class GetCourse {
    private String url;
    private String services;
    private String expertise;
    private Courses courses;
    private String linkedIn;
    private String instructor;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public concepts.POJO_Deserialize.Courses getCourses() {
        return courses;
    }

    public void setCourses(concepts.POJO_Deserialize.Courses courses) {
        this.courses = courses;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
