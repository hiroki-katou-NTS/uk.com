/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace.history;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtWorkplaceHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtWorkplaceHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	

	/** The hist id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    private String histId;
    
    /** The sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The wpl id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WPL_ID")
    private String wplId;

    public KmnmtWorkplaceHistPK() {
    }

    public KmnmtWorkplaceHistPK(String histId, String sid, String wplId) {
        this.histId = histId;
        this.sid = sid;
        this.wplId = wplId;
    }

    public String getHistId() {
        return histId;
    }

    public void setHistId(String histId) {
        this.histId = histId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getWplId() {
        return wplId;
    }

    public void setWplId(String wplId) {
        this.wplId = wplId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (histId != null ? histId.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (wplId != null ? wplId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmnmtWorkplaceHistPK)) {
            return false;
        }
        KmnmtWorkplaceHistPK other = (KmnmtWorkplaceHistPK) object;
        if ((this.histId == null && other.histId != null) || (this.histId != null && !this.histId.equals(other.histId))) {
            return false;
        }
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if ((this.wplId == null && other.wplId != null) || (this.wplId != null && !this.wplId.equals(other.wplId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KmnmtEmpWorkplaceHistPK[ histId=" + histId + ", sid=" + sid + ", wplId=" + wplId + " ]";
    }
    
}
