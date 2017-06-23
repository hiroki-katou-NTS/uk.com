/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtAffiliWorkplaceHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliWorkplaceHistPK implements Serializable {
	
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

    /**
     * Instantiates a new kmnmt affili workplace hist PK.
     */
    public KmnmtAffiliWorkplaceHistPK() {
    }

    /**
     * Instantiates a new kmnmt affili workplace hist PK.
     *
     * @param histId the hist id
     * @param sid the sid
     * @param wplId the wpl id
     */
    public KmnmtAffiliWorkplaceHistPK(String histId, String sid, String wplId) {
        this.histId = histId;
        this.sid = sid;
        this.wplId = wplId;
    }

    /**
     * Gets the hist id.
     *
     * @return the hist id
     */
    public String getHistId() {
        return histId;
    }

    /**
     * Sets the hist id.
     *
     * @param histId the new hist id
     */
    public void setHistId(String histId) {
        this.histId = histId;
    }

    /**
     * Gets the sid.
     *
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * Sets the sid.
     *
     * @param sid the new sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * Gets the wpl id.
     *
     * @return the wpl id
     */
    public String getWplId() {
        return wplId;
    }

    /**
     * Sets the wpl id.
     *
     * @param wplId the new wpl id
     */
    public void setWplId(String wplId) {
        this.wplId = wplId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (histId != null ? histId.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (wplId != null ? wplId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmnmtAffiliWorkplaceHistPK)) {
            return false;
        }
        KmnmtAffiliWorkplaceHistPK other = (KmnmtAffiliWorkplaceHistPK) object;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KmnmtEmpWorkplaceHistPK[ histId=" + histId + ", sid=" + sid + ", wplId=" + wplId + " ]";
    }
    
}
