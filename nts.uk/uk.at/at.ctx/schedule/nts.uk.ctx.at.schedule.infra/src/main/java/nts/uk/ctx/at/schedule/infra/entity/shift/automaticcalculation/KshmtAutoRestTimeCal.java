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
 * The Class KshmtAutoRestTimeCal.
 */
@Entity
@Table(name = "KSHMT_AUTO_REST_TIME_CAL ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshmtAutoRestTimeCal.findAll", query = "SELECT k FROM KshmtAutoRestTimeCal k"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByInsDate", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByInsCcd", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByInsScd", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByInsPg", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByUpdDate", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByUpdCcd", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByUpdScd", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByUpdPg", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByExclusVer", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByCid", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.kshmtAutoRestTimeCalPK.cid = :cid"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByWkpid", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.kshmtAutoRestTimeCalPK.wkpid = :wkpid"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByJobid", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.kshmtAutoRestTimeCalPK.jobid = :jobid"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByAutoCalAtr", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.kshmtAutoRestTimeCalPK.autoCalAtr = :autoCalAtr"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByRestTime", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.restTime = :restTime"),
    @NamedQuery(name = "KshmtAutoRestTimeCal.findByLateNightTime", query = "SELECT k FROM KshmtAutoRestTimeCal k WHERE k.lateNightTime = :lateNightTime")})
public class KshmtAutoRestTimeCal implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto rest time cal PK. */
    @EmbeddedId
    protected KshmtAutoRestTimeCalPK kshmtAutoRestTimeCalPK;
    
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
    
    /** The rest time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "REST_TIME")
    private short restTime;
    
    /** The late night time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME")
    private short lateNightTime;

    /**
     * Instantiates a new kshmt auto rest time cal.
     */
    public KshmtAutoRestTimeCal() {
    }

    /**
     * Instantiates a new kshmt auto rest time cal.
     *
     * @param kshmtAutoRestTimeCalPK the kshmt auto rest time cal PK
     */
    public KshmtAutoRestTimeCal(KshmtAutoRestTimeCalPK kshmtAutoRestTimeCalPK) {
        this.kshmtAutoRestTimeCalPK = kshmtAutoRestTimeCalPK;
    }

    /**
     * Instantiates a new kshmt auto rest time cal.
     *
     * @param kshmtAutoRestTimeCalPK the kshmt auto rest time cal PK
     * @param exclusVer the exclus ver
     * @param restTime the rest time
     * @param lateNightTime the late night time
     */
    public KshmtAutoRestTimeCal(KshmtAutoRestTimeCalPK kshmtAutoRestTimeCalPK, int exclusVer, short restTime, short lateNightTime) {
        this.kshmtAutoRestTimeCalPK = kshmtAutoRestTimeCalPK;
        this.exclusVer = exclusVer;
        this.restTime = restTime;
        this.lateNightTime = lateNightTime;
    }

    /**
     * Instantiates a new kshmt auto rest time cal.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     * @param jobid the jobid
     * @param autoCalAtr the auto cal atr
     */
    public KshmtAutoRestTimeCal(String cid, short wkpid, short jobid, short autoCalAtr) {
        this.kshmtAutoRestTimeCalPK = new KshmtAutoRestTimeCalPK(cid, wkpid, jobid, autoCalAtr);
    }

    /**
     * Gets the kshmt auto rest time cal PK.
     *
     * @return the kshmt auto rest time cal PK
     */
    public KshmtAutoRestTimeCalPK getKshmtAutoRestTimeCalPK() {
        return kshmtAutoRestTimeCalPK;
    }

    /**
     * Sets the kshmt auto rest time cal PK.
     *
     * @param kshmtAutoRestTimeCalPK the new kshmt auto rest time cal PK
     */
    public void setKshmtAutoRestTimeCalPK(KshmtAutoRestTimeCalPK kshmtAutoRestTimeCalPK) {
        this.kshmtAutoRestTimeCalPK = kshmtAutoRestTimeCalPK;
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

    /**
     * Gets the late night time.
     *
     * @return the late night time
     */
    public short getLateNightTime() {
        return lateNightTime;
    }

    /**
     * Sets the late night time.
     *
     * @param lateNightTime the new late night time
     */
    public void setLateNightTime(short lateNightTime) {
        this.lateNightTime = lateNightTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtAutoRestTimeCalPK != null ? kshmtAutoRestTimeCalPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoRestTimeCal)) {
            return false;
        }
        KshmtAutoRestTimeCal other = (KshmtAutoRestTimeCal) object;
        if ((this.kshmtAutoRestTimeCalPK == null && other.kshmtAutoRestTimeCalPK != null) || (this.kshmtAutoRestTimeCalPK != null && !this.kshmtAutoRestTimeCalPK.equals(other.kshmtAutoRestTimeCalPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoRestTimeCal[ kshmtAutoRestTimeCalPK=" + kshmtAutoRestTimeCalPK + " ]";
    }
    
}
