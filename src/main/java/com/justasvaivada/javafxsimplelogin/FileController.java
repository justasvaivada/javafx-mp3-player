package com.justasvaivada.javafxsimplelogin;

import java.io.File;

public class FileController {

    public static File[] getMusicFiles (File fileDir) {

        var listOfFiles = fileDir.listFiles();

        if (listOfFiles == null){
            System.out.println("Skipped - " + fileDir);
            return null;
        }
        for (File x: listOfFiles) {
            if (x.isDirectory()){
                getMusicFiles(x);
            }
            else if (x.getName().endsWith(".mp3")) {
                System.out.println(x.getPath());
            }
        }
        return listOfFiles;
    }

    public static File getSong (File[] musicFiles, int fileIndex) {
        File song = null;
        try {
            song = musicFiles[fileIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return song;
    }

    public static String getSongName(File song) {
        return song.getName();
    }

}
