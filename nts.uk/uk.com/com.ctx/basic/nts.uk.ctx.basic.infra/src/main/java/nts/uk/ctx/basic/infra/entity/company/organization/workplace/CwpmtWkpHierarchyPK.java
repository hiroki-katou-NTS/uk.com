/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CwpmtWkpHierarchyPK.
 */
@Getter
@Setter
@Embeddable
public class CwpmtWkpHierarchyPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
	
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKPID")
    private String wkpid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    private String hisId;

    public CwpmtWkpHierarchyPK() {
    }

    public CwpmtWkpHierarchyPK(String cid, String wkpid, String hisId) {
        this.cid = cid;
        this.wkpid = wkpid;
        this.hisId = hisId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getWkpid() {
        return wkpid;
    }

    public void setWkpid(String wkpid) {
        this.wkpid = wkpid;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        hash += (hisId != null ? hisId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CwpmtWkpHierarchyPK)) {
            return false;
        }
        CwpmtWkpHierarchyPK other = (CwpmtWkpHierarchyPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        if ((this.hisId == null && other.hisId != null) || (this.hisId != null && !this.hisId.equals(other.hisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CwpmtWkpHierarchyPK[ cid=" + cid + ", wkpid=" + wkpid + ", hisId=" + hisId + " ]";
    }
    
}
