public class App {

    public static void main(String[] args) {
        String source = args[0];
        String destination = args[1];

        App app = new App();
        app.start(source, destination);
    }

    public void start(String source, String destination){
        final BackupMaker backupMaker = new BackupMaker(source, destination);
        Thread quitHook = new Thread(() -> {
            System.out.println("Triggered quit hook");
            backupMaker.interrupt();
        });

        Runtime.getRuntime().addShutdownHook(quitHook);

        backupMaker.start();
        backupMaker.begin();
    }
}
