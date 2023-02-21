import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//TODO add customisable sleep()

public class BackupMaker extends Thread{

    private File save;
    private Long lastModified;
    private Long currentModified;
    private final String filePath;
    private final String backupPath;

    /**
     * This object stores two file paths which are used to copy a file/folder.
     * <p>
     * By default, a back-up is performed every 15 minutes.
     * <p>
     * If the object is interrupted, it will automatically call the backup() method.
     *
     * @param filePath the file/folder to be backed up
     * @param backupPath the path to which the file/folder should be copied to
     */
    public BackupMaker(String filePath, String backupPath){
        this.save = new File(filePath);
        this.lastModified = save.lastModified();
        this.currentModified = save.lastModified();
        this.filePath = filePath;
        this.backupPath = backupPath;
    }

    /**
     * Copies file/folder to the backup.
     *
     * @throws IOException if File cannot be found
     */
    public void backup() throws IOException{
        save = new File(this.filePath);
        System.out.println(save);
        File backup = new File(backupPath);
        System.out.println(backup);
        FileUtils.copyDirectory(save, backup);
        this.lastModified = this.currentModified;
    }

    /**
     * The BackupMaker creates a backup and then proceeds to wait.
     * <p>
     * If it is interrupted, it creates a backup and stops looping.
     */
    public void begin(){
        try{
            this.backup();
        }
        catch(IOException e){
            System.out.println("IOException on startup");
        }

        while(true){
            try{
                this.currentModified = this.save.lastModified();

                //Check if file has been modified
                if(this.currentModified > this.lastModified){
                    this.backup();
                }

                TimeUnit.MINUTES.sleep(15);
            }
            catch(IOException e){
                System.out.println("Encountered IOException");
                e.printStackTrace();
            }
            catch(InterruptedException e){
                try{
                    this.backup();
                    break;
                }
                catch (IOException f){
                    System.out.println("Encountered IOException while quitting");
                    f.printStackTrace();
                }
            }
        }
    }

//    while(true){
//        try{
//            currentSave.equals(save.lastModified());
//
//            //If the file has been modified
//            if(currentSave > lastSave){
//
//            }
//
//            TimeUnit.MINUTES.sleep(15);
//        }
//        catch(InterruptedException e){
//
//        }
//
//    }
}
