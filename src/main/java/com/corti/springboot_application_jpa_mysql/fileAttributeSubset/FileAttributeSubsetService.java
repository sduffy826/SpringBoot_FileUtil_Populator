package com.corti.springboot_application_jpa_mysql.fileAttributeSubset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileAttributeSubsetService {
  static final boolean DEBUG = false;
  
  @Autowired
  private FileAttributeSubsetRepository fileAttributeSubsetRepository;
  
  public List<FileAttributeSubset> getAllFileAttributeSubsets() {
    System.out.println("In getAllFileAttributeSubsets()");
    List<FileAttributeSubset> fileAttributeSubsetList = new ArrayList<>();
    fileAttributeSubsetRepository.findAll()
      .forEach(fileAttributeSubsetList::add);  // add element to the array
    return fileAttributeSubsetList;    
  }

  // Had an old way similar to:
  //   when we were using the array: return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();

  public FileAttributeSubset getFileAttributeSubset(int id) {
    return fileAttributeSubsetRepository.findById(id).orElse(null);
  }
  
  // Should only be one record per source, path, filename so we only return that
  public FileAttributeSubset getFileAttributeSubsetBySourcePathFileName(String src, String pth, String fn) {
    List<FileAttributeSubset> fAS = fileAttributeSubsetRepository.findBySourceIdentifierAndPathParentAndFileName(src, pth, fn);
    if (fAS.size() > 0) {
      return fAS.get(0);
    }
    else {
      return null;
    }
  }
  
  public void addFileAttributeSubset(FileAttributeSubset fileAttributeSubset) {
    fileAttributeSubsetRepository.save(fileAttributeSubset);
  }
  
  public void addOrUpdateFileAttributeSubset(FileAttributeSubset fileAttributeSubset) {
    FileAttributeSubset existingFileAttributeSubset = getFileAttributeSubsetBySourcePathFileName(
        fileAttributeSubset.sourceIdentifier, fileAttributeSubset.pathParent, fileAttributeSubset.fileName);
    if (existingFileAttributeSubset != null) {
      if (fileAttributeSubsetsDiffer(fileAttributeSubset, existingFileAttributeSubset)) {
        if (DEBUG) {
          System.out.println("Updating record on file, old below");
          System.out.println(existingFileAttributeSubset); 
        }
        if (fileAttributeSubset.id == 0) { // Use id in the existing record
          fileAttributeSubset.setId(existingFileAttributeSubset.id);
        }
        else
          if (fileAttributeSubset.id != existingFileAttributeSubset.id) {
          // This should never happen... we have a dupe.. we'll delete the existing record
          deleteFileAttributeSubset(existingFileAttributeSubset.id);
        }
        updateFileAttributeSubset(fileAttributeSubset);
      }
      else {
        if (DEBUG) System.out.println("Argument is the same no update necessary");
      }
    }
    else {
      if (DEBUG) System.out.println("Adding record");
      addFileAttributeSubset(fileAttributeSubset);
    }
  }

  public void updateFileAttributeSubset(FileAttributeSubset fileAttributeSubset) {
    if (DEBUG) System.out.println(String.format("updatefileAttributeSubset: %s",fileAttributeSubset.toString()));
    fileAttributeSubsetRepository.save(fileAttributeSubset);
  }
  
  public void deleteFileAttributeSubset(int id) {
    fileAttributeSubsetRepository.deleteById(id);
  }
  
  // compare two file attribute subsets, did order so that least fields should be compared, or the
  // easiest compares are first
  // Does not compare ID
  public boolean fileAttributeSubsetsDiffer(FileAttributeSubset fas1, FileAttributeSubset fas2) {
    if (DEBUG) {
      if (fas1.sizeInBytes != fas2.sizeInBytes) System.out.println("**Size");
      if (fas1.checkSumValue.equals(fas2.checkSumValue) == false) {
        System.out.println("**CheckSum");
        System.out.println(fas1.checkSumValue+"]]");
        System.out.println(fas2.checkSumValue+"]]");
      }
      if (fas1.lastModifiedTime.compareTo(fas2.lastModifiedTime) != 0) System.out.println("**Mod time");
      if (fas1.creationTime.compareTo(fas2.creationTime) != 0) {
        System.out.println("**Creation time");
        System.out.println(fas1.creationTime.getTime());
        System.out.println(fas2.creationTime.getTime());

      }
      if (fas1.isDirectory != fas2.isDirectory) System.out.println("**isDir");
      if (fas1.isRegularFile != fas2.isRegularFile) System.out.println("**isReg");
      if (fas1.isSymbolicLink != fas2.isSymbolicLink) System.out.println("**isSym");
      if (fas1.sourceIdentifier.equals(fas2.sourceIdentifier) == false) System.out.println("**sourceId");
      if (fas1.pathParent.equals(fas2.pathParent) == false) System.out.println("**pathParent");
      if (fas1.fileName.equals(fas2.fileName) == false) System.out.println("**fileName");
      if (fas1.fileExtension.equals(fas2.fileExtension) == false)  System.out.println("**fileExt");
    }
    if ( (fas1.sizeInBytes != fas2.sizeInBytes) ||
         (fas1.checkSumValue.equals(fas2.checkSumValue) == false) ||
         (fas1.lastModifiedTime != fas2.lastModifiedTime) ||
         (fas1.creationTime != fas2.creationTime) ||
         (fas1.isDirectory != fas2.isDirectory) ||
         (fas1.isRegularFile != fas2.isRegularFile) ||
         (fas1.isSymbolicLink != fas2.isSymbolicLink) ||
         (fas1.sourceIdentifier.equals(fas2.sourceIdentifier) == false) ||
         (fas1.pathParent.equals(fas2.pathParent) == false) ||
         (fas1.fileName.equals(fas2.fileName) == false) ||
         (fas1.fileExtension.equals(fas2.fileExtension) == false))
      return true;
    else
      return false;    
  }  
}
