/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.workfixed;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcstWorkFixedPK.
 */
@Getter
@Setter
@Embeddable
public class KrcstWorkFixedPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The wkpid. */
    @Column(name = "WKPID")
    private String wkpid;
	
    /** The closure id. */
    @Column(name = "CLOSURE_ID")
    private int closureId;
    
    /** The cid. */
    @Column(name = "CID")
    private String cid;

    /**
     * Instantiates a new krcst work fixed PK.
     */
    public KrcstWorkFixedPK() {
    	super();
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
    
}
