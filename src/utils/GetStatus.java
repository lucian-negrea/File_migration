/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import beans.Bugs;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author RO100051
 */
public class GetStatus extends Thread{
    public static ArrayList<String> getDirectories(String path){
        File f = new File(path);
        ArrayList<String> directoryList = new ArrayList<String>(Arrays.asList(f.list()));
        return directoryList;
    }
    
}
