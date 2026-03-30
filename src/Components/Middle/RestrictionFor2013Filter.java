package Components.Middle;

public class RestrictionFor2013Filter extends AbstractStudentFilter {
    @Override
    protected void applyRule(StudentRecord record) {
        boolean is2013Student = record.getStudentId().startsWith("2013");
        boolean isCsStudent = "CS".equals(record.getDepartment());

        if (!is2013Student || isCsStudent) {
            return;
        }

        record.removeCourse("17651");
        record.removeCourse("17652");
    }
}
