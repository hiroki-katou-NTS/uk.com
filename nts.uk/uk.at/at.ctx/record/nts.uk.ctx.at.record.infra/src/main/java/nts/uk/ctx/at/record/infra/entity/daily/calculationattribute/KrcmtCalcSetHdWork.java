/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.daily.calculationattribute;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KRCMT_CALC_SET_HD_WORK")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findAll", query = "SELECT k FROM KrcmtCalcSetHdWork k"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByInsDate", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByInsCcd", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByInsScd", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByInsPg", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByUpdDate", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByUpdCcd", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByUpdScd", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByUpdPg", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByExclusVer", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByHolWorkTimeId", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.holWorkTimeId = :holWorkTimeId"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByHolWorkTimeCalAtr", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.holWorkTimeCalAtr = :holWorkTimeCalAtr"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByHolWorkTimeLimitSet", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.holWorkTimeLimitSet = :holWorkTimeLimitSet"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByLateNightTimeCalAtr", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.lateNightTimeCalAtr = :lateNightTimeCalAtr"),
//    @NamedQuery(name = "KrcmtCalcSetHdWork.findByLateNightTimeLimitSet", query = "SELECT k FROM KrcmtCalcSetHdWork k WHERE k.lateNightTimeLimitSet = :lateNightTimeLimitSet")
	})
public class KrcmtCalcSetHdWork extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "HOL_WORK_TIME_ID")
    public String holWorkTimeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOL_WORK_TIME_CAL_ATR")
    public int holWorkTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOL_WORK_TIME_LIMIT_SET")
    public int holWorkTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME_CAL_ATR")
    public int lateNightTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME_LIMIT_SET")
    public int lateNightTimeLimitSet;

    public KrcmtCalcSetHdWork() {
    }

    public KrcmtCalcSetHdWork(String holWorkTimeId) {
        this.holWorkTimeId = holWorkTimeId;
    }

    public KrcmtCalcSetHdWork(String holWorkTimeId, int holWorkTimeCalAtr, int holWorkTimeLimitSet, int lateNightTimeCalAtr, int lateNightTimeLimitSet) {
        this.holWorkTimeId = holWorkTimeId;
        this.holWorkTimeCalAtr = holWorkTimeCalAtr;
        this.holWorkTimeLimitSet = holWorkTimeLimitSet;
        this.lateNightTimeCalAtr = lateNightTimeCalAtr;
        this.lateNightTimeLimitSet = lateNightTimeLimitSet;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (holWorkTimeId != null ? holWorkTimeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcmtCalcSetHdWork)) {
            return false;
        }
        KrcmtCalcSetHdWork other = (KrcmtCalcSetHdWork) object;
        if ((this.holWorkTimeId == null && other.holWorkTimeId != null) || (this.holWorkTimeId != null && !this.holWorkTimeId.equals(other.holWorkTimeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcmtCalcSetHdWork[ holWorkTimeId=" + holWorkTimeId + " ]";
    }

	@Override
	protected Object getKey() {
		return holWorkTimeId;
	}
    
}
