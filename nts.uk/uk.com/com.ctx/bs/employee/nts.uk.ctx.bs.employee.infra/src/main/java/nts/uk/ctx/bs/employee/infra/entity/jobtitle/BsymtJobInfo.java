/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.bs.employee.infra.entity.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtJobInfo.
 *
 * @author NWS_THANHNC_PC
 */
@Getter
@Setter
@Entity
@Table(name = "BSYMT_JOB_INFO")
public class BsymtJobInfo extends UkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The bsymt job info PK. */
    @EmbeddedId
    protected BsymtJobInfoPK bsymtJobInfoPK;
        
    /** The job cd. */
    @Column(name = "JOB_CD")
    private String jobCd;
       
    /** The job name. */
    @Column(name = "JOB_NAME")
    private String jobName;
      
    /** The sequence cd. */
    @Column(name = "SEQUENCE_CD")
    private String sequenceCd;

    /**
     * Instantiates a new bsymt job info.
     */
    public BsymtJobInfo() {
    }

    /**
     * Instantiates a new bsymt job info.
     *
     * @param bsymtJobInfoPK the bsymt job info PK
     */
    public BsymtJobInfo(BsymtJobInfoPK bsymtJobInfoPK) {
        this.bsymtJobInfoPK = bsymtJobInfoPK;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsymtJobInfoPK != null ? bsymtJobInfoPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsymtJobInfo)) {
            return false;
        }
        BsymtJobInfo other = (BsymtJobInfo) object;
        if ((this.bsymtJobInfoPK == null && other.bsymtJobInfoPK != null) || (this.bsymtJobInfoPK != null && !this.bsymtJobInfoPK.equals(other.bsymtJobInfoPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.bsymtJobInfoPK;
	}
    
}
