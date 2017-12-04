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
 * The Class KshmtFlexOtTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_OT_TIME_SET")
public class KshmtFlexOtTimeSet  extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex ot time set PK. */
    @EmbeddedId
    protected KshmtFlexOtTimeSetPK kshmtFlexOtTimeSetPK;
    
    /** The treat time work. */
    @Basic(optional = false)
    @Column(name = "TREAT_TIME_WORK")
    private int treatTimeWork;
    
    /** The treat early ot work. */
    @Basic(optional = false)
    @Column(name = "TREAT_EARLY_OT_WORK")
    private int treatEarlyOtWork;
    
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
    
    /** The ot frame no. */
    @Basic(optional = false)
    @Column(name = "OT_FRAME_NO")
    private BigDecimal otFrameNo;
    
    /** The legal ot frame no. */
    @Column(name = "LEGAL_OT_FRAME_NO")
    private BigDecimal legalOtFrameNo;
    
    /** The payoff order. */
    @Column(name = "PAYOFF_ORDER")
    private int payoffOrder;

    /**
     * Instantiates a new kshmt flex ot time set.
     */
    public KshmtFlexOtTimeSet() {
    }

    /**
     * Instantiates a new kshmt flex ot time set.
     *
     * @param kshmtFlexOtTimeSetPK the kshmt flex ot time set PK
     */
    public KshmtFlexOtTimeSet(KshmtFlexOtTimeSetPK kshmtFlexOtTimeSetPK) {
        this.kshmtFlexOtTimeSetPK = kshmtFlexOtTimeSetPK;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtFlexOtTimeSetPK != null ? kshmtFlexOtTimeSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtFlexOtTimeSet)) {
			return false;
		}
		KshmtFlexOtTimeSet other = (KshmtFlexOtTimeSet) object;
		if ((this.kshmtFlexOtTimeSetPK == null && other.kshmtFlexOtTimeSetPK != null)
				|| (this.kshmtFlexOtTimeSetPK != null
						&& !this.kshmtFlexOtTimeSetPK.equals(other.kshmtFlexOtTimeSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexOtTimeSet[ kshmtFlexOtTimeSetPK=" + kshmtFlexOtTimeSetPK + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexOtTimeSetPK;
	}
	
    
}
