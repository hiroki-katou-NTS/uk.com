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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class KshmtAutoWkpCalSet.
 */
@Entity
@Table(name = "KSHMT_AUTO_WKP_CAL_SET ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshmtAutoWkpCalSet.findAll", query = "SELECT k FROM KshmtAutoWkpCalSet k"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByInsDate", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByInsCcd", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByInsScd", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByInsPg", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByUpdDate", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByUpdCcd", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByUpdScd", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByUpdPg", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByExclusVer", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByCid", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.kshmtAutoWkpCalSetPK.cid = :cid"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByWkpid", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.kshmtAutoWkpCalSetPK.wkpid = :wkpid"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByNormalOtTime", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.normalOtTime = :normalOtTime"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByFlexOtTime", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.flexOtTime = :flexOtTime"),
    @NamedQuery(name = "KshmtAutoWkpCalSet.findByRestTime", query = "SELECT k FROM KshmtAutoWkpCalSet k WHERE k.restTime = :restTime")})
public class KshmtAutoWkpCalSet implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto wkp cal set PK. */
    @EmbeddedId
    protected KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK;
    
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
    
    /** The normal ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_OT_TIME")
    private short normalOtTime;
    
    /** The flex ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_OT_TIME")
    private short flexOtTime;
    
    /** The rest time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "REST_TIME")
    private short restTime;

    /**
     * Instantiates a new kshmt auto wkp cal set.
     */
    public KshmtAutoWkpCalSet() {
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param kshmtAutoWkpCalSetPK the kshmt auto wkp cal set PK
     */
    public KshmtAutoWkpCalSet(KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK) {
        this.kshmtAutoWkpCalSetPK = kshmtAutoWkpCalSetPK;
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param kshmtAutoWkpCalSetPK the kshmt auto wkp cal set PK
     * @param exclusVer the exclus ver
     * @param normalOtTime the normal ot time
     * @param flexOtTime the flex ot time
     * @param restTime the rest time
     */
    public KshmtAutoWkpCalSet(KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK, int exclusVer, short normalOtTime, short flexOtTime, short restTime) {
        this.kshmtAutoWkpCalSetPK = kshmtAutoWkpCalSetPK;
        this.exclusVer = exclusVer;
        this.normalOtTime = normalOtTime;
        this.flexOtTime = flexOtTime;
        this.restTime = restTime;
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KshmtAutoWkpCalSet(String cid, short wkpid) {
        this.kshmtAutoWkpCalSetPK = new KshmtAutoWkpCalSetPK(cid, wkpid);
    }

    /**
     * Gets the kshmt auto wkp cal set PK.
     *
     * @return the kshmt auto wkp cal set PK
     */
    public KshmtAutoWkpCalSetPK getKshmtAutoWkpCalSetPK() {
        return kshmtAutoWkpCalSetPK;
    }

    /**
     * Sets the kshmt auto wkp cal set PK.
     *
     * @param kshmtAutoWkpCalSetPK the new kshmt auto wkp cal set PK
     */
    public void setKshmtAutoWkpCalSetPK(KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK) {
        this.kshmtAutoWkpCalSetPK = kshmtAutoWkpCalSetPK;
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
     * Gets the normal ot time.
     *
     * @return the normal ot time
     */
    public short getNormalOtTime() {
        return normalOtTime;
    }

    /**
     * Sets the normal ot time.
     *
     * @param normalOtTime the new normal ot time
     */
    public void setNormalOtTime(short normalOtTime) {
        this.normalOtTime = normalOtTime;
    }

    /**
     * Gets the flex ot time.
     *
     * @return the flex ot time
     */
    public short getFlexOtTime() {
        return flexOtTime;
    }

    /**
     * Sets the flex ot time.
     *
     * @param flexOtTime the new flex ot time
     */
    public void setFlexOtTime(short flexOtTime) {
        this.flexOtTime = flexOtTime;
    }

    /**
     * Gets the rest time.
     *
     * @return the rest time
     */
    public short getRestTime() {
        return restTime;
    }

    /**
     * Sets the rest time.
     *
     * @param restTime the new rest time
     */
    public void setRestTime(short restTime) {
        this.restTime = restTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtAutoWkpCalSetPK != null ? kshmtAutoWkpCalSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoWkpCalSet)) {
            return false;
        }
        KshmtAutoWkpCalSet other = (KshmtAutoWkpCalSet) object;
        if ((this.kshmtAutoWkpCalSetPK == null && other.kshmtAutoWkpCalSetPK != null) || (this.kshmtAutoWkpCalSetPK != null && !this.kshmtAutoWkpCalSetPK.equals(other.kshmtAutoWkpCalSetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoWkpCalSet[ kshmtAutoWkpCalSetPK=" + kshmtAutoWkpCalSetPK + " ]";
    }
    
}
