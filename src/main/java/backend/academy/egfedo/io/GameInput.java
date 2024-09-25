package backend.academy.egfedo.io;

public interface GameInput {

    Command getCommand();

    record Command(Type type, String data) {
        public enum Type {
            CHAR, STRING
        }
    }
}
