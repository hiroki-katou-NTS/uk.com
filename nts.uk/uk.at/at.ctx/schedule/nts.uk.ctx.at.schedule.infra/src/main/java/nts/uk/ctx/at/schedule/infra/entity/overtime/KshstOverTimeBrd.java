/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.overtime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOverTimeBrd.
 */

@Getter
@Setter
@Entity
@Table(name = "KSHST_OVER_TIME_BRD")
public class KshstOverTimeBrd implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time brd PK. */
    @EmbeddedId
    protected KshstOverTimeBrdPK kshstOverTimeBrdPK;
    
    /** The name. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NAME")
    private String name;
    
    /** The use atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "USE_ATR")
    private short useAtr;
    
    /** The product number. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_NUMBER")
    private short productNumber;

    /**
     * Instantiates a new kshst over time brd.
     */
    public KshstOverTimeBrd() {
    }

    /**
     * Instantiates a new kshst over time brd.
     *
     * @param kshstOverTimeBrdPK the kshst over time brd PK
     */
    public KshstOverTimeBrd(KshstOverTimeBrdPK kshstOverTimeBrdPK) {
        this.kshstOverTimeBrdPK = kshstOverTimeBrdPK;
    }

    /**
     * Instantiates a new kshst over time brd.
     *
     * @param kshstOverTimeBrdPK the kshst over time brd PK
     * @param exclusVer the exclus ver
     * @param name the name
     * @param useAtr the use atr
     * @param productNumber the product number
     */
	public KshstOverTimeBrd(KshstOverTimeBrdPK kshstOverTimeBrdPK, int exclusVer, String name,
			short useAtr, short productNumber) {
		this.kshstOverTimeBrdPK = kshstOverTimeBrdPK;
		this.name = name;
		this.useAtr = useAtr;
		this.productNumber = productNumber;
	}

    /**
     * Instantiates a new kshst over time brd.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     */
    public KshstOverTimeBrd(String cid, short brdItemNo) {
        this.kshstOverTimeBrdPK = new KshstOverTimeBrdPK(cid, brdItemNo);
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstOverTimeBrdPK != null ? kshstOverTimeBrdPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstOverTimeBrd)) {
			return false;
		}
		KshstOverTimeBrd other = (KshstOverTimeBrd) object;
		if ((this.kshstOverTimeBrdPK == null && other.kshstOverTimeBrdPK != null)
				|| (this.kshstOverTimeBrdPK != null
						&& !this.kshstOverTimeBrdPK.equals(other.kshstOverTimeBrdPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshstOverTimeBrd[ kshstOverTimeBrdPK=" + kshstOverTimeBrdPK + " ]";
    }
    
}
