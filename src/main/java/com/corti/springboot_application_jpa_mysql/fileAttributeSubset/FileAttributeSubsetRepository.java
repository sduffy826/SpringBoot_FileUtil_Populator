package com.corti.springboot_application_jpa_mysql.fileAttributeSubset;

import java.util.List;

import javax.persistence.Column;

import org.springframework.data.repository.CrudRepository;

public interface FileAttributeSubsetRepository extends CrudRepository<FileAttributeSubset, Integer> {
  // Was going to build query but found it has And logic so didn't need to, it basically equates to same though
  //@Query("select a from FileAttributeSubset a where a.sourceIdentifier=?1 and a.pathParent=?2 and a.fileName = ?3")
  //list<FileAttributeSubset> findBySource
  
  // Get by source path and filename
  List<FileAttributeSubset> findBySourceIdentifierAndPathParentAndFileName(String si, String pt, String fn);
  
  List<FileAttributeSubset> findByCheckSumValue(String checkSumValue);
  
}
