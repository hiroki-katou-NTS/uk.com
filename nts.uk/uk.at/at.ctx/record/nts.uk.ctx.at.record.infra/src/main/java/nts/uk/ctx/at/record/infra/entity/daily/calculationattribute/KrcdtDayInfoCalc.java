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

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KRCDT_DAY_INFO_CALC")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcdtDayInfoCalc.findAll", query = "SELECT k FROM KrcdtDayInfoCalc k"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByInsDate", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByInsCcd", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByInsScd", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByInsPg", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByUpdDate", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByUpdCcd", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByUpdScd", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByUpdPg", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByExclusVer", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findBySid", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.krcstDaiCalculationSetPK.sid = :sid"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByYmd", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.krcstDaiCalculationSetPK.ymd = :ymd"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByFlexExcessTimeId", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.flexExcessTimeId = :flexExcessTimeId"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByBonusPayNormalCalSet", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.bonusPayNormalCalSet = :bonusPayNormalCalSet"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByBonusPaySpeCalSet", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.bonusPaySpeCalSet = :bonusPaySpeCalSet"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByHolWorkTimeId", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.holWorkTimeId = :holWorkTimeId"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByOverTimeWorkId", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.overTimeWorkId = :overTimeWorkId"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByLeaveLateSet", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.leaveLateSet = :leaveLateSet"),
//    @NamedQuery(name = "KrcdtDayInfoCalc.findByLeaveEarlySet", query = "SELECT k FROM KrcdtDayInfoCalc k WHERE k.leaveEarlySet = :leaveEarlySet")
	})
public class KrcdtDayInfoCalc extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final JpaEntityMapper<KrcdtDayInfoCalc> MAPPER = new JpaEntityMapper<>(KrcdtDayInfoCalc.class);
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

    public KrcdtDayInfoCalc() {
    }

    public KrcdtDayInfoCalc(KrcstDaiCalculationSetPK krcstDaiCalculationSetPK) {
        this.krcstDaiCalculationSetPK = krcstDaiCalculationSetPK;
    }

    public KrcdtDayInfoCalc(KrcstDaiCalculationSetPK krcstDaiCalculationSetPK, String flexExcessTimeId, int bonusPayNormalCalSet, 
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

	public static KrcdtDayInfoCalc toEntity(CalAttrOfDailyPerformance domain, String flexExcessTimeId, String holWorkTimeId, String overTimeWorkId) {
		return new KrcdtDayInfoCalc(
				new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()),
				flexExcessTimeId,
				BooleanUtils.toInteger(domain.getCalcategory().getRasingSalarySetting().isRaisingSalaryCalcAtr()),
				BooleanUtils.toInteger(domain.getCalcategory().getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr()),
				holWorkTimeId,
				overTimeWorkId,
				BooleanUtils.toInteger(domain.getCalcategory().getLeaveEarlySetting().isLate()),
				BooleanUtils.toInteger(domain.getCalcategory().getLeaveEarlySetting().isLeaveEarly()),
				domain.getCalcategory().getDivergenceTime().getDivergenceTime().value);
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
        if (!(object instanceof KrcdtDayInfoCalc)) {
            return false;
        }
        KrcdtDayInfoCalc other = (KrcdtDayInfoCalc) object;
        if ((this.krcstDaiCalculationSetPK == null && other.krcstDaiCalculationSetPK != null) || (this.krcstDaiCalculationSetPK != null && !this.krcstDaiCalculationSetPK.equals(other.krcstDaiCalculationSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcdtDayInfoCalc[ krcstDaiCalculationSetPK=" + krcstDaiCalculationSetPK + " ]";
    }

	public CalAttrOfDailyPerformance toDomain(KrcmtCalcSetFlex flexCalc, KrcmtCalcSetHdWork holidayCalc, KrcmtCalcSetOverTime overtimeCalc) {
		return new CalAttrOfDailyPerformance(
				this.krcstDaiCalculationSetPK.sid,
				this.krcstDaiCalculationSetPK.ymd,
				flexCalc.toDomain(),
				new AutoCalRaisingSalarySetting(BooleanUtils.toBoolean(this.bonusPaySpeCalSet), BooleanUtils.toBoolean(this.bonusPayNormalCalSet)),
				holidayCalc.toDomain(),
				overtimeCalc.toDomain(),
				new AutoCalcOfLeaveEarlySetting(BooleanUtils.toBoolean(this.leaveLateSet), BooleanUtils.toBoolean(this.leaveEarlySet)),
				new AutoCalcSetOfDivergenceTime(EnumAdaptor.valueOf(this.divergenceTime, DivergenceTimeAttr.class)));
	}

	@Override
	protected Object getKey() {
		return krcstDaiCalculationSetPK;
	}
}
