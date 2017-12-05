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
 * The Class KshmtFlexWorkTimeSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_FLEX_WORK_TIME_SET")
public class KshmtFlexWorkTimeSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex work time set PK. */
    @EmbeddedId
    protected KshmtFlexWorkTimeSetPK kshmtFlexWorkTimeSetPK;
        
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

    /**
     * Instantiates a new kshmt flex work time set.
     */
    public KshmtFlexWorkTimeSet() {
    }

    /**
     * Instantiates a new kshmt flex work time set.
     *
     * @param kshmtFlexWorkTimeSetPK the kshmt flex work time set PK
     */
    public KshmtFlexWorkTimeSet(KshmtFlexWorkTimeSetPK kshmtFlexWorkTimeSetPK) {
        this.kshmtFlexWorkTimeSetPK = kshmtFlexWorkTimeSetPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexWorkTimeSetPK != null ? kshmtFlexWorkTimeSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexWorkTimeSet)) {
			return false;
		}
		KshmtFlexWorkTimeSet other = (KshmtFlexWorkTimeSet) object;
		if ((this.kshmtFlexWorkTimeSetPK == null && other.kshmtFlexWorkTimeSetPK != null)
				|| (this.kshmtFlexWorkTimeSetPK != null
						&& !this.kshmtFlexWorkTimeSetPK.equals(other.kshmtFlexWorkTimeSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexWorkTimeSet[ kshmtFlexWorkTimeSetPK=" + kshmtFlexWorkTimeSetPK + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexWorkTimeSetPK;
	}
	

}
