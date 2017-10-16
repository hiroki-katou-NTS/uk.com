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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Embeddable
public class KscmtWorkScheduleTimePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "SID")
    private String sid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YMD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ymd;

    public KscmtWorkScheduleTimePK() {
    }

    public KscmtWorkScheduleTimePK(String sid, Date ymd) {
        this.sid = sid;
        this.ymd = ymd;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getYmd() {
        return ymd;
    }

    public void setYmd(Date ymd) {
        this.ymd = ymd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (ymd != null ? ymd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscmtWorkScheduleTimePK)) {
            return false;
        }
        KscmtWorkScheduleTimePK other = (KscmtWorkScheduleTimePK) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if ((this.ymd == null && other.ymd != null) || (this.ymd != null && !this.ymd.equals(other.ymd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KscmtWorkScheduleTimePK[ sid=" + sid + ", ymd=" + ymd + " ]";
    }
    
}
