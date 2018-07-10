package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author HungTT - 年休付与残数履歴データ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_ANNLEA_REMAIN_HIST")
public class KrcdtAnnLeaRemainHist extends UkJpaEntity {

	@Id
	@Column(name = "ANNLEAV_ID")
	public String annLeavID;

	@Column(name = "CID")
	public String cid;

	@Column(name = "SID")
	public String sid;

	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;

	@Column(name = "DEADLINE")
	public GeneralDate deadline;

	@Column(name = "EXP_STATUS")
	public int expStatus;

	@Column(name = "REGISTER_TYPE")
	public int registerType;

	@Column(name = "GRANT_DAYS")
	public double grantDays;

	@Column(name = "GRANT_MINUTES")
	@Basic(optional = true)
	public Integer grantMinutes;

	@Column(name = "USED_DAYS")
	public double usedDays;

	@Column(name = "USED_MINUTES")
	@Basic(optional = true)
	public Integer usedMinutes;

	@Column(name = "STOWAGE_DAYS")
	@Basic(optional = true)
	public Double stowageDays;

	@Column(name = "REMAINING_DAYS")
	public double remainingDays;

	@Column(name = "REMAINING_MINUTES")
	@Basic(optional = true)
	public Integer remaningMinutes;

	@Column(name = "USED_PERCENT")
	public double usedPercent;

	@Column(name = "PRESCRIBED_DAYS")
	@Basic(optional = true)
	public Double prescribedDays;

	@Column(name = "DEDUCTED_DAYS")
	@Basic(optional = true)
	public Double deductedDays;

	@Column(name = "WORKING_DAYS")
	@Basic(optional = true)
	public Double workingDays;

	// 年月
	@Column(name = "YM")
	@Basic(optional = false)
	public Integer yearMonth;

	// 締めID
	@Column(name = "CLOSURE_ID")
	@Basic(optional = false)
	public Integer closureId;

	// 締め日.日
	@Column(name = "CLOSURE_DAY")
	@Basic(optional = false)
	public Integer closeDay;

	// 締め日.末日とする
	@Column(name = "IS_LAST_DAY")
	@Basic(optional = false)
	public Integer isLastDay;

	@Override
	protected Object getKey() {
		return annLeavID;
	}

	public KrcdtAnnLeaRemainHist(String annLeavID, String cid, String sid, GeneralDate grantDate, GeneralDate deadline,
			int expStatus, int registerType, double grantDays, Integer grantMinutes, double usedDays,
			Integer usedMinutes, Double stowageDays, double remainingDays, Integer remaningMinutes, double usedPercent,
			Double prescribedDays, Double deductedDays, Double workingDays, Integer yearMonth, int closureId,
			Integer closeDay, Integer isLastDay) {
		super();
		this.annLeavID = annLeavID;
		this.cid = cid;
		this.sid = sid;
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expStatus = expStatus;
		this.registerType = registerType;
		this.grantDays = grantDays;
		this.grantMinutes = grantMinutes;
		this.usedDays = usedDays;
		this.usedMinutes = usedMinutes;
		this.stowageDays = stowageDays;
		this.remainingDays = remainingDays;
		this.remaningMinutes = remaningMinutes;
		this.usedPercent = usedPercent;
		this.prescribedDays = prescribedDays;
		this.deductedDays = deductedDays;
		this.workingDays = workingDays;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closeDay = closeDay;
		this.isLastDay = isLastDay;
	}

	public static KrcdtAnnLeaRemainHist fromDomain(AnnualLeaveRemainingHistory domain) {
		return new KrcdtAnnLeaRemainHist(domain.getAnnLeavID(), domain.getCid(), domain.getEmployeeId(),
				domain.getGrantDate(), domain.getDeadline(), domain.getExpirationStatus().value,
				domain.getRegisterType().value, domain.getDetails().getGrantNumber().getDays().v(),
				domain.getDetails().getGrantNumber().getMinutes().isPresent()
						? domain.getDetails().getGrantNumber().getMinutes().get().v() : null,
				domain.getDetails().getUsedNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getMinutes().isPresent()
						? domain.getDetails().getUsedNumber().getMinutes().get().v() : null,
				domain.getDetails().getUsedNumber().getStowageDays().isPresent()
						? domain.getDetails().getUsedNumber().getStowageDays().get().v() : null,
				domain.getDetails().getRemainingNumber().getDays().v(),
				domain.getDetails().getRemainingNumber().getMinutes().isPresent()
						? domain.getDetails().getRemainingNumber().getMinutes().get().v() : null,
				domain.getDetails().getUsedPercent().v().doubleValue(),
				domain.getAnnualLeaveConditionInfo().isPresent()
						? domain.getAnnualLeaveConditionInfo().get().getPrescribedDays().v() : null,
				domain.getAnnualLeaveConditionInfo().isPresent()
						? domain.getAnnualLeaveConditionInfo().get().getDeductedDays().v() : null,
				domain.getAnnualLeaveConditionInfo().isPresent()
						? domain.getAnnualLeaveConditionInfo().get().getWorkingDays().v() : null,
				domain.getYearMonth().v(), domain.getClosureId().value, domain.getClosureDate().getClosureDay().v(),
				domain.getClosureDate().getLastDayOfMonth() ? 1 : 0);
	}

	public AnnualLeaveRemainingHistory toDomain() {
		return new AnnualLeaveRemainingHistory(this.annLeavID, this.cid, this.sid, this.grantDate, this.deadline,
				this.expStatus, this.registerType, this.grantDays, this.grantMinutes, this.usedDays, this.usedMinutes,
				this.stowageDays, this.remainingDays, this.remaningMinutes, this.usedPercent, this.prescribedDays,
				this.deductedDays, this.workingDays, this.yearMonth, this.closureId, this.closeDay,
				this.isLastDay == 1);
	}

}
