/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.daily.calculationattribute;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "KRCST_DAI_CALCULATION_SET")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcstDaiCalculationSet.findAll", query = "SELECT k FROM KrcstDaiCalculationSet k"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByInsDate", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByInsCcd", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByInsScd", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByInsPg", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByUpdDate", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByUpdCcd", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByUpdScd", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByUpdPg", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByExclusVer", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findBySid", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.krcstDaiCalculationSetPK.sid = :sid"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByYmd", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.krcstDaiCalculationSetPK.ymd = :ymd"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByFlexExcessTimeId", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.flexExcessTimeId = :flexExcessTimeId"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByBonusPayNormalCalSet", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.bonusPayNormalCalSet = :bonusPayNormalCalSet"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByBonusPaySpeCalSet", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.bonusPaySpeCalSet = :bonusPaySpeCalSet"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByHolWorkTimeId", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.holWorkTimeId = :holWorkTimeId"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByOverTimeWorkId", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.overTimeWorkId = :overTimeWorkId"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByLeaveLateSet", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.leaveLateSet = :leaveLateSet"),
//    @NamedQuery(name = "KrcstDaiCalculationSet.findByLeaveEarlySet", query = "SELECT k FROM KrcstDaiCalculationSet k WHERE k.leaveEarlySet = :leaveEarlySet")
	})
public class KrcstDaiCalculationSet extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public KrcstDaiCalculationSetPK krcstDaiCalculationSetPK;
    
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "FLEX_EXCESS_TIME_ID")
    public String flexExcessTimeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BONUS_PAY_NORMAL_CAL_SET")
    public int bonusPayNormalCalSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BONUS_PAY_SPE_CAL_SET")
    public int bonusPaySpeCalSet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "HOL_WORK_TIME_ID")
    public String holWorkTimeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "OVER_TIME_WORK_ID")
    public String overTimeWorkId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEAVE_LATE_SET")
    public int leaveLateSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEAVE_EARLY_SET")
    public int leaveEarlySet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIVERGENCE_TIME")
    public int divergenceTime;

    public KrcstDaiCalculationSet() {
    }

    public KrcstDaiCalculationSet(KrcstDaiCalculationSetPK krcstDaiCalculationSetPK) {
        this.krcstDaiCalculationSetPK = krcstDaiCalculationSetPK;
    }

    public KrcstDaiCalculationSet(KrcstDaiCalculationSetPK krcstDaiCalculationSetPK, String flexExcessTimeId, int bonusPayNormalCalSet, 
    		int bonusPaySpeCalSet, String holWorkTimeId, String overTimeWorkId, int leaveLateSet, int leaveEarlySet, int divergenceTime) {
        this.krcstDaiCalculationSetPK = krcstDaiCalculationSetPK;
        this.flexExcessTimeId = flexExcessTimeId;
        this.bonusPayNormalCalSet = bonusPayNormalCalSet;
        this.bonusPaySpeCalSet = bonusPaySpeCalSet;
        this.holWorkTimeId = holWorkTimeId;
        this.overTimeWorkId = overTimeWorkId;
        this.leaveLateSet = leaveLateSet;
        this.leaveEarlySet = leaveEarlySet;
        this.divergenceTime = divergenceTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcstDaiCalculationSetPK != null ? krcstDaiCalculationSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstDaiCalculationSet)) {
            return false;
        }
        KrcstDaiCalculationSet other = (KrcstDaiCalculationSet) object;
        if ((this.krcstDaiCalculationSetPK == null && other.krcstDaiCalculationSetPK != null) || (this.krcstDaiCalculationSetPK != null && !this.krcstDaiCalculationSetPK.equals(other.krcstDaiCalculationSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstDaiCalculationSet[ krcstDaiCalculationSetPK=" + krcstDaiCalculationSetPK + " ]";
    }

	@Override
	protected Object getKey() {
		return krcstDaiCalculationSetPK;
	}
    
}
