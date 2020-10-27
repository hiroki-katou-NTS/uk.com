/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

/**
 * The Class KrcmtCalcSetJob.
 */
@Setter
@Getter
@Entity
@Table(name = "KRCMT_CALC_SET_JOB")
public class KrcmtCalcSetJob extends KshmtAutoCalSet implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto job cal set PK. */
    @EmbeddedId
    protected KrcmtCalcSetJobPK krcmtCalcSetJobPK;

    /**
     * Instantiates a new kshmt auto job cal set.
     */
    public KrcmtCalcSetJob() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto job cal set.
     *
     * @param krcmtCalcSetJobPK the kshmt auto job cal set PK
     */
    public KrcmtCalcSetJob(KrcmtCalcSetJobPK krcmtCalcSetJobPK) {
        this.krcmtCalcSetJobPK = krcmtCalcSetJobPK;
    }

    /**
     * Instantiates a new kshmt auto job cal set.
     *
     * @param cid the cid
     * @param jobid the jobid
     */
    public KrcmtCalcSetJob(String cid, String jobid) {
        this.krcmtCalcSetJobPK = new KrcmtCalcSetJobPK(cid, jobid);
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcmtCalcSetJobPK != null ? krcmtCalcSetJobPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KrcmtCalcSetJob)) {
            return false;
        }
        KrcmtCalcSetJob other = (KrcmtCalcSetJob) object;
        if ((this.krcmtCalcSetJobPK == null && other.krcmtCalcSetJobPK != null) || (this.krcmtCalcSetJobPK != null && !this.krcmtCalcSetJobPK.equals(other.krcmtCalcSetJobPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcmtCalcSetJobPK;
	}
    
}
