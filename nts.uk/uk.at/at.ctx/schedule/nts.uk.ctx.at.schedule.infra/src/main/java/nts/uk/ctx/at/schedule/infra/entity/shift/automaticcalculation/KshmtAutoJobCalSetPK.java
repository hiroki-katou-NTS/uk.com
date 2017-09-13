/*
 * 
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class KshmtAutoJobCalSetPK.
 */
@Embeddable
public class KshmtAutoJobCalSetPK implements Serializable {
    
    /** The cid. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    /** The jobid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOBID")
    private short jobid;

    /**
     * Instantiates a new kshmt auto job cal set PK.
     */
    public KshmtAutoJobCalSetPK() {
    }

    /**
     * Instantiates a new kshmt auto job cal set PK.
     *
     * @param cid the cid
     * @param jobid the jobid
     */
    public KshmtAutoJobCalSetPK(String cid, short jobid) {
        this.cid = cid;
        this.jobid = jobid;
    }

    /**
     * Gets the cid.
     *
     * @return the cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * Sets the cid.
     *
     * @param cid the new cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * Gets the jobid.
     *
     * @return the jobid
     */
    public short getJobid() {
        return jobid;
    }

    /**
     * Sets the jobid.
     *
     * @param jobid the new jobid
     */
    public void setJobid(short jobid) {
        this.jobid = jobid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) jobid;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoJobCalSetPK)) {
            return false;
        }
        KshmtAutoJobCalSetPK other = (KshmtAutoJobCalSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.jobid != other.jobid) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoJobCalSetPK[ cid=" + cid + ", jobid=" + jobid + " ]";
    }
    
}
