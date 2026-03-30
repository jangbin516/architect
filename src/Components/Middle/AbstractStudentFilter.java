package Components.Middle;

import Framework.CommonFilterImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class AbstractStudentFilter extends CommonFilterImpl {
    @Override
    public boolean specificComputationForFilter() throws IOException {
        StringBuilder lineBuilder = new StringBuilder();
        int byteRead;

        while ((byteRead = in.read()) != -1) {
            if (byteRead == '\r') {
                continue;
            }
            if (byteRead == '\n') {
                processLine(lineBuilder.toString());
                lineBuilder.setLength(0);
            } else {
                lineBuilder.append((char) byteRead);
            }
        }

        if (lineBuilder.length() > 0) {
            processLine(lineBuilder.toString());
        }

        return true;
    }

    private void processLine(String rawLine) throws IOException {
        String line = rawLine.trim();
        if (line.isEmpty()) {
            return;
        }

        StudentRecord record = StudentRecord.fromLine(line);
        applyRule(record);
        out.write((record.toLine() + "\n").getBytes());
    }

    protected abstract void applyRule(StudentRecord record);

    protected static class StudentRecord {
        private final String studentId;
        private final String firstName;
        private final String lastName;
        private final String department;
        private final List<String> courseIds;

        private StudentRecord(String studentId, String firstName, String lastName, String department, List<String> courseIds) {
            this.studentId = studentId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.department = department;
            this.courseIds = courseIds;
        }

        static StudentRecord fromLine(String line) {
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length < 4) {
                throw new IllegalArgumentException("잘못된 학생 데이터 형식: " + line);
            }

            List<String> courses = new ArrayList<>();
            if (tokens.length > 4) {
                courses.addAll(Arrays.asList(tokens).subList(4, tokens.length));
            }

            return new StudentRecord(tokens[0], tokens[1], tokens[2], tokens[3], courses);
        }

        String getStudentId() {
            return studentId;
        }

        String getDepartment() {
            return department;
        }

        boolean hasCourse(String courseId) {
            return courseIds.contains(courseId);
        }

        void addCourseIfMissing(String courseId) {
            if (!hasCourse(courseId)) {
                courseIds.add(courseId);
            }
        }

        void removeCourse(String courseId) {
            courseIds.removeIf(courseId::equals);
        }

        String toLine() {
            StringBuilder builder = new StringBuilder();
            builder.append(studentId).append(' ')
                    .append(firstName).append(' ')
                    .append(lastName).append(' ')
                    .append(department);

            for (String courseId : courseIds) {
                builder.append(' ').append(courseId);
            }
            return builder.toString();
        }
    }
}
