/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Getter
@Setter
@Entity
@Table(name = "CWPMT_WKP_HIERARCHY")
@XmlRootElement

public class CwpmtWkpHierarchy extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CwpmtWkpHierarchyPK cwpmtWkpHierarchyPK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIERARCHY_CD")
    private String hierarchyCd;

    public CwpmtWkpHierarchy() {
    }

    public CwpmtWkpHierarchy(CwpmtWkpHierarchyPK cwpmtWkpHierarchyPK) {
        this.cwpmtWkpHierarchyPK = cwpmtWkpHierarchyPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cwpmtWkpHierarchyPK != null ? cwpmtWkpHierarchyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CwpmtWkpHierarchy)) {
            return false;
        }
        CwpmtWkpHierarchy other = (CwpmtWkpHierarchy) object;
        if ((this.cwpmtWkpHierarchyPK == null && other.cwpmtWkpHierarchyPK != null) || (this.cwpmtWkpHierarchyPK != null && !this.cwpmtWkpHierarchyPK.equals(other.cwpmtWkpHierarchyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CwpmtWkpHierarchy[ cwpmtWkpHierarchyPK=" + cwpmtWkpHierarchyPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.cwpmtWkpHierarchyPK;
	}
    
}
