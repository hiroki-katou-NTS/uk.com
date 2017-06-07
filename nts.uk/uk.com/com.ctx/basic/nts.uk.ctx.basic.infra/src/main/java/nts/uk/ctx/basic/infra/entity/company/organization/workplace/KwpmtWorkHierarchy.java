/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KwpmtWorkHierarchy.
 */
@Getter
@Setter
@Entity
@Table(name = "KWPMT_WORK_HIERARCHY")
@XmlRootElement
public class KwpmtWorkHierarchy extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KwpmtWorkHierarchyPK kwpmtWorkHierarchyPK;
    

    public KwpmtWorkHierarchy() {
    }

    public KwpmtWorkHierarchy(KwpmtWorkHierarchyPK kwpmtWorkHierarchyPK) {
        this.kwpmtWorkHierarchyPK = kwpmtWorkHierarchyPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kwpmtWorkHierarchyPK != null ? kwpmtWorkHierarchyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KwpmtWorkHierarchy)) {
            return false;
        }
        KwpmtWorkHierarchy other = (KwpmtWorkHierarchy) object;
        if ((this.kwpmtWorkHierarchyPK == null && other.kwpmtWorkHierarchyPK != null) || (this.kwpmtWorkHierarchyPK != null && !this.kwpmtWorkHierarchyPK.equals(other.kwpmtWorkHierarchyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KwpmtWorkHierarchy[ kwpmtWorkHierarchyPK=" + kwpmtWorkHierarchyPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.getKwpmtWorkHierarchyPK();
	}
    
}
