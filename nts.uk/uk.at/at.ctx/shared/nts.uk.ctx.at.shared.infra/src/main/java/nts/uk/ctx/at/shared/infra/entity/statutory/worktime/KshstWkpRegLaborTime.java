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
@Table(name = "KSHST_WKP_REG_LABOR_TIME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstWkpRegLaborTime.findAll", query = "SELECT k FROM KshstWkpRegLaborTime k"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByInsDate", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByInsCcd", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByInsScd", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByInsPg", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByUpdDate", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByUpdCcd", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByUpdScd", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByUpdPg", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByExclusVer", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByCid", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.kshstWkpRegLaborTimePK.cid = :cid"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByWkpId", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.kshstWkpRegLaborTimePK.wkpId = :wkpId"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByWeeklyTime", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.weeklyTime = :weeklyTime"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByWeekStr", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.weekStr = :weekStr"),
    @NamedQuery(name = "KshstWkpRegLaborTime.findByDailyTime", query = "SELECT k FROM KshstWkpRegLaborTime k WHERE k.dailyTime = :dailyTime")})
public class KshstWkpRegLaborTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstWkpRegLaborTimePK kshstWkpRegLaborTimePK;
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

    public KshstWkpRegLaborTime() {
    }

    public KshstWkpRegLaborTime(KshstWkpRegLaborTimePK kshstWkpRegLaborTimePK) {
        this.kshstWkpRegLaborTimePK = kshstWkpRegLaborTimePK;
    }

    public KshstWkpRegLaborTime(KshstWkpRegLaborTimePK kshstWkpRegLaborTimePK, int exclusVer, int weeklyTime, short weekStr, short dailyTime) {
        this.kshstWkpRegLaborTimePK = kshstWkpRegLaborTimePK;
        this.exclusVer = exclusVer;
        this.weeklyTime = weeklyTime;
        this.weekStr = weekStr;
        this.dailyTime = dailyTime;
    }

    public KshstWkpRegLaborTime(String cid, String wkpId) {
        this.kshstWkpRegLaborTimePK = new KshstWkpRegLaborTimePK(cid, wkpId);
    }

    public KshstWkpRegLaborTimePK getKshstWkpRegLaborTimePK() {
        return kshstWkpRegLaborTimePK;
    }

    public void setKshstWkpRegLaborTimePK(KshstWkpRegLaborTimePK kshstWkpRegLaborTimePK) {
        this.kshstWkpRegLaborTimePK = kshstWkpRegLaborTimePK;
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
        hash += (kshstWkpRegLaborTimePK != null ? kshstWkpRegLaborTimePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstWkpRegLaborTime)) {
            return false;
        }
        KshstWkpRegLaborTime other = (KshstWkpRegLaborTime) object;
        if ((this.kshstWkpRegLaborTimePK == null && other.kshstWkpRegLaborTimePK != null) || (this.kshstWkpRegLaborTimePK != null && !this.kshstWkpRegLaborTimePK.equals(other.kshstWkpRegLaborTimePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstWkpRegLaborTime[ kshstWkpRegLaborTimePK=" + kshstWkpRegLaborTimePK + " ]";
    }
    
}
