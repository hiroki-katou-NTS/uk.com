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
@Table(name = "KSHST_SHA_TRANS_LAB_TIME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstShaTransLabTime.findAll", query = "SELECT k FROM KshstShaTransLabTime k"),
    @NamedQuery(name = "KshstShaTransLabTime.findByInsDate", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstShaTransLabTime.findByInsCcd", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstShaTransLabTime.findByInsScd", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstShaTransLabTime.findByInsPg", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstShaTransLabTime.findByUpdDate", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstShaTransLabTime.findByUpdCcd", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstShaTransLabTime.findByUpdScd", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstShaTransLabTime.findByUpdPg", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstShaTransLabTime.findByExclusVer", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstShaTransLabTime.findByCid", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.kshstShaTransLabTimePK.cid = :cid"),
    @NamedQuery(name = "KshstShaTransLabTime.findBySid", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.kshstShaTransLabTimePK.sid = :sid"),
    @NamedQuery(name = "KshstShaTransLabTime.findByWeeklyTime", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.weeklyTime = :weeklyTime"),
    @NamedQuery(name = "KshstShaTransLabTime.findByWeekStr", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.weekStr = :weekStr"),
    @NamedQuery(name = "KshstShaTransLabTime.findByDailyTime", query = "SELECT k FROM KshstShaTransLabTime k WHERE k.dailyTime = :dailyTime")})
public class KshstShaTransLabTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstShaTransLabTimePK kshstShaTransLabTimePK;
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
    @Column(name = "WEEK_STR")
    private short weekStr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAILY_TIME")
    private short dailyTime;

    public KshstShaTransLabTime() {
    }

    public KshstShaTransLabTime(KshstShaTransLabTimePK kshstShaTransLabTimePK) {
        this.kshstShaTransLabTimePK = kshstShaTransLabTimePK;
    }

    public KshstShaTransLabTime(KshstShaTransLabTimePK kshstShaTransLabTimePK, int exclusVer, int weeklyTime, short weekStr, short dailyTime) {
        this.kshstShaTransLabTimePK = kshstShaTransLabTimePK;
        this.exclusVer = exclusVer;
        this.weeklyTime = weeklyTime;
        this.weekStr = weekStr;
        this.dailyTime = dailyTime;
    }

    public KshstShaTransLabTime(String cid, String sid) {
        this.kshstShaTransLabTimePK = new KshstShaTransLabTimePK(cid, sid);
    }

    public KshstShaTransLabTimePK getKshstShaTransLabTimePK() {
        return kshstShaTransLabTimePK;
    }

    public void setKshstShaTransLabTimePK(KshstShaTransLabTimePK kshstShaTransLabTimePK) {
        this.kshstShaTransLabTimePK = kshstShaTransLabTimePK;
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

    public short getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(short weekStr) {
        this.weekStr = weekStr;
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
        hash += (kshstShaTransLabTimePK != null ? kshstShaTransLabTimePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstShaTransLabTime)) {
            return false;
        }
        KshstShaTransLabTime other = (KshstShaTransLabTime) object;
        if ((this.kshstShaTransLabTimePK == null && other.kshstShaTransLabTimePK != null) || (this.kshstShaTransLabTimePK != null && !this.kshstShaTransLabTimePK.equals(other.kshstShaTransLabTimePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstShaTransLabTime[ kshstShaTransLabTimePK=" + kshstShaTransLabTimePK + " ]";
    }
    
}
