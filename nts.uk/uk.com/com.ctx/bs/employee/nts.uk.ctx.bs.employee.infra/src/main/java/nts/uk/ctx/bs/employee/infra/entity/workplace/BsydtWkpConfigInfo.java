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

@Entity
@Table(name = "BSYDT_WKP_CONFIG_INFO")

public class BsydtWkpConfigInfo extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@EmbeddedId
    protected BsydtWkpConfigInfoPK bsydtWkpConfigInfoPK;
    
    @Column(name = "WKPID")
    private String wkpid;
    
    @Column(name = "HIERARCHY_CD")
    private String hierarchyCd;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsydtWkpConfigInfoPK != null ? bsydtWkpConfigInfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsydtWkpConfigInfo)) {
            return false;
        }
        BsydtWkpConfigInfo other = (BsydtWkpConfigInfo) object;
        if ((this.bsydtWkpConfigInfoPK == null && other.bsydtWkpConfigInfoPK != null) || (this.bsydtWkpConfigInfoPK != null && !this.bsydtWkpConfigInfoPK.equals(other.bsydtWkpConfigInfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsydtWkpConfigInfo[ bsydtWkpConfigInfoPK=" + bsydtWkpConfigInfoPK + " ]";
    }

	@Override
	protected Object getKey() {	
		return this.bsydtWkpConfigInfoPK;
	}
    
}
