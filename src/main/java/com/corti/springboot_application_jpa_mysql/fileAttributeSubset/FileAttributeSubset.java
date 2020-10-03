package com.corti.springboot_application_jpa_mysql.fileAttributeSubset;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.corti.files.FileAttributes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class FileAttributeSubset {
  @Id @GeneratedValue
  protected int id;
  
  @Column(length=32)
  protected String sourceIdentifier; // Source identifier (i.e. machine where files are)
  
  protected String fileName;         // filename
  
  @Column(length=1024)
  protected String pathParent;       // path 
  
  protected String fileExtension;    // Wanted to ignore certain types
  
  // @Temporal(TemporalType.TIMESTAMP)
  protected java.sql.Timestamp creationTime;
  //@Column(columnDefinition = "DATETIME(6)")
  // protected FileTime creationTime;
  // @Temporal(TemporalType.TIMESTAMP)
  protected java.sql.Timestamp lastModifiedTime;
  //@Column(columnDefinition = "DATETIME(6)")
  // protected FileTime lastModifiedTime;
  protected boolean isDirectory;
  protected boolean isRegularFile;
  protected boolean isSymbolicLink;
  protected long sizeInBytes;
  
  protected String checkSumValue;

  // default constructor
  public FileAttributeSubset() {
    id = 0;
    sizeInBytes = 0;
    sourceIdentifier = fileName = pathParent = fileExtension = checkSumValue = null;
    creationTime = lastModifiedTime = null;
    isDirectory = isRegularFile = isSymbolicLink= false;        
  }
  
  public FileAttributeSubset(String sourceIdentifier, FileAttributes fileAttributes) {
    setSourceIdentifier(sourceIdentifier);
    setFileName(fileAttributes.getFileName());
    setPathParent(fileAttributes.getAbsoluteParentPath());
    setFileExtension(fileAttributes.getFileExtension());
    setCreationTime(fileAttributes.getCreationTime());
    setLastModifiedTime(fileAttributes.getLastAccessTime());
    setIsDirectory(fileAttributes.isDirectory());
    setIsRegularFile(fileAttributes.isRegularFile());
    setIsSymbolicLink(fileAttributes.isSymbolicLink());
    setSizeInBytes(fileAttributes.getSizeInBytes());
    setCheckSumValue(fileAttributes.getCheckSumValue());
  }
    
  public int getId() {
    return id;
  }
  
  public String getSourceIdentifier() {
    return sourceIdentifier;
  }

  public String getFileName() {
    return fileName;
  }

  public String getPathParent() {
    return pathParent;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public java.sql.Timestamp getCreationTime() {
    return creationTime;
  }

  public java.sql.Timestamp getLastModifiedTime() {
    return lastModifiedTime;
  }

  public boolean getIsDirectory() {
    return isDirectory;
  }

  public boolean getIsRegularFile() {
    return isRegularFile;
  }

  public boolean getIsSymbolicLink() {
    return isSymbolicLink;
  }

  public long getSizeInBytes() {
    return sizeInBytes;
  }

  public String getCheckSumValue() {
    return checkSumValue;
  }

  // -----------------------------------
  public void setId(int id) {
    this.id = id;
  }

  public void setSourceIdentifier(String sourceIdentifier) {
    this.sourceIdentifier = sourceIdentifier;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setPathParent(String pathParent) {
    this.pathParent = pathParent;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public void setCreationTime(java.sql.Timestamp creationTime) {
    this.creationTime = creationTime;
  }

  // When given a FileTime
  public void setCreationTime(FileTime creationTime) {
    this.creationTime = new java.sql.Timestamp(creationTime.toMillis());
  }
  
  public void setLastModifiedTime(java.sql.Timestamp lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public void setLastModifiedTime(FileTime lastModifiedTime) {
    this.lastModifiedTime = new java.sql.Timestamp(lastModifiedTime.toMillis());
  }

  public void setIsDirectory(boolean isDirectory) {
    this.isDirectory = isDirectory;
  }

  public void setIsRegularFile(boolean isRegularFile) {
    this.isRegularFile = isRegularFile;
  }

  public void setIsSymbolicLink(boolean isSymbolicLink) {
    this.isSymbolicLink = isSymbolicLink;
  }

  public void setSizeInBytes(long sizeInBytes) {
    this.sizeInBytes = sizeInBytes;
  }

  public void setCheckSumValue(String checkSumValue) {
    this.checkSumValue = checkSumValue;
  }

  @Override
  public String toString() {
    return "FileAttributeSubset [id=" + id + ", fileName=" + fileName + ", pathParent=" + pathParent
        + ", fileExtension=" + fileExtension + ", creationTime=" + creationTime + ", lastModifiedTime="
        + lastModifiedTime + ", isDirectory=" + isDirectory + ", isRegularFile=" + isRegularFile + ", isSymbolicLink="
        + isSymbolicLink + ", sizeInBytes=" + sizeInBytes + ", checkSumValue=" + checkSumValue + "]";
  } 
}