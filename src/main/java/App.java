public class App {
    public static String filePath = "C:\\Users\\AmazedAlloy\\AppData\\Roaming\\.minecraft\\saves\\Survival";
    public static String backupPath = "C:\\Users\\AmazedAlloy\\OneDrive\\Minecraft Worlds";

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start(){
        final BackupMaker backupMaker = new BackupMaker(filePath, backupPath);
        Thread quitHook = new Thread(backupMaker::interrupt);

        Runtime.getRuntime().addShutdownHook(quitHook);

        backupMaker.start();
        backupMaker.begin();
    }
}
