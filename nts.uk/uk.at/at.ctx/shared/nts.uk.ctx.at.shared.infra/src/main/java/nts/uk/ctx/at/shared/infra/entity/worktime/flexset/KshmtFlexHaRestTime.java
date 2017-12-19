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
 * The Class KshmtFlexHaRestTime.
 */

@Setter
@Getter
@Entity
@Table(name = "KSHMT_FLEX_HA_REST_TIME")
public class KshmtFlexHaRestTime extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex ha rest time PK. */
    @EmbeddedId
    protected KshmtFlexHaRestTimePK kshmtFlexHaRestTimePK;
    
    /** The fix rest time. */
    @Basic(optional = false)
    @Column(name = "FIX_REST_TIME")
    private int fixRestTime;
    
    /** The use rest after set. */
    @Basic(optional = false)
    @Column(name = "USE_REST_AFTER_SET")
    private int useRestAfterSet;
    
    /** The after rest time. */
    @Basic(optional = false)
    @Column(name = "AFTER_REST_TIME")
    private int afterRestTime;
    
    /** The after passage time. */
    @Basic(optional = false)
    @Column(name = "AFTER_PASSAGE_TIME")
    private int afterPassageTime;

    /**
     * Instantiates a new kshmt flex ha rest time.
     */
    public KshmtFlexHaRestTime() {
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtFlexHaRestTimePK != null ? kshmtFlexHaRestTimePK.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexHaRestTime)) {
			return false;
		}
		KshmtFlexHaRestTime other = (KshmtFlexHaRestTime) object;
		if ((this.kshmtFlexHaRestTimePK == null && other.kshmtFlexHaRestTimePK != null)
				|| (this.kshmtFlexHaRestTimePK != null
						&& !this.kshmtFlexHaRestTimePK.equals(other.kshmtFlexHaRestTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexHaRestTime[ kshmtFlexHaRestTimePK=" + kshmtFlexHaRestTimePK + " ]";
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexHaRestTimePK;
	}
	
	
    
}
