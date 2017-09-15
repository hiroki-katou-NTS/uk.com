/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class KshmtAutoUseUnitSet.
 */
@Entity
@Table(name = "KSHMT_AUTO_USE_UNIT_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshmtAutoUseUnitSet.findAll", query = "SELECT k FROM KshmtAutoUseUnitSet k"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByInsDate", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByInsCcd", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByInsScd", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByInsPg", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByUpdDate", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByUpdCcd", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByUpdScd", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByUpdPg", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByExclusVer", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByCid", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.cid = :cid"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByJobCalSet", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.jobCalSet = :jobCalSet"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByWkpCalSet", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.wkpCalSet = :wkpCalSet"),
    @NamedQuery(name = "KshmtAutoUseUnitSet.findByWkpJobCalSet", query = "SELECT k FROM KshmtAutoUseUnitSet k WHERE k.wkpJobCalSet = :wkpJobCalSet")})
public class KshmtAutoUseUnitSet implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The ins date. */
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    
    /** The ins ccd. */
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;
    
    /** The ins scd. */
    @Size(max = 12)
    @Column(name = "INS_SCD")
    private String insScd;
    
    /** The ins pg. */
    @Size(max = 14)
    @Column(name = "INS_PG")
    private String insPg;
    
    /** The upd date. */
    @Column(name = "UPD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    
    /** The upd ccd. */
    @Size(max = 4)
    @Column(name = "UPD_CCD")
    private String updCcd;
    
    /** The upd scd. */
    @Size(max = 12)
    @Column(name = "UPD_SCD")
    private String updScd;
    
    /** The upd pg. */
    @Size(max = 14)
    @Column(name = "UPD_PG")
    private String updPg;
    
    /** The exclus ver. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    /** The job cal set. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOB_CAL_SET")
    private short jobCalSet;
    
    /** The wkp cal set. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKP_CAL_SET")
    private short wkpCalSet;
    
    /** The wkp job cal set. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKP_JOB_CAL_SET")
    private short wkpJobCalSet;

    /**
     * Instantiates a new kshmt auto use unit set.
     */
    public KshmtAutoUseUnitSet() {
    }

    /**
     * Instantiates a new kshmt auto use unit set.
     *
     * @param cid the cid
     */
    public KshmtAutoUseUnitSet(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kshmt auto use unit set.
     *
     * @param cid the cid
     * @param exclusVer the exclus ver
     * @param jobCalSet the job cal set
     * @param wkpCalSet the wkp cal set
     * @param wkpJobCalSet the wkp job cal set
     */
    public KshmtAutoUseUnitSet(String cid, int exclusVer, short jobCalSet, short wkpCalSet, short wkpJobCalSet) {
        this.cid = cid;
        this.exclusVer = exclusVer;
        this.jobCalSet = jobCalSet;
        this.wkpCalSet = wkpCalSet;
        this.wkpJobCalSet = wkpJobCalSet;
    }

    /**
     * Gets the ins date.
     *
     * @return the ins date
     */
    public Date getInsDate() {
        return insDate;
    }

    /**
     * Sets the ins date.
     *
     * @param insDate the new ins date
     */
    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    /**
     * Gets the ins ccd.
     *
     * @return the ins ccd
     */
    public String getInsCcd() {
        return insCcd;
    }

    /**
     * Sets the ins ccd.
     *
     * @param insCcd the new ins ccd
     */
    public void setInsCcd(String insCcd) {
        this.insCcd = insCcd;
    }

    /**
     * Gets the ins scd.
     *
     * @return the ins scd
     */
    public String getInsScd() {
        return insScd;
    }

    /**
     * Sets the ins scd.
     *
     * @param insScd the new ins scd
     */
    public void setInsScd(String insScd) {
        this.insScd = insScd;
    }

    /**
     * Gets the ins pg.
     *
     * @return the ins pg
     */
    public String getInsPg() {
        return insPg;
    }

    /**
     * Sets the ins pg.
     *
     * @param insPg the new ins pg
     */
    public void setInsPg(String insPg) {
        this.insPg = insPg;
    }

    /**
     * Gets the upd date.
     *
     * @return the upd date
     */
    public Date getUpdDate() {
        return updDate;
    }

    /**
     * Sets the upd date.
     *
     * @param updDate the new upd date
     */
    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    /**
     * Gets the upd ccd.
     *
     * @return the upd ccd
     */
    public String getUpdCcd() {
        return updCcd;
    }

    /**
     * Sets the upd ccd.
     *
     * @param updCcd the new upd ccd
     */
    public void setUpdCcd(String updCcd) {
        this.updCcd = updCcd;
    }

    /**
     * Gets the upd scd.
     *
     * @return the upd scd
     */
    public String getUpdScd() {
        return updScd;
    }

    /**
     * Sets the upd scd.
     *
     * @param updScd the new upd scd
     */
    public void setUpdScd(String updScd) {
        this.updScd = updScd;
    }

    /**
     * Gets the upd pg.
     *
     * @return the upd pg
     */
    public String getUpdPg() {
        return updPg;
    }

    /**
     * Sets the upd pg.
     *
     * @param updPg the new upd pg
     */
    public void setUpdPg(String updPg) {
        this.updPg = updPg;
    }

    /**
     * Gets the exclus ver.
     *
     * @return the exclus ver
     */
    public int getExclusVer() {
        return exclusVer;
    }

    /**
     * Sets the exclus ver.
     *
     * @param exclusVer the new exclus ver
     */
    public void setExclusVer(int exclusVer) {
        this.exclusVer = exclusVer;
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
     * Gets the job cal set.
     *
     * @return the job cal set
     */
    public short getJobCalSet() {
        return jobCalSet;
    }

    /**
     * Sets the job cal set.
     *
     * @param jobCalSet the new job cal set
     */
    public void setJobCalSet(short jobCalSet) {
        this.jobCalSet = jobCalSet;
    }

    /**
     * Gets the wkp cal set.
     *
     * @return the wkp cal set
     */
    public short getWkpCalSet() {
        return wkpCalSet;
    }

    /**
     * Sets the wkp cal set.
     *
     * @param wkpCalSet the new wkp cal set
     */
    public void setWkpCalSet(short wkpCalSet) {
        this.wkpCalSet = wkpCalSet;
    }

    /**
     * Gets the wkp job cal set.
     *
     * @return the wkp job cal set
     */
    public short getWkpJobCalSet() {
        return wkpJobCalSet;
    }

    /**
     * Sets the wkp job cal set.
     *
     * @param wkpJobCalSet the new wkp job cal set
     */
    public void setWkpJobCalSet(short wkpJobCalSet) {
        this.wkpJobCalSet = wkpJobCalSet;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoUseUnitSet)) {
            return false;
        }
        KshmtAutoUseUnitSet other = (KshmtAutoUseUnitSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoUseUnitSet[ cid=" + cid + " ]";
    }
    
}
