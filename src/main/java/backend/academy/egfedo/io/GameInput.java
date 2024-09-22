package backend.academy.egfedo.io;

public interface GameInput {

    record Command(Type type, String data) {
        public enum Type {
            CHAR, STRING
        }
    }

    Command getCommand();
}
