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
 * The Class KshmtAutoFlexOtCal.
 */
@Entity
@Table(name = "KSHMT_AUTO_FLEX_OT_CAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshmtAutoFlexOtCal.findAll", query = "SELECT k FROM KshmtAutoFlexOtCal k"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByInsDate", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByInsCcd", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByInsScd", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByInsPg", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByUpdDate", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByUpdCcd", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByUpdScd", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByUpdPg", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByExclusVer", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByCid", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.kshmtAutoFlexOtCalPK.cid = :cid"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByWkpid", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.kshmtAutoFlexOtCalPK.wkpid = :wkpid"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByJobid", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.kshmtAutoFlexOtCalPK.jobid = :jobid"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByAutoCalAtr", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.kshmtAutoFlexOtCalPK.autoCalAtr = :autoCalAtr"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByFlexOtTime", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.flexOtTime = :flexOtTime"),
    @NamedQuery(name = "KshmtAutoFlexOtCal.findByFlexOtNightTime", query = "SELECT k FROM KshmtAutoFlexOtCal k WHERE k.flexOtNightTime = :flexOtNightTime")})
public class KshmtAutoFlexOtCal implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto flex ot cal PK. */
    @EmbeddedId
    protected KshmtAutoFlexOtCalPK kshmtAutoFlexOtCalPK;
    
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
    
    /** The flex ot time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_OT_TIME")
    private short flexOtTime;
    
    /** The flex ot night time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_OT_NIGHT_TIME")
    private short flexOtNightTime;

    /**
     * Instantiates a new kshmt auto flex ot cal.
     */
    public KshmtAutoFlexOtCal() {
    }

    /**
     * Instantiates a new kshmt auto flex ot cal.
     *
     * @param kshmtAutoFlexOtCalPK the kshmt auto flex ot cal PK
     */
    public KshmtAutoFlexOtCal(KshmtAutoFlexOtCalPK kshmtAutoFlexOtCalPK) {
        this.kshmtAutoFlexOtCalPK = kshmtAutoFlexOtCalPK;
    }

    /**
     * Instantiates a new kshmt auto flex ot cal.
     *
     * @param kshmtAutoFlexOtCalPK the kshmt auto flex ot cal PK
     * @param exclusVer the exclus ver
     * @param flexOtTime the flex ot time
     * @param flexOtNightTime the flex ot night time
     */
    public KshmtAutoFlexOtCal(KshmtAutoFlexOtCalPK kshmtAutoFlexOtCalPK, int exclusVer, short flexOtTime, short flexOtNightTime) {
        this.kshmtAutoFlexOtCalPK = kshmtAutoFlexOtCalPK;
        this.exclusVer = exclusVer;
        this.flexOtTime = flexOtTime;
        this.flexOtNightTime = flexOtNightTime;
    }

    /**
     * Instantiates a new kshmt auto flex ot cal.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     * @param jobid the jobid
     * @param autoCalAtr the auto cal atr
     */
    public KshmtAutoFlexOtCal(String cid, short wkpid, short jobid, short autoCalAtr) {
        this.kshmtAutoFlexOtCalPK = new KshmtAutoFlexOtCalPK(cid, wkpid, jobid, autoCalAtr);
    }

    /**
     * Gets the kshmt auto flex ot cal PK.
     *
     * @return the kshmt auto flex ot cal PK
     */
    public KshmtAutoFlexOtCalPK getKshmtAutoFlexOtCalPK() {
        return kshmtAutoFlexOtCalPK;
    }

    /**
     * Sets the kshmt auto flex ot cal PK.
     *
     * @param kshmtAutoFlexOtCalPK the new kshmt auto flex ot cal PK
     */
    public void setKshmtAutoFlexOtCalPK(KshmtAutoFlexOtCalPK kshmtAutoFlexOtCalPK) {
        this.kshmtAutoFlexOtCalPK = kshmtAutoFlexOtCalPK;
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
     * Gets the flex ot night time.
     *
     * @return the flex ot night time
     */
    public short getFlexOtNightTime() {
        return flexOtNightTime;
    }

    /**
     * Sets the flex ot night time.
     *
     * @param flexOtNightTime the new flex ot night time
     */
    public void setFlexOtNightTime(short flexOtNightTime) {
        this.flexOtNightTime = flexOtNightTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtAutoFlexOtCalPK != null ? kshmtAutoFlexOtCalPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoFlexOtCal)) {
            return false;
        }
        KshmtAutoFlexOtCal other = (KshmtAutoFlexOtCal) object;
        if ((this.kshmtAutoFlexOtCalPK == null && other.kshmtAutoFlexOtCalPK != null) || (this.kshmtAutoFlexOtCalPK != null && !this.kshmtAutoFlexOtCalPK.equals(other.kshmtAutoFlexOtCalPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtAutoFlexOtCal[ kshmtAutoFlexOtCalPK=" + kshmtAutoFlexOtCalPK + " ]";
    }
    
}
