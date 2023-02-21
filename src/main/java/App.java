public class App {

    public static void main(String[] args) {
        App app = new App();
        app.start(args);
    }

    public void start(String[] args){
        final BackupMaker backupMaker = new BackupMaker(args[0], args[1]);
        Thread quitHook = new Thread(() -> {
            System.out.println("Triggered quit hook");
            backupMaker.interrupt();
        });

        Runtime.getRuntime().addShutdownHook(quitHook);

        backupMaker.start();
        backupMaker.begin();
    }
}
