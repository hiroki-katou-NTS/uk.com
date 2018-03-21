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
@Table(name = "KSHST_EMP_NORMAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstEmpNormalSet.findAll", query = "SELECT k FROM KshstEmpNormalSet k"),
    @NamedQuery(name = "KshstEmpNormalSet.findByInsDate", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstEmpNormalSet.findByInsCcd", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstEmpNormalSet.findByInsScd", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstEmpNormalSet.findByInsPg", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstEmpNormalSet.findByUpdDate", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstEmpNormalSet.findByUpdCcd", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstEmpNormalSet.findByUpdScd", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstEmpNormalSet.findByUpdPg", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstEmpNormalSet.findByExclusVer", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstEmpNormalSet.findByCid", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.kshstEmpNormalSetPK.cid = :cid"),
    @NamedQuery(name = "KshstEmpNormalSet.findByEmpCd", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.kshstEmpNormalSetPK.empCd = :empCd"),
    @NamedQuery(name = "KshstEmpNormalSet.findByYear", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.kshstEmpNormalSetPK.year = :year"),
    @NamedQuery(name = "KshstEmpNormalSet.findByJanTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.janTime = :janTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByFebTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.febTime = :febTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByMarTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.marTime = :marTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByAprTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.aprTime = :aprTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByMayTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.mayTime = :mayTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByJunTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.junTime = :junTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByJulTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.julTime = :julTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByAugTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.augTime = :augTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findBySepTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.sepTime = :sepTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByOctTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.octTime = :octTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByNovTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.novTime = :novTime"),
    @NamedQuery(name = "KshstEmpNormalSet.findByDecTime", query = "SELECT k FROM KshstEmpNormalSet k WHERE k.decTime = :decTime")})
public class KshstEmpNormalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstEmpNormalSetPK kshstEmpNormalSetPK;
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

    public KshstEmpNormalSet() {
    }

    public KshstEmpNormalSet(KshstEmpNormalSetPK kshstEmpNormalSetPK) {
        this.kshstEmpNormalSetPK = kshstEmpNormalSetPK;
    }

    public KshstEmpNormalSet(KshstEmpNormalSetPK kshstEmpNormalSetPK, int exclusVer, int janTime, int febTime, int marTime, int aprTime, int mayTime, int junTime, int julTime, int augTime, int sepTime, int octTime, int novTime, int decTime) {
        this.kshstEmpNormalSetPK = kshstEmpNormalSetPK;
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

    public KshstEmpNormalSet(String cid, String empCd, short year) {
        this.kshstEmpNormalSetPK = new KshstEmpNormalSetPK(cid, empCd, year);
    }

    public KshstEmpNormalSetPK getKshstEmpNormalSetPK() {
        return kshstEmpNormalSetPK;
    }

    public void setKshstEmpNormalSetPK(KshstEmpNormalSetPK kshstEmpNormalSetPK) {
        this.kshstEmpNormalSetPK = kshstEmpNormalSetPK;
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
        hash += (kshstEmpNormalSetPK != null ? kshstEmpNormalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstEmpNormalSet)) {
            return false;
        }
        KshstEmpNormalSet other = (KshstEmpNormalSet) object;
        if ((this.kshstEmpNormalSetPK == null && other.kshstEmpNormalSetPK != null) || (this.kshstEmpNormalSetPK != null && !this.kshstEmpNormalSetPK.equals(other.kshstEmpNormalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstEmpNormalSet[ kshstEmpNormalSetPK=" + kshstEmpNormalSetPK + " ]";
    }
    
}
