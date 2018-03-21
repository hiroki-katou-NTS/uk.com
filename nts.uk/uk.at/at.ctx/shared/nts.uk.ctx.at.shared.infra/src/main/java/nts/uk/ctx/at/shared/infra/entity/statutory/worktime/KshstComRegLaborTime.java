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
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KSHST_COM_REG_LABOR_TIME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstComRegLaborTime.findAll", query = "SELECT k FROM KshstComRegLaborTime k"),
    @NamedQuery(name = "KshstComRegLaborTime.findByInsDate", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstComRegLaborTime.findByInsCcd", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstComRegLaborTime.findByInsScd", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstComRegLaborTime.findByInsPg", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstComRegLaborTime.findByUpdDate", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstComRegLaborTime.findByUpdCcd", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstComRegLaborTime.findByUpdScd", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstComRegLaborTime.findByUpdPg", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstComRegLaborTime.findByExclusVer", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstComRegLaborTime.findByCid", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.cid = :cid"),
    @NamedQuery(name = "KshstComRegLaborTime.findByWeeklyTime", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.weeklyTime = :weeklyTime"),
    @NamedQuery(name = "KshstComRegLaborTime.findByWeekStr", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.weekStr = :weekStr"),
    @NamedQuery(name = "KshstComRegLaborTime.findByDailyTime", query = "SELECT k FROM KshstComRegLaborTime k WHERE k.dailyTime = :dailyTime")})
public class KshstComRegLaborTime implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
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
    private int dailyTime;

    public KshstComRegLaborTime() {
    }

    public KshstComRegLaborTime(String cid) {
        this.cid = cid;
    }

    public KshstComRegLaborTime(String cid, int exclusVer, int weeklyTime, short weekStr, int dailyTime) {
        this.cid = cid;
        this.exclusVer = exclusVer;
        this.weeklyTime = weeklyTime;
        this.weekStr = weekStr;
        this.dailyTime = dailyTime;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public int getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(int dailyTime) {
        this.dailyTime = dailyTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstComRegLaborTime)) {
            return false;
        }
        KshstComRegLaborTime other = (KshstComRegLaborTime) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KshstComRegLaborTime[ cid=" + cid + " ]";
    }
    
}
