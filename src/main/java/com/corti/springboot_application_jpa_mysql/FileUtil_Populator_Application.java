package com.corti.springboot_application_jpa_mysql;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import org.apache.logging.log4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.corti.files.FileAttributes;
import com.corti.files.GetDirectoriesFromPath;
import com.corti.files.GetFileAttributesForDirectory;
import com.corti.springboot_application_jpa_mysql.fileAttributeSubset.FileAttributeSubset;
import com.corti.springboot_application_jpa_mysql.fileAttributeSubset.FileAttributeSubsetService;


//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FileUtil_Populator_Application  implements CommandLineRunner {
  private static Logger LOG = (Logger) LoggerFactory
      .getLogger(FileUtil_Populator_Application.class);
  static final boolean DEBUG = false;
  
  @Autowired 
  private FileAttributeSubsetService fileAttributeSubsetService;
  
  public static void main(String[] args) {
    LOG.info("STARTING THE APPLICATION");
    SpringApplication.run(FileUtil_Populator_Application.class, args);
    LOG.info("APPLICATION FINISHED");
  }

  @PostConstruct
  public void init() {
    // Setting Spring Boot TimeZone
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

  }

  @Override
  public void run(String... args) throws Exception {
    long numDirs = 0;
    long numFiles = 0;
    LOG.info("EXECUTING : command line runner");

    for (int i = 0; i < args.length; ++i) {
      LOG.info("args[{}]: {}", i, args[i]);
    }
    if (args.length < 2) return;
    
    // List<FileAttributeSubset> fileAttributeSubsetList = fileAttributeSubsetService.getAllFileAttributeSubsets();
    // for (FileAttributeSubset fas : fileAttributeSubsetList) {
    //   LOG.info(fas.toString());
     // }    
      
    //Path startingPath = Paths.get("/home/dev/workspace/");
    Path startingPath = Paths.get(args[1]);
    
    // This tests paths to be included in the search, for this one we show how you
    //   can include paths and ignore the 'case' of the directory name
    GetDirectoriesFromPath me = new GetDirectoriesFromPath(startingPath);
    me.setDebugFlag(false);
    me.setMaxDepth(100);
    me.setPathMatcherIgnoreCase(true);
    
    // me.setPaths2Include("glob:**/FileUtils/**");  // This won't match the FileUtils directory
    // me.setPaths2Include("glob:**/FileUtils**");   // This is if you want to match the FileUtils dir
    me.setPaths2Exclude("glob:**.{settings,git}");
    me.runIt();
    System.out.println("Number of directories: " + me.getFiles().size());
    
    List<Path> theDirectories = me.getFiles();
    for (Path aPath : theDirectories) {
      if (DEBUG) System.out.println("Processing directory: " + aPath.toString());
      if ((++numDirs % 50) == 0) System.out.println("Directories so far: " + numDirs);
      // Get the files
      GetFileAttributesForDirectory getFileAttributesForDirectory = 
          new GetFileAttributesForDirectory(aPath, startingPath);
      //getFileAttributesForDirectory.setPaths2Exclude("glob:**.{.settings,.git,sgitignore}");
      
      getFileAttributesForDirectory.setDebugFlag(false);
      List<FileAttributes> tempList = getFileAttributesForDirectory.getFilesAttributes();
      
      for (FileAttributes fileAttr : tempList) {
        if ((++numFiles % 200) == 0) System.out.println("  Files: " + numFiles);
        
        FileAttributeSubset fileAttributeSubset = new FileAttributeSubset(args[0],fileAttr);
        
        fileAttributeSubsetService.addOrUpdateFileAttributeSubset(fileAttributeSubset);
        if (DEBUG) System.out.println(fileAttributeSubset.toString());        
      }      
    } 
    System.out.println("Directories: " + numDirs + "  Files: " + numFiles + "\n\n");
  }
}
