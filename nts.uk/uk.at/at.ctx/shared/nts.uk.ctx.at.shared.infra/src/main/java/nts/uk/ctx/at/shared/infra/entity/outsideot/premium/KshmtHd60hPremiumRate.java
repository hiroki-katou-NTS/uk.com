/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.premium;

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
 * The Class KshmtHd60hPremiumRate.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_HD60H_PREMIUM_RATE")
public class KshmtHd60hPremiumRate implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst premium ext 60 h rate PK. */
    @EmbeddedId
    protected KshmtHd60hPremiumRatePK kshmtHd60hPremiumRatePK;
    
    /** The premium rate. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREMIUM_RATE")
    private int premiumRate;

    /**
     * Instantiates a new kshst premium ext 60 h rate.
     */
    public KshmtHd60hPremiumRate() {
    }

    /**
     * Instantiates a new kshst premium ext 60 h rate.
     *
     * @param kshmtHd60hPremiumRatePK the kshst premium ext 60 h rate PK
     */
    public KshmtHd60hPremiumRate(KshmtHd60hPremiumRatePK kshmtHd60hPremiumRatePK) {
        this.kshmtHd60hPremiumRatePK = kshmtHd60hPremiumRatePK;
    }

    /**
     * Instantiates a new kshst premium ext 60 h rate.
     *
     * @param kshmtHd60hPremiumRatePK the kshst premium ext 60 h rate PK
     * @param premiumRate the premium rate
     */
    public KshmtHd60hPremiumRate(KshmtHd60hPremiumRatePK kshmtHd60hPremiumRatePK, short premiumRate) {
        this.kshmtHd60hPremiumRatePK = kshmtHd60hPremiumRatePK;
        this.premiumRate = premiumRate;
    }

    /**
     * Instantiates a new kshst premium ext 60 h rate.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     * @param overTimeNo the over time no
     */
    public KshmtHd60hPremiumRate(String cid, short brdItemNo, short overTimeNo) {
        this.kshmtHd60hPremiumRatePK = new KshmtHd60hPremiumRatePK(cid, brdItemNo, overTimeNo);
    }

    /**
     * Gets the kshst premium ext 60 h rate PK.
     *
     * @return the kshst premium ext 60 h rate PK
     */
    public KshmtHd60hPremiumRatePK getKshmtHd60hPremiumRatePK() {
        return kshmtHd60hPremiumRatePK;
    }

    /**
     * Sets the kshst premium ext 60 h rate PK.
     *
     * @param kshmtHd60hPremiumRatePK the new kshst premium ext 60 h rate PK
     */
    public void setKshmtHd60hPremiumRatePK(KshmtHd60hPremiumRatePK kshmtHd60hPremiumRatePK) {
        this.kshmtHd60hPremiumRatePK = kshmtHd60hPremiumRatePK;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtHd60hPremiumRatePK != null ? kshmtHd60hPremiumRatePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtHd60hPremiumRate)) {
			return false;
		}
		KshmtHd60hPremiumRate other = (KshmtHd60hPremiumRate) object;
		if ((this.kshmtHd60hPremiumRatePK == null && other.kshmtHd60hPremiumRatePK != null)
				|| (this.kshmtHd60hPremiumRatePK != null
						&& !this.kshmtHd60hPremiumRatePK.equals(other.kshmtHd60hPremiumRatePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtHd60hPremiumRate[ kshmtHd60hPremiumRatePK=" + kshmtHd60hPremiumRatePK
				+ " ]";
	}

}
