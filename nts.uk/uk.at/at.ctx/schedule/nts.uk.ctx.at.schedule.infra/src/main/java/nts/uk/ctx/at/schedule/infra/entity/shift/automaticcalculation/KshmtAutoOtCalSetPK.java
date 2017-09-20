/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class KshmtAutoOtCalSetPK.
 */
@Embeddable
public class KshmtAutoOtCalSetPK implements Serializable {
    
    /** The cid. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    /** The wkpid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKPID")
    private short wkpid;
    
    /** The jobid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOBID")
    private short jobid;
    
    /** The auto cal atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTO_CAL_ATR")
    private short autoCalAtr;

    /**
     * Instantiates a new kshmt auto ot cal set PK.
     */
    public KshmtAutoOtCalSetPK() {
    }

    /**
     * Instantiates a new kshmt auto ot cal set PK.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     * @param jobid the jobid
     * @param autoCalAtr the auto cal atr
     */
    public KshmtAutoOtCalSetPK(String cid, short wkpid, short jobid, short autoCalAtr) {
        this.cid = cid;
        this.wkpid = wkpid;
        this.jobid = jobid;
        this.autoCalAtr = autoCalAtr;
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
     * Gets the wkpid.
     *
     * @return the wkpid
     */
    public short getWkpid() {
        return wkpid;
    }

    /**
     * Sets the wkpid.
     *
     * @param wkpid the new wkpid
     */
    public void setWkpid(short wkpid) {
        this.wkpid = wkpid;
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

    /**
     * Gets the auto cal atr.
     *
     * @return the auto cal atr
     */
    public short getAutoCalAtr() {
        return autoCalAtr;
    }

    /**
     * Sets the auto cal atr.
     *
     * @param autoCalAtr the new auto cal atr
     */
    public void setAutoCalAtr(short autoCalAtr) {
        this.autoCalAtr = autoCalAtr;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) wkpid;
        hash += (int) jobid;
        hash += (int) autoCalAtr;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoOtCalSetPK)) {
            return false;
        }
        KshmtAutoOtCalSetPK other = (KshmtAutoOtCalSetPK) object;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoOtCalSetPK[ cid=" + cid + ", wkpid=" + wkpid + ", jobid=" + jobid + ", autoCalAtr=" + autoCalAtr + " ]";
    }
    
}
