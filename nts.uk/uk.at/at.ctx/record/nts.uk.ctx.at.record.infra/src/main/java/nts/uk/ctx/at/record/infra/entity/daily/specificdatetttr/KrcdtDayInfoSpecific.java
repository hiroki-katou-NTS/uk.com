/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KRCDT_DAY_INFO_SPECIFIC")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findAll", query = "SELECT k FROM KrcdtDayInfoSpecific k"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByInsDate", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByInsCcd", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByInsScd", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByInsPg", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByUpdDate", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByUpdCcd", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByUpdScd", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByUpdPg", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByExclusVer", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findBySid", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.krcdtDaiSpeDayClaPK.sid = :sid"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByYmd", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.krcdtDaiSpeDayClaPK.ymd = :ymd"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findBySpeDayItemNo", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.speDayItemNo = :speDayItemNo"),
//    @NamedQuery(name = "KrcdtDayInfoSpecific.findByTobeSpeDay", query = "SELECT k FROM KrcdtDayInfoSpecific k WHERE k.tobeSpeDay = :tobeSpeDay")
	})
public class KrcdtDayInfoSpecific extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public KrcdtDaiSpeDayClaPK krcdtDaiSpeDayClaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOBE_SPE_DAY")
    public int tobeSpeDay;

    public KrcdtDayInfoSpecific() {
    }

    public KrcdtDayInfoSpecific(KrcdtDaiSpeDayClaPK krcdtDaiSpeDayClaPK) {
        this.krcdtDaiSpeDayClaPK = krcdtDaiSpeDayClaPK;
    }

    public KrcdtDayInfoSpecific(KrcdtDaiSpeDayClaPK krcdtDaiSpeDayClaPK, int tobeSpeDay) {
        this.krcdtDaiSpeDayClaPK = krcdtDaiSpeDayClaPK;
        this.tobeSpeDay = tobeSpeDay;
    }

    public KrcdtDayInfoSpecific(String sid, GeneralDate ymd, int speDayItemNo) {
        this.krcdtDaiSpeDayClaPK = new KrcdtDaiSpeDayClaPK(sid, ymd, speDayItemNo);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcdtDaiSpeDayClaPK != null ? krcdtDaiSpeDayClaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcdtDayInfoSpecific)) {
            return false;
        }
        KrcdtDayInfoSpecific other = (KrcdtDayInfoSpecific) object;
        if ((this.krcdtDaiSpeDayClaPK == null && other.krcdtDaiSpeDayClaPK != null) || (this.krcdtDaiSpeDayClaPK != null && !this.krcdtDaiSpeDayClaPK.equals(other.krcdtDaiSpeDayClaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcdtDayInfoSpecific[ krcdtDaiSpeDayClaPK=" + krcdtDaiSpeDayClaPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.krcdtDaiSpeDayClaPK;
	}
    
}
