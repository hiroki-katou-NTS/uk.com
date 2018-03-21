/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime;

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
public class KshstWkpRegLaborTimePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "WKP_ID")
    private String wkpId;

    public KshstWkpRegLaborTimePK() {
    }

    public KshstWkpRegLaborTimePK(String cid, String wkpId) {
        this.cid = cid;
        this.wkpId = wkpId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getWkpId() {
        return wkpId;
    }

    public void setWkpId(String wkpId) {
        this.wkpId = wkpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (wkpId != null ? wkpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstWkpRegLaborTimePK)) {
            return false;
        }
        KshstWkpRegLaborTimePK other = (KshstWkpRegLaborTimePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wkpId == null && other.wkpId != null) || (this.wkpId != null && !this.wkpId.equals(other.wkpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstWkpRegLaborTimePK[ cid=" + cid + ", wkpId=" + wkpId + " ]";
    }
    
}
