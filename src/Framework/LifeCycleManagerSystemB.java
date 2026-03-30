package Framework;

import Components.Middle.PrerequisiteValidationFilter;
import Components.Sink.SplitSinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManagerSystemB {
    public static void main(String[] args) {
        try {
            CommonFilter sourceFilter = new SourceFilter("Students.txt");
            CommonFilter prerequisiteValidationFilter = new PrerequisiteValidationFilter("Courses.txt");
            CommonFilter splitSinkFilter = new SplitSinkFilter("Output-1.txt", "Output-2.txt");

            sourceFilter.connectOutputTo(prerequisiteValidationFilter);
            prerequisiteValidationFilter.connectOutputTo(splitSinkFilter);

            new Thread(sourceFilter).start();
            new Thread(prerequisiteValidationFilter).start();
            new Thread(splitSinkFilter).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
