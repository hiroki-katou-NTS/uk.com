/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KscmtEstPriceSya.
 */

/**
 * Gets the est condition 5 th mny.
 *
 * @return the est condition 5 th mny
 */
@Getter

/**
 * Sets the est condition 5 th mny.
 *
 * @param estCondition5thMny the new est condition 5 th mny
 */
@Setter
@Entity
@Table(name = "KSCMT_EST_PRICE_SYA")
public class KscmtEstPriceSya extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est price per set PK. */
    @EmbeddedId
    protected KscmtEstPricePerSetPK kscmtEstPricePerSetPK;
    
    /** The est condition 1 st mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_1ST_MNY")
    private int estCondition1stMny;
    
    /** The est condition 2 nd mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_MNY")
    private int estCondition2ndMny;
    
    /** The est condition 3 rd mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_MNY")
    private int estCondition3rdMny;
    
    /** The est condition 4 th mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_MNY")
    private int estCondition4thMny;
    
    /** The est condition 5 th mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_MNY")
    private int estCondition5thMny;

    /**
     * Instantiates a new kscmt est price per set.
     */
    public KscmtEstPriceSya() {
    }

    /**
     * Instantiates a new kscmt est price per set.
     *
     * @param kscmtEstPricePerSetPK the kscmt est price per set PK
     */
    public KscmtEstPriceSya(KscmtEstPricePerSetPK kscmtEstPricePerSetPK) {
        this.kscmtEstPricePerSetPK = kscmtEstPricePerSetPK;
    }

    /**
     * Instantiates a new kscmt est price per set.
     *
     * @param sid the sid
     * @param targetYear the target year
     * @param targetCls the target cls
     */
    public KscmtEstPriceSya(String sid, short targetYear, short targetCls) {
        this.kscmtEstPricePerSetPK = new KscmtEstPricePerSetPK(sid, targetYear, targetCls);
    }

    /**
     * Gets the kscmt est price per set PK.
     *
     * @return the kscmt est price per set PK
     */
    public KscmtEstPricePerSetPK getKscmtEstPricePerSetPK() {
        return kscmtEstPricePerSetPK;
    }

    /**
     * Sets the kscmt est price per set PK.
     *
     * @param kscmtEstPricePerSetPK the new kscmt est price per set PK
     */
    public void setKscmtEstPricePerSetPK(KscmtEstPricePerSetPK kscmtEstPricePerSetPK) {
        this.kscmtEstPricePerSetPK = kscmtEstPricePerSetPK;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtEstPricePerSetPK != null ? kscmtEstPricePerSetPK.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KscmtEstPriceSya)) {
			return false;
		}
		KscmtEstPriceSya other = (KscmtEstPriceSya) object;
		if ((this.kscmtEstPricePerSetPK == null && other.kscmtEstPricePerSetPK != null)
				|| (this.kscmtEstPricePerSetPK != null
						&& !this.kscmtEstPricePerSetPK.equals(other.kscmtEstPricePerSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KscmtEstPriceSya[ kscmtEstPricePerSetPK=" + kscmtEstPricePerSetPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstPricePerSetPK;
	}
    
}
