package Components.Sink;

import Framework.CommonFilterImpl;

import java.io.FileWriter;
import java.io.IOException;

public class SplitSinkFilter extends CommonFilterImpl {
    private final String passFile;
    private final String failFile;

    public SplitSinkFilter(String passFile, String failFile) {
        this.passFile = passFile;
        this.failFile = failFile;
    }

    @Override
    public boolean specificComputationForFilter() throws IOException {
        try (FileWriter passWriter = new FileWriter(passFile);
             FileWriter failWriter = new FileWriter(failFile)) {

            StringBuilder lineBuilder = new StringBuilder();
            int byteRead;

            while ((byteRead = in.read()) != -1) {
                if (byteRead == '\r') {
                    continue;
                }
                if (byteRead == '\n') {
                    writeByStatus(lineBuilder.toString(), passWriter, failWriter);
                    lineBuilder.setLength(0);
                } else {
                    lineBuilder.append((char) byteRead);
                }
            }

            if (lineBuilder.length() > 0) {
                writeByStatus(lineBuilder.toString(), passWriter, failWriter);
            }
        }

        System.out.print("::System B filtering is finished; Output-1.txt and Output-2.txt are created.");
        return true;
    }

    private void writeByStatus(String taggedLine, FileWriter passWriter, FileWriter failWriter) throws IOException {
        String line = taggedLine.trim();
        if (line.isEmpty()) {
            return;
        }

        int separatorIndex = line.indexOf('|');
        if (separatorIndex <= 0 || separatorIndex >= line.length() - 1) {
            return;
        }

        String status = line.substring(0, separatorIndex);
        String studentLine = line.substring(separatorIndex + 1);

        if ("PASS".equals(status)) {
            passWriter.write(studentLine + "\n");
        } else if ("FAIL".equals(status)) {
            failWriter.write(studentLine + "\n");
        }
    }
}
