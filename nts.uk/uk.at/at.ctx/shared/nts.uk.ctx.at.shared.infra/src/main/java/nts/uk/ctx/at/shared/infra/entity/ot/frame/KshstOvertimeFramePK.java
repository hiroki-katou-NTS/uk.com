/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.frame;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOvertimeFramePK.
 */
@Getter
@Setter
@Embeddable
public class KshstOvertimeFramePK implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5716312849409233872L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;

    /** The ot fr no. */
    @Column(name = "OT_FR_NO")
    private short otFrNo;

    /**
     * Instantiates a new kshst overtime frame PK.
     */
    public KshstOvertimeFramePK() {
    	super();
    }

    /**
     * Instantiates a new kshst overtime frame PK.
     *
     * @param cid the cid
     * @param otFrNo the ot fr no
     */
    public KshstOvertimeFramePK(String cid, short otFrNo) {
        this.cid = cid;
        this.otFrNo = otFrNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) otFrNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshstOvertimeFramePK)) {
            return false;
        }
        KshstOvertimeFramePK other = (KshstOvertimeFramePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.otFrNo != other.otFrNo) {
            return false;
        }
        return true;
    }
    
}
