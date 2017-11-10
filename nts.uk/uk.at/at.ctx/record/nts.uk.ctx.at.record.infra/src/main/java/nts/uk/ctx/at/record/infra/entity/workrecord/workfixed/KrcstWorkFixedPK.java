/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.workfixed;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class KrcstWorkFixedPK.
 */
@Embeddable
public class KrcstWorkFixedPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The wkpid. */
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "WKPID")
    private String wkpid;
	
    /** The closure id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLOSURE_ID")
    private int closureId;
    
    /** The cid. */
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;

    /**
     * Instantiates a new krcst work fixed PK.
     */
    public KrcstWorkFixedPK() {
    }

    /**
     * Instantiates a new krcst work fixed PK.
     *
     * @param wkpid the wkpid
     * @param closureId the closure id
     * @param cid the cid
     */
    public KrcstWorkFixedPK(String wkpid, int closureId, String cid) {
        this.wkpid = wkpid;
        this.closureId = closureId;
        this.cid = cid;
    }

    /**
     * Gets the wkpid.
     *
     * @return the wkpid
     */
    public String getWkpid() {
        return wkpid;
    }

    /**
     * Sets the wkpid.
     *
     * @param wkpid the new wkpid
     */
    public void setWkpid(String wkpid) {
        this.wkpid = wkpid;
    }

    /**
     * Gets the closure id.
     *
     * @return the closure id
     */
    public int getClosureId() {
        return closureId;
    }

    /**
     * Sets the closure id.
     *
     * @param closureId the new closure id
     */
    public void setClosureId(int closureId) {
        this.closureId = closureId;
    }
    
    /**
     * Gets the cid.
     *
     * @return the cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * Sets the cid.
     *
     * @param cid the new cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        hash += (int) closureId;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstWorkFixedPK)) {
            return false;
        }
        KrcstWorkFixedPK other = (KrcstWorkFixedPK) object;
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        if (this.closureId != other.closureId) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KrcstWorkFixedPK[ wkpid=" + wkpid + ", closureId=" + closureId + ", cid=" + cid + " ]";
    }
    
}
