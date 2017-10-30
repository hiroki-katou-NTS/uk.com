/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.workfixed;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Embeddable
public class KrcstWorkFixedPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "WKPID")
    private String wkpid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLOSURE_ID")
    private int closureId;

    public KrcstWorkFixedPK() {
    }

    public KrcstWorkFixedPK(String wkpid, int closureId) {
        this.wkpid = wkpid;
        this.closureId = closureId;
    }

    public String getWkpid() {
        return wkpid;
    }

    public void setWkpid(String wkpid) {
        this.wkpid = wkpid;
    }

    public int getClosureId() {
        return closureId;
    }

    public void setClosureId(int closureId) {
        this.closureId = closureId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        hash += (int) closureId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstWorkFixedPK)) {
            return false;
        }
        KrcstWorkFixedPK other = (KrcstWorkFixedPK) object;
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        if (this.closureId != other.closureId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KrcstWorkFixedPK[ wkpid=" + wkpid + ", closureId=" + closureId + " ]";
    }
    
}
