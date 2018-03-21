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
@Table(name = "KSHST_WKP_TRANS_LAB_TIME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstWkpTransLabTime.findAll", query = "SELECT k FROM KshstWkpTransLabTime k"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByInsDate", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByInsCcd", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByInsScd", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByInsPg", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByUpdDate", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByUpdCcd", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByUpdScd", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByUpdPg", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByExclusVer", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByCid", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.kshstWkpTransLabTimePK.cid = :cid"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByWkpId", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.kshstWkpTransLabTimePK.wkpId = :wkpId"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByWeeklyTime", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.weeklyTime = :weeklyTime"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByStrWeek", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.strWeek = :strWeek"),
    @NamedQuery(name = "KshstWkpTransLabTime.findByDailyTime", query = "SELECT k FROM KshstWkpTransLabTime k WHERE k.dailyTime = :dailyTime")})
public class KshstWkpTransLabTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstWkpTransLabTimePK kshstWkpTransLabTimePK;
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
    @Column(name = "WEEKLY_TIME")
    private int weeklyTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_WEEK")
    private short strWeek;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAILY_TIME")
    private short dailyTime;

    public KshstWkpTransLabTime() {
    }

    public KshstWkpTransLabTime(KshstWkpTransLabTimePK kshstWkpTransLabTimePK) {
        this.kshstWkpTransLabTimePK = kshstWkpTransLabTimePK;
    }

    public KshstWkpTransLabTime(KshstWkpTransLabTimePK kshstWkpTransLabTimePK, int exclusVer, int weeklyTime, short strWeek, short dailyTime) {
        this.kshstWkpTransLabTimePK = kshstWkpTransLabTimePK;
        this.exclusVer = exclusVer;
        this.weeklyTime = weeklyTime;
        this.strWeek = strWeek;
        this.dailyTime = dailyTime;
    }

    public KshstWkpTransLabTime(String cid, String wkpId) {
        this.kshstWkpTransLabTimePK = new KshstWkpTransLabTimePK(cid, wkpId);
    }

    public KshstWkpTransLabTimePK getKshstWkpTransLabTimePK() {
        return kshstWkpTransLabTimePK;
    }

    public void setKshstWkpTransLabTimePK(KshstWkpTransLabTimePK kshstWkpTransLabTimePK) {
        this.kshstWkpTransLabTimePK = kshstWkpTransLabTimePK;
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

    public int getWeeklyTime() {
        return weeklyTime;
    }

    public void setWeeklyTime(int weeklyTime) {
        this.weeklyTime = weeklyTime;
    }

    public short getStrWeek() {
        return strWeek;
    }

    public void setStrWeek(short strWeek) {
        this.strWeek = strWeek;
    }

    public short getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(short dailyTime) {
        this.dailyTime = dailyTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstWkpTransLabTimePK != null ? kshstWkpTransLabTimePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstWkpTransLabTime)) {
            return false;
        }
        KshstWkpTransLabTime other = (KshstWkpTransLabTime) object;
        if ((this.kshstWkpTransLabTimePK == null && other.kshstWkpTransLabTimePK != null) || (this.kshstWkpTransLabTimePK != null && !this.kshstWkpTransLabTimePK.equals(other.kshstWkpTransLabTimePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstWkpTransLabTime[ kshstWkpTransLabTimePK=" + kshstWkpTransLabTimePK + " ]";
    }
    
}
