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
 * The Class KshmtAutoWkpCalSetPK.
 */
@Embeddable
public class KshmtAutoWkpCalSetPK implements Serializable {
    
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

    /**
     * Instantiates a new kshmt auto wkp cal set PK.
     */
    public KshmtAutoWkpCalSetPK() {
    }

    /**
     * Instantiates a new kshmt auto wkp cal set PK.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KshmtAutoWkpCalSetPK(String cid, short wkpid) {
        this.cid = cid;
        this.wkpid = wkpid;
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) wkpid;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoWkpCalSetPK)) {
            return false;
        }
        KshmtAutoWkpCalSetPK other = (KshmtAutoWkpCalSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.wkpid != other.wkpid) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoWkpCalSetPK[ cid=" + cid + ", wkpid=" + wkpid + " ]";
    }
    
}
