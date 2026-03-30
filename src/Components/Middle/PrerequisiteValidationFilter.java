package Components.Middle;

import Framework.CommonFilterImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrerequisiteValidationFilter extends CommonFilterImpl {
    private final String coursesFile;
    private final Map<String, List<String>> prerequisiteByCourse = new HashMap<>();

    public PrerequisiteValidationFilter(String coursesFile) {
        this.coursesFile = coursesFile;
    }

    @Override
    public boolean specificComputationForFilter() throws IOException {
        loadCoursePrerequisites();

        StringBuilder lineBuilder = new StringBuilder();
        int byteRead;

        while ((byteRead = in.read()) != -1) {
            if (byteRead == '\r') {
                continue;
            }
            if (byteRead == '\n') {
                processStudentLine(lineBuilder.toString());
                lineBuilder.setLength(0);
            } else {
                lineBuilder.append((char) byteRead);
            }
        }

        if (lineBuilder.length() > 0) {
            processStudentLine(lineBuilder.toString());
        }

        return true;
    }

    private void loadCoursePrerequisites() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(coursesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] tokens = trimmed.split("\\s+");
                if (tokens.length < 3) {
                    continue;
                }

                String courseId = tokens[0];
                List<String> prerequisites = new ArrayList<>();
                if (tokens.length > 3) {
                    prerequisites.addAll(Arrays.asList(tokens).subList(3, tokens.length));
                }

                prerequisiteByCourse.put(courseId, prerequisites);
            }
        }
    }

    private void processStudentLine(String rawLine) throws IOException {
        String line = rawLine.trim();
        if (line.isEmpty()) {
            return;
        }

        String[] tokens = line.split("\\s+");
        if (tokens.length < 4) {
            return;
        }

        List<String> registeredCourses = new ArrayList<>();
        if (tokens.length > 4) {
            registeredCourses.addAll(Arrays.asList(tokens).subList(4, tokens.length));
        }

        boolean valid = hasAllPrerequisites(registeredCourses);
        String status = valid ? "PASS" : "FAIL";
        out.write((status + "|" + line + "\n").getBytes());
    }

    private boolean hasAllPrerequisites(List<String> registeredCourses) {
        Set<String> courseSet = new HashSet<>(registeredCourses);

        for (String courseId : registeredCourses) {
            List<String> prerequisites = prerequisiteByCourse.get(courseId);
            if (prerequisites == null || prerequisites.isEmpty()) {
                continue;
            }

            for (String prerequisite : prerequisites) {
                if (!courseSet.contains(prerequisite)) {
                    return false;
                }
            }
        }

        return true;
    }
}
