/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtAutoUseUnitSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_AUTO_USE_UNIT_SET")
public class KshmtAutoUseUnitSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Column(name = "CID")
    private String cid;
    
    /** The job cal set. */
    @Column(name = "JOB_CAL_SET")
    private short jobCalSet;
    
    /** The wkp cal set. */
    @Column(name = "WKP_CAL_SET")
    private short wkpCalSet;
    
    /** The wkp job cal set. */
    @Column(name = "WKP_JOB_CAL_SET")
    private short wkpJobCalSet;

    /**
     * Instantiates a new kshmt auto use unit set.
     */
    public KshmtAutoUseUnitSet() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto use unit set.
     *
     * @param cid the cid
     */
    public KshmtAutoUseUnitSet(String cid) {
        this.cid = cid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtAutoUseUnitSet)) {
            return false;
        }
        KshmtAutoUseUnitSet other = (KshmtAutoUseUnitSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}

}
