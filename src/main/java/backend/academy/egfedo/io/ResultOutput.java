package backend.academy.egfedo.io;

public interface ResultOutput {

    enum Result {
        WIN, LOSE
    }

    void outputResult(Result result, String message);

}
