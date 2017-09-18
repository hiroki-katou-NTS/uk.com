/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtWkpConfigInfo.
 */
@Entity
@Table(name = "BSYMT_WKP_CONFIG_INFO")

public class BsymtWkpConfigInfo extends UkJpaEntity implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
	/** The bsymt wkp config info PK. */
	@EmbeddedId
    protected BsymtWkpConfigInfoPK bsymtWkpConfigInfoPK;
    
    /** The wkpid. */
    @Column(name = "WKPID")
    private String wkpid;
    
    /** The hierarchy cd. */
    @Column(name = "HIERARCHY_CD")
    private String hierarchyCd;

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsymtWkpConfigInfoPK != null ? bsymtWkpConfigInfoPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsymtWkpConfigInfo)) {
            return false;
        }
        BsymtWkpConfigInfo other = (BsymtWkpConfigInfo) object;
        if ((this.bsymtWkpConfigInfoPK == null && other.bsymtWkpConfigInfoPK != null) || (this.bsymtWkpConfigInfoPK != null && !this.bsymtWkpConfigInfoPK.equals(other.bsymtWkpConfigInfoPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.BsymtWkpConfigInfo[ bsymtWkpConfigInfoPK=" + bsymtWkpConfigInfoPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {	
		return this.bsymtWkpConfigInfoPK;
	}
    
}
