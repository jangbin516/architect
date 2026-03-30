/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

public class LifeCycleManager {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException(
                    "Run one problem at a time: Framework.LifeCycleManagerProblem1 | Problem2 | Problem3");
        }

        switch (args[0]) {
            case "1":
                LifeCycleManagerProblem1.main(new String[0]);
                break;
            case "2":
                LifeCycleManagerProblem2.main(new String[0]);
                break;
            case "3":
                LifeCycleManagerProblem3.main(new String[0]);
                break;
            default:
                throw new IllegalArgumentException("Usage: java -cp src Framework.LifeCycleManager [1|2|3]");
        }
    }
}
