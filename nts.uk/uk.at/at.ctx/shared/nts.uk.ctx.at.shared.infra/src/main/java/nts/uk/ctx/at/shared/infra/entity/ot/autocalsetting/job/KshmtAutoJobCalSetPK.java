/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtAutoJobCalSetPK.
 */

/**
 * Gets the jobid.
 *
 * @return the jobid
 */
@Getter

/**
 * Sets the jobid.
 *
 * @param jobid the new jobid
 */
@Setter
@Embeddable
public class KshmtAutoJobCalSetPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;

    /** The jobid. */
    @Column(name = "JOBID")
    private String jobid;

    /**
     * Instantiates a new kshmt auto job cal set PK.
     */
    public KshmtAutoJobCalSetPK() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto job cal set PK.
     *
     * @param cid the cid
     * @param jobid the jobid
     */
    public KshmtAutoJobCalSetPK(String cid, String jobid) {
        this.cid = cid;
        this.jobid = jobid;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash +=  (jobid != null ? jobid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoJobCalSetPK)) {
            return false;
        }
        KshmtAutoJobCalSetPK other = (KshmtAutoJobCalSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.jobid != other.jobid) {
            return false;
        }
        return true;
    }

}
