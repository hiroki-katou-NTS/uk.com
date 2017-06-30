/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.classification;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtAffiliClassificationHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliClassificationHistPK implements Serializable {
	
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
    
    /** The clscd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLSCD")
    private String clscd;

    /**
     * Instantiates a new kmnmt affili classification hist PK.
     */
    public KmnmtAffiliClassificationHistPK() {
    }

    /**
     * Instantiates a new kmnmt affili classification hist PK.
     *
     * @param histId the hist id
     * @param sid the sid
     * @param clscd the clscd
     */
    public KmnmtAffiliClassificationHistPK(String histId, String sid, String clscd) {
        this.histId = histId;
        this.sid = sid;
        this.clscd = clscd;
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
     * Gets the clscd.
     *
     * @return the clscd
     */
    public String getClscd() {
        return clscd;
    }

    /**
     * Sets the clscd.
     *
     * @param clscd the new clscd
     */
    public void setClscd(String clscd) {
        this.clscd = clscd;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (histId != null ? histId.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (clscd != null ? clscd.hashCode() : 0);
        return hash;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KmnmtAffiliClassificationHistPK)) {
			return false;
		}
		KmnmtAffiliClassificationHistPK other = (KmnmtAffiliClassificationHistPK) object;
		if ((this.histId == null && other.histId != null)
				|| (this.histId != null && !this.histId.equals(other.histId))) {
			return false;
		}
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if ((this.clscd == null && other.clscd != null)
				|| (this.clscd != null && !this.clscd.equals(other.clscd))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KmnmtClassificationHistPK[ histId=" + histId + ", sid=" + sid + ", clscd="
				+ clscd + " ]";
	}
    
}
