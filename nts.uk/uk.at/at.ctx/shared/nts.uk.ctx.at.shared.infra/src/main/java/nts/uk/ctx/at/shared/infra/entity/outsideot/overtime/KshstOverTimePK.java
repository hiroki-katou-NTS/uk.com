/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOverTimePK.
 */
@Getter
@Setter
@Embeddable
public class KshstOverTimePK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The over time no. */
    @Column(name = "OVER_TIME_NO")
    private int overTimeNo;

    /**
     * Instantiates a new kshst over time PK.
     */
    public KshstOverTimePK() {
    	super();
    }

    /**
     * Instantiates a new kshst over time PK.
     *
     * @param cid the cid
     * @param overTimeNo the over time no
     */
    public KshstOverTimePK(String cid, short overTimeNo) {
        this.cid = cid;
        this.overTimeNo = overTimeNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) overTimeNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstOverTimePK)) {
			return false;
		}
		KshstOverTimePK other = (KshstOverTimePK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.overTimeNo != other.overTimeNo) {
			return false;
		}
		return true;
	}
    
}
