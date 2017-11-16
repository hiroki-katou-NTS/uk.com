/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtScheduleExcLogPK.
 */
@Getter
@Setter
@Embeddable
public class KscdtScheExeLogPK implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The exe id. */
    @NotNull
    @Column(name = "EXE_ID")
    private String exeId;

    /**
     * Instantiates a new kscmt schedule exc log PK.
     */
    public KscdtScheExeLogPK() {
    }

    /**
     * Instantiates a new kscmt schedule exc log PK.
     *
     * @param companyId the company id
     * @param executionId the execution id
     */
    public KscdtScheExeLogPK(String companyId, String executionId) {
    	this.cid = companyId;
    	this.exeId = executionId;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (exeId != null ? exeId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtScheExeLogPK)) {
            return false;
        }
        KscdtScheExeLogPK other = (KscdtScheExeLogPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtScheduleExcLogPK[ cid=" + cid + ", exeId=" + exeId + " ]";
    }
    
}
