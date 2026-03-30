package Framework;

import Components.Middle.RestrictionFor2013Filter;

public class LifeCycleManagerProblem3 {
    public static void main(String[] args) {
        PipelineExecutor.run(new RestrictionFor2013Filter(), "Output_problem3.txt");
    }
}
