/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime;

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
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KSHST_WKP_NORMAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstWkpNormalSet.findAll", query = "SELECT k FROM KshstWkpNormalSet k"),
    @NamedQuery(name = "KshstWkpNormalSet.findByInsDate", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstWkpNormalSet.findByInsCcd", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstWkpNormalSet.findByInsScd", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstWkpNormalSet.findByInsPg", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstWkpNormalSet.findByUpdDate", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstWkpNormalSet.findByUpdCcd", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstWkpNormalSet.findByUpdScd", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstWkpNormalSet.findByUpdPg", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstWkpNormalSet.findByExclusVer", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstWkpNormalSet.findByCid", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.kshstWkpNormalSetPK.cid = :cid"),
    @NamedQuery(name = "KshstWkpNormalSet.findByWkpId", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.kshstWkpNormalSetPK.wkpId = :wkpId"),
    @NamedQuery(name = "KshstWkpNormalSet.findByYear", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.kshstWkpNormalSetPK.year = :year"),
    @NamedQuery(name = "KshstWkpNormalSet.findByJanTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.janTime = :janTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByFebTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.febTime = :febTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByMarTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.marTime = :marTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByAprTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.aprTime = :aprTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByMayTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.mayTime = :mayTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByJunTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.junTime = :junTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByJulTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.julTime = :julTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByAugTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.augTime = :augTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findBySepTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.sepTime = :sepTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByOctTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.octTime = :octTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByNovTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.novTime = :novTime"),
    @NamedQuery(name = "KshstWkpNormalSet.findByDecTime", query = "SELECT k FROM KshstWkpNormalSet k WHERE k.decTime = :decTime")})
public class KshstWkpNormalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstWkpNormalSetPK kshstWkpNormalSetPK;
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;
    @Size(max = 30)
    @Column(name = "INS_SCD")
    private String insScd;
    @Size(max = 14)
    @Column(name = "INS_PG")
    private String insPg;
    @Column(name = "UPD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Size(max = 4)
    @Column(name = "UPD_CCD")
    private String updCcd;
    @Size(max = 30)
    @Column(name = "UPD_SCD")
    private String updScd;
    @Size(max = 14)
    @Column(name = "UPD_PG")
    private String updPg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JAN_TIME")
    private int janTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FEB_TIME")
    private int febTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAR_TIME")
    private int marTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APR_TIME")
    private int aprTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAY_TIME")
    private int mayTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JUN_TIME")
    private int junTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JUL_TIME")
    private int julTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUG_TIME")
    private int augTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEP_TIME")
    private int sepTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OCT_TIME")
    private int octTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOV_TIME")
    private int novTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEC_TIME")
    private int decTime;

    public KshstWkpNormalSet() {
    }

    public KshstWkpNormalSet(KshstWkpNormalSetPK kshstWkpNormalSetPK) {
        this.kshstWkpNormalSetPK = kshstWkpNormalSetPK;
    }

    public KshstWkpNormalSet(KshstWkpNormalSetPK kshstWkpNormalSetPK, int exclusVer, int janTime, int febTime, int marTime, int aprTime, int mayTime, int junTime, int julTime, int augTime, int sepTime, int octTime, int novTime, int decTime) {
        this.kshstWkpNormalSetPK = kshstWkpNormalSetPK;
        this.exclusVer = exclusVer;
        this.janTime = janTime;
        this.febTime = febTime;
        this.marTime = marTime;
        this.aprTime = aprTime;
        this.mayTime = mayTime;
        this.junTime = junTime;
        this.julTime = julTime;
        this.augTime = augTime;
        this.sepTime = sepTime;
        this.octTime = octTime;
        this.novTime = novTime;
        this.decTime = decTime;
    }

    public KshstWkpNormalSet(String cid, String wkpId, short year) {
        this.kshstWkpNormalSetPK = new KshstWkpNormalSetPK(cid, wkpId, year);
    }

    public KshstWkpNormalSetPK getKshstWkpNormalSetPK() {
        return kshstWkpNormalSetPK;
    }

    public void setKshstWkpNormalSetPK(KshstWkpNormalSetPK kshstWkpNormalSetPK) {
        this.kshstWkpNormalSetPK = kshstWkpNormalSetPK;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public String getInsCcd() {
        return insCcd;
    }

    public void setInsCcd(String insCcd) {
        this.insCcd = insCcd;
    }

    public String getInsScd() {
        return insScd;
    }

    public void setInsScd(String insScd) {
        this.insScd = insScd;
    }

    public String getInsPg() {
        return insPg;
    }

    public void setInsPg(String insPg) {
        this.insPg = insPg;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUpdCcd() {
        return updCcd;
    }

    public void setUpdCcd(String updCcd) {
        this.updCcd = updCcd;
    }

    public String getUpdScd() {
        return updScd;
    }

    public void setUpdScd(String updScd) {
        this.updScd = updScd;
    }

    public String getUpdPg() {
        return updPg;
    }

    public void setUpdPg(String updPg) {
        this.updPg = updPg;
    }

    public int getExclusVer() {
        return exclusVer;
    }

    public void setExclusVer(int exclusVer) {
        this.exclusVer = exclusVer;
    }

    public int getJanTime() {
        return janTime;
    }

    public void setJanTime(int janTime) {
        this.janTime = janTime;
    }

    public int getFebTime() {
        return febTime;
    }

    public void setFebTime(int febTime) {
        this.febTime = febTime;
    }

    public int getMarTime() {
        return marTime;
    }

    public void setMarTime(int marTime) {
        this.marTime = marTime;
    }

    public int getAprTime() {
        return aprTime;
    }

    public void setAprTime(int aprTime) {
        this.aprTime = aprTime;
    }

    public int getMayTime() {
        return mayTime;
    }

    public void setMayTime(int mayTime) {
        this.mayTime = mayTime;
    }

    public int getJunTime() {
        return junTime;
    }

    public void setJunTime(int junTime) {
        this.junTime = junTime;
    }

    public int getJulTime() {
        return julTime;
    }

    public void setJulTime(int julTime) {
        this.julTime = julTime;
    }

    public int getAugTime() {
        return augTime;
    }

    public void setAugTime(int augTime) {
        this.augTime = augTime;
    }

    public int getSepTime() {
        return sepTime;
    }

    public void setSepTime(int sepTime) {
        this.sepTime = sepTime;
    }

    public int getOctTime() {
        return octTime;
    }

    public void setOctTime(int octTime) {
        this.octTime = octTime;
    }

    public int getNovTime() {
        return novTime;
    }

    public void setNovTime(int novTime) {
        this.novTime = novTime;
    }

    public int getDecTime() {
        return decTime;
    }

    public void setDecTime(int decTime) {
        this.decTime = decTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstWkpNormalSetPK != null ? kshstWkpNormalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstWkpNormalSet)) {
            return false;
        }
        KshstWkpNormalSet other = (KshstWkpNormalSet) object;
        if ((this.kshstWkpNormalSetPK == null && other.kshstWkpNormalSetPK != null) || (this.kshstWkpNormalSetPK != null && !this.kshstWkpNormalSetPK.equals(other.kshstWkpNormalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstWkpNormalSet[ kshstWkpNormalSetPK=" + kshstWkpNormalSetPK + " ]";
    }
    
}
