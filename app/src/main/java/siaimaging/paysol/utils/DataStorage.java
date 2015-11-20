package siaimaging.paysol.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import siaimaging.paysol.domain.User;

/**
 * Created by dna on 11/5/15.
 */
public class DataStorage{
    public static String FaceVideoStorageFile="28716c17230737bac15823aa8f681be4";
    public static String PersonalDataStorageFile="3eb0720afe5e81600182f78025dc5559";
    public static String CSVSeprator = ",";

    private static DataStorage theInstance = new DataStorage();
    private static Context sContext;
    private String className = this.getClass().getName();


    /*
    * Use this method for getting the instance of data Storage.
    */
    public static DataStorage getInstance() {
        return theInstance;
    }
    public void setContext(Context context){
        Log.i(className, "Contex Set : "+context.getApplicationInfo().toString());
        sContext = context;
    }
    private DataStorage() {
    }
    public OutputStream createPrivateFile(String name) throws FileNotFoundException{
        // Create a path where we will place our private file on external
        // storage.
        File file = new File(sContext.getExternalFilesDir(null),name);
        OutputStream os = new FileOutputStream(file);
        return os;
    }

    public void deletePrivateFile(String name) {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = new File(sContext.getExternalFilesDir(null), name);
        if (file != null) {
            file.delete();
        }
    }

    public boolean hasPrivateFile(String name) {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = new File(sContext.getExternalFilesDir(null), name);
        Log.i(className, "File path is :  "+ file.getAbsolutePath());
        if (file != null) {
            return file.exists();
        }
        return false;
    }
    public void createUser(User user) throws IOException{
        OutputStream userFile = createPrivateFile(PersonalDataStorageFile);
        user.setId(0L);
        userFile.write(user.toCSVString(CSVSeprator).getBytes());
    }

    public User getUser() throws IOException{
        Log.i(className,"getUser() called");
        if(hasPrivateFile(PersonalDataStorageFile)){
            File file = new File(sContext.getExternalFilesDir(null),PersonalDataStorageFile);
            FileInputStream userFile = new FileInputStream(file);
            long fileSize = file.length();
            Log.i(className, "file.getTotalSpace() : " + file.getTotalSpace());
            Log.i(className, "file.getUsableSpace() : " + file.getUsableSpace());
            Log.i(className, "fileSize : " + (int)fileSize);
            byte[] data = new byte[((int) fileSize)];
            if(userFile.read(data,0,((int) fileSize)) > 0){
                String userData = new String(data);
                return new User(userData,CSVSeprator);
            }else {
              return null;
            }
        }else{
            return null;
        }
    }
}
