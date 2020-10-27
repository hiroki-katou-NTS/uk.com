/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.premium;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtHd60hPremiumRatePK.
 */

@Getter
@Setter
@Embeddable
public class KshmtHd60hPremiumRatePK implements Serializable {
    
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
    
    /** The over time no. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "OVER_TIME_NO")
    private int overTimeNo;

    /**
     * Instantiates a new kshst premium ext 60 h rate PK.
     */
    public KshmtHd60hPremiumRatePK() {
    }

    /**
     * Instantiates a new kshst premium ext 60 h rate PK.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     * @param overTimeNo the over time no
     */
    public KshmtHd60hPremiumRatePK(String cid, int brdItemNo, int overTimeNo) {
        this.cid = cid;
        this.brdItemNo = brdItemNo;
        this.overTimeNo = overTimeNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) brdItemNo;
        hash += (int) overTimeNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHd60hPremiumRatePK)) {
            return false;
        }
        KshmtHd60hPremiumRatePK other = (KshmtHd60hPremiumRatePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.brdItemNo != other.brdItemNo) {
            return false;
        }
        if (this.overTimeNo != other.overTimeNo) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtHd60hPremiumRatePK[ cid=" + cid + ", brdItemNo=" + brdItemNo + ", overTimeNo=" + overTimeNo + " ]";
    }
    
}
