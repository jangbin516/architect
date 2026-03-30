package Components.Middle;

public class CsRequiredCoursesFilter extends AbstractStudentFilter {
    @Override
    protected void applyRule(StudentRecord record) {
        if (!"CS".equals(record.getDepartment())) {
            return;
        }

        record.addCourseIfMissing("12345");
        record.addCourseIfMissing("23456");
    }
}
