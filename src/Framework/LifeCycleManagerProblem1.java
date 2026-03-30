package Framework;

import Components.Middle.CsRequiredCoursesFilter;

public class LifeCycleManagerProblem1 {
    public static void main(String[] args) {
        PipelineExecutor.run(new CsRequiredCoursesFilter(), "Output_problem1.txt");
    }
}
