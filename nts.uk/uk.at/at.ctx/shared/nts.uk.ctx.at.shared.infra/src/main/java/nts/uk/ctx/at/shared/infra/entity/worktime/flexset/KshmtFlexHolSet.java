/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtFlexHolSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_HOL_SET")
public class KshmtFlexHolSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex hol set PK. */
    @EmbeddedId
    protected KshmtFlexHolSetPK kshmtFlexHolSetPK;
    
    /** The unit. */
    @Basic(optional = false)
    @Column(name = "UNIT")
    private int unit;
    
    /** The rounding. */
    @Basic(optional = false)
    @Column(name = "ROUNDING")
    private int rounding;
    
    /** The time str. */
    @Basic(optional = false)
    @Column(name = "TIME_STR")
    private int timeStr;
    
    /** The time end. */
    @Basic(optional = false)
    @Column(name = "TIME_END")
    private int timeEnd;
    
    /** The hol time. */
    @Basic(optional = false)
    @Column(name = "HOL_TIME")
    private int holTime;
    
    /** The hol frame no. */
    @Basic(optional = false)
    @Column(name = "HOL_FRAME_NO")
    private BigDecimal holFrameNo;
    
    /** The out hol time. */
    @Basic(optional = false)
    @Column(name = "OUT_HOL_TIME")
    private int outHolTime;
    
    /** The out hol frame no. */
    @Basic(optional = false)
    @Column(name = "OUT_HOL_FRAME_NO")
    private BigDecimal outHolFrameNo;
    
    /** The pub hol time. */
    @Basic(optional = false)
    @Column(name = "PUB_HOL_TIME")
    private int pubHolTime;
    
    /** The pub hol frame no. */
    @Basic(optional = false)
    @Column(name = "PUB_HOL_FRAME_NO")
    private BigDecimal pubHolFrameNo;

    /**
     * Instantiates a new kshmt flex hol set.
     */
    public KshmtFlexHolSet() {
    }

    /**
     * Instantiates a new kshmt flex hol set.
     *
     * @param kshmtFlexHolSetPK the kshmt flex hol set PK
     */
    public KshmtFlexHolSet(KshmtFlexHolSetPK kshmtFlexHolSetPK) {
        this.kshmtFlexHolSetPK = kshmtFlexHolSetPK;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexHolSetPK != null ? kshmtFlexHolSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexHolSet)) {
			return false;
		}
		KshmtFlexHolSet other = (KshmtFlexHolSet) object;
		if ((this.kshmtFlexHolSetPK == null && other.kshmtFlexHolSetPK != null)
				|| (this.kshmtFlexHolSetPK != null && !this.kshmtFlexHolSetPK.equals(other.kshmtFlexHolSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexHolSet[ kshmtFlexHolSetPK=" + kshmtFlexHolSetPK + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexHolSetPK;
	}
	
    
}
