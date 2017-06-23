/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employment.history;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * The Class KmnmtEmploymentHistPK.
 */
@Embeddable
public class KmnmtEmploymentHistPK implements Serializable {

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
    
    /** The emptcd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPTCD")
    private String emptcd;

    public KmnmtEmploymentHistPK() {
    }

    public KmnmtEmploymentHistPK(String histId, String sid, String emptcd) {
        this.histId = histId;
        this.sid = sid;
        this.emptcd = emptcd;
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

    public String getEmptcd() {
        return emptcd;
    }

    public void setEmptcd(String emptcd) {
        this.emptcd = emptcd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (histId != null ? histId.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (emptcd != null ? emptcd.hashCode() : 0);
        return hash;
    }

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KmnmtEmploymentHistPK)) {
			return false;
		}
		KmnmtEmploymentHistPK other = (KmnmtEmploymentHistPK) object;
		if ((this.histId == null && other.histId != null)
				|| (this.histId != null && !this.histId.equals(other.histId))) {
			return false;
		}
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if ((this.emptcd == null && other.emptcd != null)
				|| (this.emptcd != null && !this.emptcd.equals(other.emptcd))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KmnmtEmploymentHistPK[ histId=" + histId + ", sid=" + sid + ", emptcd="
				+ emptcd + " ]";
	}
    
}
