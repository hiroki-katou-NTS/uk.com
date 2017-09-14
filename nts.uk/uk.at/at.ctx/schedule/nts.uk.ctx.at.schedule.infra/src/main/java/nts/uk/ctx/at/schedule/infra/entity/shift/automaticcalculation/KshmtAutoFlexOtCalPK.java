package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

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
public class KshmtAutoFlexOtCalPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKPID")
    private short wkpid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOBID")
    private short jobid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTO_CAL_ATR")
    private short autoCalAtr;

    public KshmtAutoFlexOtCalPK() {
    }

    public KshmtAutoFlexOtCalPK(String cid, short wkpid, short jobid, short autoCalAtr) {
        this.cid = cid;
        this.wkpid = wkpid;
        this.jobid = jobid;
        this.autoCalAtr = autoCalAtr;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public short getWkpid() {
        return wkpid;
    }

    public void setWkpid(short wkpid) {
        this.wkpid = wkpid;
    }

    public short getJobid() {
        return jobid;
    }

    public void setJobid(short jobid) {
        this.jobid = jobid;
    }

    public short getAutoCalAtr() {
        return autoCalAtr;
    }

    public void setAutoCalAtr(short autoCalAtr) {
        this.autoCalAtr = autoCalAtr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) wkpid;
        hash += (int) jobid;
        hash += (int) autoCalAtr;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoFlexOtCalPK)) {
            return false;
        }
        KshmtAutoFlexOtCalPK other = (KshmtAutoFlexOtCalPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.wkpid != other.wkpid) {
            return false;
        }
        if (this.jobid != other.jobid) {
            return false;
        }
        if (this.autoCalAtr != other.autoCalAtr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshmtAutoFlexOtCalPK[ cid=" + cid + ", wkpid=" + wkpid + ", jobid=" + jobid + ", autoCalAtr=" + autoCalAtr + " ]";
    }
    
}
