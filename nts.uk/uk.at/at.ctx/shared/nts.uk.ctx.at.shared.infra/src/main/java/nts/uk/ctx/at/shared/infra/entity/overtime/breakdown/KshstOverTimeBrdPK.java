/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.overtime.breakdown;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOverTimeBrdPK.
 */

@Getter
@Setter
@Embeddable
public class KshstOverTimeBrdPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The brd item no. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "BRD_ITEM_NO")
    private int brdItemNo;

    /**
     * Instantiates a new kshst over time brd PK.
     */
    public KshstOverTimeBrdPK() {
    }

    /**
     * Instantiates a new kshst over time brd PK.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     */
    public KshstOverTimeBrdPK(String cid, short brdItemNo) {
        this.cid = cid;
        this.brdItemNo = brdItemNo;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (int) brdItemNo;
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KshstOverTimeBrdPK)) {
			return false;
		}
		KshstOverTimeBrdPK other = (KshstOverTimeBrdPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.brdItemNo != other.brdItemNo) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshstOverTimeBrdPK[ cid=" + cid + ", brdItemNo=" + brdItemNo + " ]";
	}
    
}
