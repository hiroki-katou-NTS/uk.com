/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportQpdptPaydayPK.
 */
@Embeddable
@Setter
@Getter
public class ReportQpdptPaydayPK {

    /** The ccd. */
    @Basic(optional = false)
    @Column(name = "CCD")
    public String ccd;
    
    /** The pid. */
    @Basic(optional = false)
    @Column(name = "PID")
    public String pid;

    /**
     * Instantiates a new report qpdpt payday PK.
     */
    public ReportQpdptPaydayPK() {
    }

    /**
     * Instantiates a new report qpdpt payday PK.
     *
     * @param ccd the ccd
     * @param pid the pid
     */
    public ReportQpdptPaydayPK(String ccd, String pid) {
        this.ccd = ccd;
        this.pid = pid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccd != null ? ccd.hashCode() : 0);
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportQpdptPaydayPK)) {
            return false;
        }
        ReportQpdptPaydayPK other = (ReportQpdptPaydayPK) object;
        if ((this.ccd == null && other.ccd != null) || (this.ccd != null && !this.ccd.equals(other.ccd))) {
            return false;
        }
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.QpdptPaydayPK[ ccd=" + ccd + ", pid=" + pid + " ]";
    }
    
}
