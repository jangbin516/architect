package Components.Middle;

public class EeRequiredCoursesFilter extends AbstractStudentFilter {
    @Override
    protected void applyRule(StudentRecord record) {
        if (!"EE".equals(record.getDepartment())) {
            return;
        }

        record.addCourseIfMissing("23456");
    }
}
