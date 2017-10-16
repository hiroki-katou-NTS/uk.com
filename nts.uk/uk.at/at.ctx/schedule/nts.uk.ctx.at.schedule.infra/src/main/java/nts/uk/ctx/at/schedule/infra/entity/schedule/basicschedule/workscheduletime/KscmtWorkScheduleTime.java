/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletime;

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
@Table(name = "KSCMT_WORK_SCHEDULE_TIME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KscmtWorkScheduleTime.findAll", query = "SELECT k FROM KscmtWorkScheduleTime k"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByInsDate", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByInsCcd", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByInsScd", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByInsPg", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByUpdDate", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByUpdCcd", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByUpdScd", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByUpdPg", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByExclusVer", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findBySid", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.kscmtWorkScheduleTimePK.sid = :sid"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByYmd", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.kscmtWorkScheduleTimePK.ymd = :ymd"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByBreakTime", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.breakTime = :breakTime"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByWorkingTime", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.workingTime = :workingTime"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByWeekdayTime", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.weekdayTime = :weekdayTime"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByPrescribedTime", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.prescribedTime = :prescribedTime"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByTotalLaborTime", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.totalLaborTime = :totalLaborTime"),
    @NamedQuery(name = "KscmtWorkScheduleTime.findByChildCareTime", query = "SELECT k FROM KscmtWorkScheduleTime k WHERE k.childCareTime = :childCareTime")})
public class KscmtWorkScheduleTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KscmtWorkScheduleTimePK kscmtWorkScheduleTimePK;
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;
    @Size(max = 12)
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
    @Size(max = 12)
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
    @Column(name = "BREAK_TIME")
    private short breakTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORKING_TIME")
    private short workingTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WEEKDAY_TIME")
    private short weekdayTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRESCRIBED_TIME")
    private short prescribedTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_LABOR_TIME")
    private short totalLaborTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_CARE_TIME")
    private short childCareTime;

    public KscmtWorkScheduleTime() {
    }

    public KscmtWorkScheduleTime(KscmtWorkScheduleTimePK kscmtWorkScheduleTimePK) {
        this.kscmtWorkScheduleTimePK = kscmtWorkScheduleTimePK;
    }

    public KscmtWorkScheduleTime(KscmtWorkScheduleTimePK kscmtWorkScheduleTimePK, int exclusVer, short breakTime, short workingTime, short weekdayTime, short prescribedTime, short totalLaborTime, short childCareTime) {
        this.kscmtWorkScheduleTimePK = kscmtWorkScheduleTimePK;
        this.exclusVer = exclusVer;
        this.breakTime = breakTime;
        this.workingTime = workingTime;
        this.weekdayTime = weekdayTime;
        this.prescribedTime = prescribedTime;
        this.totalLaborTime = totalLaborTime;
        this.childCareTime = childCareTime;
    }

    public KscmtWorkScheduleTime(String sid, Date ymd) {
        this.kscmtWorkScheduleTimePK = new KscmtWorkScheduleTimePK(sid, ymd);
    }

    public KscmtWorkScheduleTimePK getKscmtWorkScheduleTimePK() {
        return kscmtWorkScheduleTimePK;
    }

    public void setKscmtWorkScheduleTimePK(KscmtWorkScheduleTimePK kscmtWorkScheduleTimePK) {
        this.kscmtWorkScheduleTimePK = kscmtWorkScheduleTimePK;
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

    public short getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(short breakTime) {
        this.breakTime = breakTime;
    }

    public short getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(short workingTime) {
        this.workingTime = workingTime;
    }

    public short getWeekdayTime() {
        return weekdayTime;
    }

    public void setWeekdayTime(short weekdayTime) {
        this.weekdayTime = weekdayTime;
    }

    public short getPrescribedTime() {
        return prescribedTime;
    }

    public void setPrescribedTime(short prescribedTime) {
        this.prescribedTime = prescribedTime;
    }

    public short getTotalLaborTime() {
        return totalLaborTime;
    }

    public void setTotalLaborTime(short totalLaborTime) {
        this.totalLaborTime = totalLaborTime;
    }

    public short getChildCareTime() {
        return childCareTime;
    }

    public void setChildCareTime(short childCareTime) {
        this.childCareTime = childCareTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtWorkScheduleTimePK != null ? kscmtWorkScheduleTimePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscmtWorkScheduleTime)) {
            return false;
        }
        KscmtWorkScheduleTime other = (KscmtWorkScheduleTime) object;
        if ((this.kscmtWorkScheduleTimePK == null && other.kscmtWorkScheduleTimePK != null) || (this.kscmtWorkScheduleTimePK != null && !this.kscmtWorkScheduleTimePK.equals(other.kscmtWorkScheduleTimePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KscmtWorkScheduleTime[ kscmtWorkScheduleTimePK=" + kscmtWorkScheduleTimePK + " ]";
    }
    
}
