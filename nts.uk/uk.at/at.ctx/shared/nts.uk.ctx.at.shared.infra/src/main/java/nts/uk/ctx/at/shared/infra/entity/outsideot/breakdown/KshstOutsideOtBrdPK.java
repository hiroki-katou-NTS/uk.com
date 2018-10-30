/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOutsideOtBrdPK.
 */

@Getter
@Setter
@Embeddable
public class KshstOutsideOtBrdPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The brd item no. */
    @Column(name = "BRD_ITEM_NO")
    private int brdItemNo;

    /**
     * Instantiates a new kshst over time brd PK.
     */
    public KshstOutsideOtBrdPK() {
    	super();
    }

    /**
     * Instantiates a new kshst over time brd PK.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     */
    public KshstOutsideOtBrdPK(String cid, short brdItemNo) {
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
		if (!(object instanceof KshstOutsideOtBrdPK)) {
			return false;
		}
		KshstOutsideOtBrdPK other = (KshstOutsideOtBrdPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.brdItemNo != other.brdItemNo) {
			return false;
		}
		return true;
	}

}
