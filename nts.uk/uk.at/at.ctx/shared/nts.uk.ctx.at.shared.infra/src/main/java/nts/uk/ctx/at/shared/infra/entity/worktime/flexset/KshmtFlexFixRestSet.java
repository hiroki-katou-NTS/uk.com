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
 * The Class KshmtFlexFixRestSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_FIX_REST_SET")
public class KshmtFlexFixRestSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex fix rest set PK. */
    @EmbeddedId
    protected KshmtFlexFixRestSetPK kshmtFlexFixRestSetPK;
    
    /** The str time. */
    @Basic(optional = false)
    @Column(name = "STR_TIME")
    private int strTime;
    
    /** The end time. */
    @Basic(optional = false)
    @Column(name = "END_TIME")
    private int endTime;

    /**
     * Instantiates a new kshmt flex fix rest set.
     */
    public KshmtFlexFixRestSet() {
    }

    /**
     * Instantiates a new kshmt flex fix rest set.
     *
     * @param kshmtFlexFixRestSetPK the kshmt flex fix rest set PK
     */
    public KshmtFlexFixRestSet(KshmtFlexFixRestSetPK kshmtFlexFixRestSetPK) {
        this.kshmtFlexFixRestSetPK = kshmtFlexFixRestSetPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtFlexFixRestSetPK != null ? kshmtFlexFixRestSetPK.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtFlexFixRestSet)) {
			return false;
		}
		KshmtFlexFixRestSet other = (KshmtFlexFixRestSet) object;
		if ((this.kshmtFlexFixRestSetPK == null && other.kshmtFlexFixRestSetPK != null)
				|| (this.kshmtFlexFixRestSetPK != null
						&& !this.kshmtFlexFixRestSetPK.equals(other.kshmtFlexFixRestSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexFixRestSet[ kshmtFlexFixRestSetPK=" + kshmtFlexFixRestSetPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexFixRestSetPK;
	}
	
    
}
