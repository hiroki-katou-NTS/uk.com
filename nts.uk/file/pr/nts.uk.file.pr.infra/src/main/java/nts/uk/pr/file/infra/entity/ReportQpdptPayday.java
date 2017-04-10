/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportQpdptPayday.
 */
@Entity
@Setter
@Getter
@Table(name = "QPDPT_PAYDAY")
public class ReportQpdptPayday implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The qpdpt payday PK. */
    @EmbeddedId
    public ReportQpdptPaydayPK qpdptPaydayPK;
   
    /** The processing no. */
    @Basic(optional = false)
    @Column(name = "PROCESSING_NO")
    public int processingNo;

    /**
     * Instantiates a new report qpdpt payday.
     */
    public ReportQpdptPayday() {
    }

    /**
     * Instantiates a new report qpdpt payday.
     *
     * @param qpdptPaydayPK the qpdpt payday PK
     */
    public ReportQpdptPayday(ReportQpdptPaydayPK qpdptPaydayPK) {
        this.qpdptPaydayPK = qpdptPaydayPK;
    }

    /**
     * Instantiates a new report qpdpt payday.
     *
     * @param qpdptPaydayPK the qpdpt payday PK
     * @param processingNo the processing no
     */
    public ReportQpdptPayday(ReportQpdptPaydayPK qpdptPaydayPK, int processingNo) {
        this.qpdptPaydayPK = qpdptPaydayPK;
        this.processingNo = processingNo;
    }

    /**
     * Instantiates a new report qpdpt payday.
     *
     * @param ccd the ccd
     * @param pid the pid
     */
    public ReportQpdptPayday(String ccd, String pid) {
        this.qpdptPaydayPK = new ReportQpdptPaydayPK(ccd, pid);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qpdptPaydayPK != null ? qpdptPaydayPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportQpdptPayday)) {
            return false;
        }
        ReportQpdptPayday other = (ReportQpdptPayday) object;
        if ((this.qpdptPaydayPK == null && other.qpdptPaydayPK != null) 
        		|| (this.qpdptPaydayPK != null && !this.qpdptPaydayPK.equals(other.qpdptPaydayPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.QpdptPayday[ qpdptPaydayPK=" + qpdptPaydayPK + " ]";
    }
    
}
