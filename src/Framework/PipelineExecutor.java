package Framework;

import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

final class PipelineExecutor {
    private PipelineExecutor() {
    }

    static void run(CommonFilter middleFilter, String outputFile) {
        try {
            CommonFilter sourceFilter = new SourceFilter("Students.txt");
            CommonFilter sinkFilter = new SinkFilter(outputFile);

            sourceFilter.connectOutputTo(middleFilter);
            middleFilter.connectOutputTo(sinkFilter);

            new Thread(sourceFilter).start();
            new Thread(middleFilter).start();
            new Thread(sinkFilter).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
