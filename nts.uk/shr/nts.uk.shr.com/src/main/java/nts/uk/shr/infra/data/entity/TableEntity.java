package nts.uk.shr.infra.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.LocalDateTimeToDBConverter;

/**
 * Base class of table entity
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TableEntity {
    
	@Convert(converter = LocalDateTimeToDBConverter.class)
    @Column(name = "INS_DATE")
    private LocalDateTime insDate;
    
    @Column(name = "INS_CCD")
    private String insCcd;
    
    @Column(name = "INS_SCD")
    private String insScd;
    
    @Column(name = "INS_PG")
    private String insPg;

	@Convert(converter = LocalDateTimeToDBConverter.class)
    @Column(name = "UPD_DATE")
    private LocalDateTime updDate;
    
    @Column(name = "UPD_CCD")
    private String updCcd;
    
    @Column(name = "UPD_SCD")
    private String updScd;
    
    @Column(name = "UPD_PG")
    private String updPg;
    
    @PrePersist
    private void setInsertingMetaInfo() {
    	this.insDate = LocalDateTime.now();
    	this.insCcd = "001";
    	this.insScd = "TEST_SCD";
    	this.insPg = "TEST_PG";
    }
    
    @PreUpdate
    private void setUpdatingMetaInfo() {
    	this.updDate = LocalDateTime.now();
    	this.updCcd = "001";
    	this.updScd = "TEST_SCD";
    	this.updPg = "TEST_PG";
    }
}