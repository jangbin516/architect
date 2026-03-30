package Framework;

import Components.Middle.EeRequiredCoursesFilter;

public class LifeCycleManagerProblem2 {
    public static void main(String[] args) {
        PipelineExecutor.run(new EeRequiredCoursesFilter(), "Output_problem2.txt");
    }
}
