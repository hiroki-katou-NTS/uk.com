package nts.uk.shr.infra.data.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.time.GeneralDateTime;

/**
 * Base class of table entity
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TableEntity {
    
	@Convert(converter = GeneralDateTimeToDBConverter.class)
    @Column(name = "INS_DATE")
    private GeneralDateTime insDate;
    
    @Column(name = "INS_CCD")
    private String insCcd;
    
    @Column(name = "INS_SCD")
    private String insScd;
    
    @Column(name = "INS_PG")
    private String insPg;

    @Column(name = "UPD_DATE")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime updDate;
    
    @Column(name = "UPD_CCD")
    private String updCcd;
    
    @Column(name = "UPD_SCD")
    private String updScd;
    
    @Column(name = "UPD_PG")
    private String updPg;
    
    @PrePersist
    private void setInsertingMetaInfo() {
    	this.insDate = GeneralDateTime.now();
    	this.insCcd = "001";
    	this.insScd = "TEST_SCD";
    	this.insPg = "TEST_PG";
    }
    
    @PreUpdate
    private void setUpdatingMetaInfo() {
    	this.updDate = GeneralDateTime.now();
    	this.updCcd = "001";
    	this.updScd = "TEST_SCD";
    	this.updPg = "TEST_PG";
    }
}