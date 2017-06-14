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
 * The Class KwpmtWorkHistPK.
 */
@Getter
@Setter
@Embeddable
public class KwpmtWorkHistPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The ccid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CCID")
    private String ccid;
    
    /** The hist id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    private String histId;

    /**
     * Instantiates a new kwpmt work hist PK.
     */
    public KwpmtWorkHistPK() {
    }

    /**
     * Instantiates a new kwpmt work hist PK.
     *
     * @param ccid the ccid
     * @param histId the hist id
     */
    public KwpmtWorkHistPK(String ccid, String histId) {
        this.ccid = ccid;
        this.histId = histId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccid != null ? ccid.hashCode() : 0);
        hash += (histId != null ? histId.hashCode() : 0);
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
		if (!(object instanceof KwpmtWorkHistPK)) {
			return false;
		}
		KwpmtWorkHistPK other = (KwpmtWorkHistPK) object;
		if ((this.ccid == null && other.ccid != null)
			|| (this.ccid != null && !this.ccid.equals(other.ccid))) {
			return false;
		}
		if ((this.histId == null && other.histId != null)
			|| (this.histId != null && !this.histId.equals(other.histId))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KwpmtWorkHistPK[ ccid=" + ccid + ", histId=" + histId + " ]";
    }
    
}
