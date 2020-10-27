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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KRCDT_DAI_SPE_DAY_CLA")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findAll", query = "SELECT k FROM KrcdtDaiSpeDayCla k"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByInsDate", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByInsCcd", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByInsScd", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByInsPg", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByUpdDate", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByUpdCcd", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByUpdScd", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByUpdPg", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByExclusVer", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findBySid", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.krcdtDaiSpeDayClaPK.sid = :sid"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByYmd", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.krcdtDaiSpeDayClaPK.ymd = :ymd"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findBySpeDayItemNo", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.speDayItemNo = :speDayItemNo"),
//    @NamedQuery(name = "KrcdtDaiSpeDayCla.findByTobeSpeDay", query = "SELECT k FROM KrcdtDaiSpeDayCla k WHERE k.tobeSpeDay = :tobeSpeDay")
	})
public class KrcdtDaiSpeDayCla extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public KrcdtDaiSpeDayClaPK krcdtDaiSpeDayClaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOBE_SPE_DAY")
    public int tobeSpeDay;

    public KrcdtDaiSpeDayCla() {
    }

    public KrcdtDaiSpeDayCla(KrcdtDaiSpeDayClaPK krcdtDaiSpeDayClaPK) {
        this.krcdtDaiSpeDayClaPK = krcdtDaiSpeDayClaPK;
    }

    public KrcdtDaiSpeDayCla(KrcdtDaiSpeDayClaPK krcdtDaiSpeDayClaPK, int tobeSpeDay) {
        this.krcdtDaiSpeDayClaPK = krcdtDaiSpeDayClaPK;
        this.tobeSpeDay = tobeSpeDay;
    }

    public KrcdtDaiSpeDayCla(String sid, GeneralDate ymd, int speDayItemNo) {
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
        if (!(object instanceof KrcdtDaiSpeDayCla)) {
            return false;
        }
        KrcdtDaiSpeDayCla other = (KrcdtDaiSpeDayCla) object;
        if ((this.krcdtDaiSpeDayClaPK == null && other.krcdtDaiSpeDayClaPK != null) || (this.krcdtDaiSpeDayClaPK != null && !this.krcdtDaiSpeDayClaPK.equals(other.krcdtDaiSpeDayClaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcdtDaiSpeDayCla[ krcdtDaiSpeDayClaPK=" + krcdtDaiSpeDayClaPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.krcdtDaiSpeDayClaPK;
	}
    
}
