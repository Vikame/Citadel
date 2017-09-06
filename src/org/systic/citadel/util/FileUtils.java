package org.systic.citadel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

public class FileUtils {

    public static File recreate(File file){
        try{
            file.delete();
            file.createNewFile();
        }catch(IOException ignored){}
        return file;
    }

    public static List<String> read(File file){
        List<String> result = new ArrayList<>();
        try{
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            while((line = reader.readLine()) != null) result.add(line);

            reader.close();
            fr.close();
        }catch(Exception ignored){}
        return result;
    }

    public static void write(File file, String line){
        try{
            FileWriter fw = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(fw);

            out.println(line);

            out.close();
            fw.close();
        }catch(Exception ignored){}
    }

    public static File getOrCreateFolder(File folder, String name){
        File file = new File(folder, name);
        file.mkdirs();
        return file;
    }

    public static File getOrCreate(File folder, String name){
        File file = new File(folder, name);
        if(!folder.exists()){
            folder.mkdirs();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void saveDefault(Plugin plugin, File folder, String name){
        File outFile = FileUtils.getOrCreate(folder, name);

        try {
            InputStream in = plugin.getResource(name);

            FileOutputStream ex = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0) {
                ex.write(buf, 0, len);
            }

            ex.close();
            in.close();
        } catch (IOException ignored){}
    }
}
