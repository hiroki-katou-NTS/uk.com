/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.report;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PadmtPersonResiaddPK.
 */
@Getter
@Setter
@Embeddable
public class PadmtPersonResiaddPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "PID")
    private String pid;
    
    /** The hist id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    private String histId;

    /**
     * Instantiates a new padmt person resiadd PK.
     */
    public PadmtPersonResiaddPK() {
    }

    /**
     * Instantiates a new padmt person resiadd PK.
     *
     * @param pid the pid
     * @param histId the hist id
     */
    public PadmtPersonResiaddPK(String pid, String histId) {
        this.pid = pid;
        this.histId = histId;
    }

    /**
     * Gets the pid.
     *
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * Sets the pid.
     *
     * @param pid the new pid
     */
    public void setPid(String pid) {
        this.pid = pid;
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        hash += (histId != null ? histId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PadmtPersonResiaddPK)) {
            return false;
        }
		PadmtPersonResiaddPK other = (PadmtPersonResiaddPK) object;
		if ((this.pid == null && other.pid != null)
			|| (this.pid != null && !this.pid.equals(other.pid))) {
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
        return "entity.PadmtPersonResiaddPK[ pid=" + pid + ", histId=" + histId + " ]";
    }
    
}