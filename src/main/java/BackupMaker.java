import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BackupMaker extends Thread{


    private File save;
    private Long lastModified;
    private Long currentModified;
    private final String filePath;
    private final String backupPath;

    public BackupMaker(String filePath, String backupPath){
        this.save = new File(filePath);
        this.lastModified = save.lastModified();
        this.currentModified = save.lastModified();
        this.filePath = filePath;
        this.backupPath = backupPath;
    }

    public void backup() throws IOException{
        save = new File(this.filePath);
        File backup = new File(backupPath);
        FileUtils.copyDirectory(save, backup);
        this.lastModified = this.currentModified;
    }

    public void begin(){
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
