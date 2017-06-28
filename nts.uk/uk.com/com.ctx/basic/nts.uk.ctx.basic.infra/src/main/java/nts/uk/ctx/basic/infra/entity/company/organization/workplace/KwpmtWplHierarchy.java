/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "KWPMT_WPL_HIERARCHY")
@XmlRootElement

public class KwpmtWplHierarchy extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KwpmtWplHierarchyPK kwpmtWplHierarchyPK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIERARCHY_CD")
    private String hierarchyCd;

    public KwpmtWplHierarchy() {
    }

    public KwpmtWplHierarchy(KwpmtWplHierarchyPK kwpmtWplHierarchyPK) {
        this.kwpmtWplHierarchyPK = kwpmtWplHierarchyPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kwpmtWplHierarchyPK != null ? kwpmtWplHierarchyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KwpmtWplHierarchy)) {
            return false;
        }
        KwpmtWplHierarchy other = (KwpmtWplHierarchy) object;
        if ((this.kwpmtWplHierarchyPK == null && other.kwpmtWplHierarchyPK != null) || (this.kwpmtWplHierarchyPK != null && !this.kwpmtWplHierarchyPK.equals(other.kwpmtWplHierarchyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KwpmtWplHierarchy[ kwpmtWplHierarchyPK=" + kwpmtWplHierarchyPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.kwpmtWplHierarchyPK;
	}
    
}
