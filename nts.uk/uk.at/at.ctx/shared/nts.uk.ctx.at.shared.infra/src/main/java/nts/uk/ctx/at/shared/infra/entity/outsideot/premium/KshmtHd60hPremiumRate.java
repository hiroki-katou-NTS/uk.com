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
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtHd60hPremiumRate.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_HD60H_PREMIUM_RATE")
public class KshmtHd60hPremiumRate extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst premium ext 60 h rate PK. */
    @EmbeddedId
    protected KshstPremiumExt60hRatePK pk;
    
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
     * @param kshstPremiumExt60hRatePK the kshst premium ext 60 h rate PK
     */
    public KshmtHd60hPremiumRate(KshstPremiumExt60hRatePK kshstPremiumExt60hRatePK) {
        this.pk = kshstPremiumExt60hRatePK;
    }

    /**
     * Instantiates a new kshst premium ext 60 h rate.
     *
     * @param kshstPremiumExt60hRatePK the kshst premium ext 60 h rate PK
     * @param premiumRate the premium rate
     */
    public KshmtHd60hPremiumRate(KshstPremiumExt60hRatePK kshstPremiumExt60hRatePK, int premiumRate) {
        this.pk = kshstPremiumExt60hRatePK;
        this.premiumRate = premiumRate;
    }

    /**
     * Instantiates a new kshst premium ext 60 h rate.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     * @param overTimeNo the over time no
     */
    public KshmtHd60hPremiumRate(String cid, int brdItemNo, int overTimeNo, int rate) {
        this.pk = new KshstPremiumExt60hRatePK(cid, brdItemNo, overTimeNo);
        this.premiumRate = rate;
    }

    /**
     * Sets the kshst premium ext 60 h rate PK.
     *
     * @param kshstPremiumExt60hRatePK the new kshst premium ext 60 h rate PK
     */
    public void setKshstPremiumExt60hRatePK(KshstPremiumExt60hRatePK kshstPremiumExt60hRatePK) {
        this.pk = kshstPremiumExt60hRatePK;
    }
    
    public PremiumExtra60HRate domain() {
    	
    	return new PremiumExtra60HRate(new PremiumRate(this.premiumRate), 
    									EnumAdaptor.valueOf(this.pk.getOverTimeNo(), OvertimeNo.class));
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (pk != null ? pk.hashCode() : 0);
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
		if ((this.pk == null && other.pk != null)
				|| (this.pk != null
						&& !this.pk.equals(other.pk))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtHd60hPremiumRate[ kshstPremiumExt60hRatePK=" + pk
				+ " ]";
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
