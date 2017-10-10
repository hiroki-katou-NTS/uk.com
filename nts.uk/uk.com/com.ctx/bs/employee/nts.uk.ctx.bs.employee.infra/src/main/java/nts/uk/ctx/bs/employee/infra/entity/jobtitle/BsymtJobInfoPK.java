/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.bs.employee.infra.entity.jobtitle;

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
public class BsymtJobInfoPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "HIST_ID")
    private String histId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "JOB_ID")
    private String jobId;

    public BsymtJobInfoPK() {
    }

    public BsymtJobInfoPK(String cid, String histId, String jobId) {
        this.cid = cid;
        this.histId = histId;
        this.jobId = jobId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getHistId() {
        return histId;
    }

    public void setHistId(String histId) {
        this.histId = histId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (histId != null ? histId.hashCode() : 0);
        hash += (jobId != null ? jobId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BsymtJobInfoPK)) {
            return false;
        }
        BsymtJobInfoPK other = (BsymtJobInfoPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.histId == null && other.histId != null) || (this.histId != null && !this.histId.equals(other.histId))) {
            return false;
        }
        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsymtJobInfoPK[ cid=" + cid + ", histId=" + histId + ", jobId=" + jobId + " ]";
    }
    
}
