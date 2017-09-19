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
 * The Class KshmtAutoOtCalSet.
 */
@Entity
@Table(name = "KSHMT_AUTO_OT_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshmtAutoOtCalSet.findAll", query = "SELECT k FROM KshmtAutoOtCalSet k"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByInsDate", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByInsCcd", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByInsScd", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByInsPg", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByUpdDate", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByUpdCcd", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByUpdScd", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByUpdPg", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByExclusVer", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByCid", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.kshmtAutoOtCalSetPK.cid = :cid"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByWkpid", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.kshmtAutoOtCalSetPK.wkpid = :wkpid"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByJobid", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.kshmtAutoOtCalSetPK.jobid = :jobid"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByAutoCalAtr", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.kshmtAutoOtCalSetPK.autoCalAtr = :autoCalAtr"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByEarlyOtTime", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.earlyOtTime = :earlyOtTime"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByEarlyMidOtTime", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.earlyMidOtTime = :earlyMidOtTime"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByNormalOtTime", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.normalOtTime = :normalOtTime"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByNormalMidOtTime", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.normalMidOtTime = :normalMidOtTime"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByLegalOtTime", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.legalOtTime = :legalOtTime"),
    @NamedQuery(name = "KshmtAutoOtCalSet.findByLegalMidOtTime", query = "SELECT k FROM KshmtAutoOtCalSet k WHERE k.legalMidOtTime = :legalMidOtTime")})
public class KshmtAutoOtCalSet implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto ot cal set PK. */
    @EmbeddedId
    protected KshmtAutoOtCalSetPK kshmtAutoOtCalSetPK;
    
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
    
    /** The early ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_OT_TIME")
    private short earlyOtTime;
    
    /** The early mid ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_MID_OT_TIME")
    private short earlyMidOtTime;
    
    /** The normal ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_OT_TIME")
    private short normalOtTime;
    
    /** The normal mid ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_MID_OT_TIME")
    private short normalMidOtTime;
    
    /** The legal ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_OT_TIME")
    private short legalOtTime;
    
    /** The legal mid ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_MID_OT_TIME")
    private short legalMidOtTime;

    /**
     * Instantiates a new kshmt auto ot cal set.
     */
    public KshmtAutoOtCalSet() {
    }

    /**
     * Instantiates a new kshmt auto ot cal set.
     *
     * @param kshmtAutoOtCalSetPK the kshmt auto ot cal set PK
     */
    public KshmtAutoOtCalSet(KshmtAutoOtCalSetPK kshmtAutoOtCalSetPK) {
        this.kshmtAutoOtCalSetPK = kshmtAutoOtCalSetPK;
    }

    /**
     * Instantiates a new kshmt auto ot cal set.
     *
     * @param kshmtAutoOtCalSetPK the kshmt auto ot cal set PK
     * @param exclusVer the exclus ver
     * @param earlyOtTime the early ot time
     * @param earlyMidOtTime the early mid ot time
     * @param normalOtTime the normal ot time
     * @param normalMidOtTime the normal mid ot time
     * @param legalOtTime the legal ot time
     * @param legalMidOtTime the legal mid ot time
     */
    public KshmtAutoOtCalSet(KshmtAutoOtCalSetPK kshmtAutoOtCalSetPK, int exclusVer, short earlyOtTime, short earlyMidOtTime, short normalOtTime, short normalMidOtTime, short legalOtTime, short legalMidOtTime) {
        this.kshmtAutoOtCalSetPK = kshmtAutoOtCalSetPK;
        this.exclusVer = exclusVer;
        this.earlyOtTime = earlyOtTime;
        this.earlyMidOtTime = earlyMidOtTime;
        this.normalOtTime = normalOtTime;
        this.normalMidOtTime = normalMidOtTime;
        this.legalOtTime = legalOtTime;
        this.legalMidOtTime = legalMidOtTime;
    }

    /**
     * Instantiates a new kshmt auto ot cal set.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     * @param jobid the jobid
     * @param autoCalAtr the auto cal atr
     */
    public KshmtAutoOtCalSet(String cid, short wkpid, short jobid, short autoCalAtr) {
        this.kshmtAutoOtCalSetPK = new KshmtAutoOtCalSetPK(cid, wkpid, jobid, autoCalAtr);
    }

    /**
     * Gets the kshmt auto ot cal set PK.
     *
     * @return the kshmt auto ot cal set PK
     */
    public KshmtAutoOtCalSetPK getKshmtAutoOtCalSetPK() {
        return kshmtAutoOtCalSetPK;
    }

    /**
     * Sets the kshmt auto ot cal set PK.
     *
     * @param kshmtAutoOtCalSetPK the new kshmt auto ot cal set PK
     */
    public void setKshmtAutoOtCalSetPK(KshmtAutoOtCalSetPK kshmtAutoOtCalSetPK) {
        this.kshmtAutoOtCalSetPK = kshmtAutoOtCalSetPK;
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
     * Gets the early ot time.
     *
     * @return the early ot time
     */
    public short getEarlyOtTime() {
        return earlyOtTime;
    }

    /**
     * Sets the early ot time.
     *
     * @param earlyOtTime the new early ot time
     */
    public void setEarlyOtTime(short earlyOtTime) {
        this.earlyOtTime = earlyOtTime;
    }

    /**
     * Gets the early mid ot time.
     *
     * @return the early mid ot time
     */
    public short getEarlyMidOtTime() {
        return earlyMidOtTime;
    }

    /**
     * Sets the early mid ot time.
     *
     * @param earlyMidOtTime the new early mid ot time
     */
    public void setEarlyMidOtTime(short earlyMidOtTime) {
        this.earlyMidOtTime = earlyMidOtTime;
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
     * Gets the normal mid ot time.
     *
     * @return the normal mid ot time
     */
    public short getNormalMidOtTime() {
        return normalMidOtTime;
    }

    /**
     * Sets the normal mid ot time.
     *
     * @param normalMidOtTime the new normal mid ot time
     */
    public void setNormalMidOtTime(short normalMidOtTime) {
        this.normalMidOtTime = normalMidOtTime;
    }

    /**
     * Gets the legal ot time.
     *
     * @return the legal ot time
     */
    public short getLegalOtTime() {
        return legalOtTime;
    }

    /**
     * Sets the legal ot time.
     *
     * @param legalOtTime the new legal ot time
     */
    public void setLegalOtTime(short legalOtTime) {
        this.legalOtTime = legalOtTime;
    }

    /**
     * Gets the legal mid ot time.
     *
     * @return the legal mid ot time
     */
    public short getLegalMidOtTime() {
        return legalMidOtTime;
    }

    /**
     * Sets the legal mid ot time.
     *
     * @param legalMidOtTime the new legal mid ot time
     */
    public void setLegalMidOtTime(short legalMidOtTime) {
        this.legalMidOtTime = legalMidOtTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtAutoOtCalSetPK != null ? kshmtAutoOtCalSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoOtCalSet)) {
            return false;
        }
        KshmtAutoOtCalSet other = (KshmtAutoOtCalSet) object;
        if ((this.kshmtAutoOtCalSetPK == null && other.kshmtAutoOtCalSetPK != null) || (this.kshmtAutoOtCalSetPK != null && !this.kshmtAutoOtCalSetPK.equals(other.kshmtAutoOtCalSetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoOtCalSet[ kshmtAutoOtCalSetPK=" + kshmtAutoOtCalSetPK + " ]";
    }
    
}
