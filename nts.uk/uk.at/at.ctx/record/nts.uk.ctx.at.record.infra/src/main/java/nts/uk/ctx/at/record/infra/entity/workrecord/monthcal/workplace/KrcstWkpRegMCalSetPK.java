/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace;

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
public class KrcstWkpRegMCalSetPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "WKPID")
    private String wkpid;

    public KrcstWkpRegMCalSetPK() {
    }

    public KrcstWkpRegMCalSetPK(String cid, String wkpid) {
        this.cid = cid;
        this.wkpid = wkpid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstWkpRegMCalSetPK)) {
            return false;
        }
        KrcstWkpRegMCalSetPK other = (KrcstWkpRegMCalSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstWkpRegMCalSetPK[ cid=" + cid + ", wkpid=" + wkpid + " ]";
    }
    
}
