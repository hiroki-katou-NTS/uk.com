/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.use;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcmtCalcSetUnitSet.
 */

/**
 * Sets the wkp job cal set.
 *
 * @param wkpJobCalSet the new wkp job cal set
 */
@Setter

/**
 * Gets the wkp job cal set.
 *
 * @return the wkp job cal set
 */
@Getter
@Entity
@Table(name = "KRCMT_CALC_SET_UNIT_SET")
public class KrcmtCalcSetUnitSet extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Column(name = "CID")
    private String cid;

    /** The job cal set. */
    @Column(name = "JOB_CAL_SET")
    private int jobCalSet;

    /** The wkp cal set. */
    @Column(name = "WKP_CAL_SET")
    private int wkpCalSet;

    /** The wkp job cal set. */
    @Column(name = "WKP_JOB_CAL_SET")
    private int wkpJobCalSet;

    /**
     * Instantiates a new kshmt auto use unit set.
     */
    public KrcmtCalcSetUnitSet() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto use unit set.
     *
     * @param cid the cid
     */
    public KrcmtCalcSetUnitSet(String cid) {
        this.cid = cid;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcmtCalcSetUnitSet)) {
            return false;
        }
        KrcmtCalcSetUnitSet other = (KrcmtCalcSetUnitSet) object;
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
