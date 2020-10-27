/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtCalcSetWkpJobPK.
 */

/**
 * Sets the jobid.
 *
 * @param jobid the new jobid
 */

/**
 * Sets the jobid.
 *
 * @param jobid the new jobid
 */
@Setter

/**
 * Gets the jobid.
 *
 * @return the jobid
 */

/**
 * Gets the jobid.
 *
 * @return the jobid
 */
@Getter	
@Embeddable
public class KrcmtCalcSetWkpJobPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;

    /** The wpkid. */
    @Column(name = "WPKID")
    private String wpkid;

    /** The jobid. */
    @Column(name = "JOBID")
    private String jobid;

    /**
     * Instantiates a new kshmt auto wkp job cal PK.
     */
    public KrcmtCalcSetWkpJobPK() {
    	super();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (wpkid != null ? wpkid.hashCode() : 0);;
        hash += (jobid != null ? jobid.hashCode() : 0);;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcmtCalcSetWkpJobPK)) {
            return false;
        }
        KrcmtCalcSetWkpJobPK other = (KrcmtCalcSetWkpJobPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.wpkid != other.wpkid) {
            return false;
        }
        if (this.jobid != other.jobid) {
            return false;
        }
        return true;
    }

	/**
	 * Instantiates a new kshmt auto wkp job cal PK.
	 *
	 * @param cid the cid
	 * @param wpkid the wpkid
	 * @param jobid the jobid
	 */
	public KrcmtCalcSetWkpJobPK(String cid, String wpkid, String jobid) {
		super();
		this.cid = cid;
		this.wpkid = wpkid;
		this.jobid = jobid;
	}

}
