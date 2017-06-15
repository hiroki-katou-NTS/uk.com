/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class KwpmtWorkplacePK implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "WPL_ID")
    private String wplid;

    public KwpmtWorkplacePK() {
    }

    public KwpmtWorkplacePK(String cid, String wplid) {
        this.cid = cid;
        this.wplid = wplid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (wplid != null ? wplid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KwpmtWorkplacePK)) {
            return false;
        }
        KwpmtWorkplacePK other = (KwpmtWorkplacePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wplid == null && other.wplid != null) || (this.wplid != null && !this.wplid.equals(other.wplid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KwpmtWorkplacePK[ cid=" + cid + ", wkpid=" + wplid + " ]";
    }
    
}
