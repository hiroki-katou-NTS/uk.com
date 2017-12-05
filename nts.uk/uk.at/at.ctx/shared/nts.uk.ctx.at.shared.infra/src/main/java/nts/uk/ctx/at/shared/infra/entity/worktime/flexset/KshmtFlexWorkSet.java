/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtFlexWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_WORK_SET")
public class KshmtFlexWorkSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex work set PK. */
    @EmbeddedId
    protected KshmtFlexWorkSetPK kshmtFlexWorkSetPK;
    
    /** The core time str. */
    @Basic(optional = false)
    @Column(name = "CORE_TIME_STR")
    private int coreTimeStr;
    
    /** The core time end. */
    @Basic(optional = false)
    @Column(name = "CORE_TIME_END")
    private int coreTimeEnd;
    
    /** The use atr. */
    @Basic(optional = false)
    @Column(name = "USE_ATR")
    private int useAtr;
    
    /** The least work time. */
    @Basic(optional = false)
    @Column(name = "LEAST_WORK_TIME")
    private int leastWorkTime;
    
    /** The deduct from work time. */
    @Basic(optional = false)
    @Column(name = "DEDUCT_FROM_WORK_TIME")
    private int deductFromWorkTime;
    
    /** The especial calc. */
    @Basic(optional = false)
    @Column(name = "ESPECIAL_CALC")
    private int especialCalc;

    /**
     * Instantiates a new kshmt flex work set.
     */
    public KshmtFlexWorkSet() {
    }

    /**
     * Instantiates a new kshmt flex work set.
     *
     * @param kshmtFlexWorkSetPK the kshmt flex work set PK
     */
    public KshmtFlexWorkSet(KshmtFlexWorkSetPK kshmtFlexWorkSetPK) {
        this.kshmtFlexWorkSetPK = kshmtFlexWorkSetPK;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexWorkSetPK != null ? kshmtFlexWorkSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexWorkSet)) {
			return false;
		}
		KshmtFlexWorkSet other = (KshmtFlexWorkSet) object;
		if ((this.kshmtFlexWorkSetPK == null && other.kshmtFlexWorkSetPK != null)
				|| (this.kshmtFlexWorkSetPK != null && !this.kshmtFlexWorkSetPK.equals(other.kshmtFlexWorkSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexWorkSet[ kshmtFlexWorkSetPK=" + kshmtFlexWorkSetPK + " ]";
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexWorkSetPK;
	}
    
}
