package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 積立年休付与残数履歴データ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_RVSLEA_REMAIN_HIST")
public class KrcdtReserveLeaveRemainHist extends UkJpaEntity {

	@Id
	@Column(name = "RVSLEA_ID")
	public String rvsLeaId;

	@Column(name = "SID")
	public String sid;

	@Column(name = "CID")
	public String cid;

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

	@Column(name = "USED_DAYS")
	public double usedDays;

	@Column(name = "OVER_LIMIT_DAYS")
	@Basic(optional = true)
	public Double overLimitDays;

	@Column(name = "REMAINING_DAYS")
	public double remainingDays;

	// 年月
	@Column(name = "YM")
	public Integer yearMonth;

	// 締めID
	@Column(name = "CLOSURE_ID")
	public Integer closureId;

	// 締め日.日
	@Column(name = "CLOSURE_DAY")
	public Integer closeDay;

	// 締め日.末日とする
	@Column(name = "IS_LAST_DAY")
	public Integer isLastDay;

	public static KrcdtReserveLeaveRemainHist fromDomain(ReserveLeaveGrantRemainHistoryData domain, String cid) {
		return new KrcdtReserveLeaveRemainHist(domain.getRsvLeaID(), domain.getEmployeeId(), cid, domain.getGrantDate(),
				domain.getDeadline(), domain.getExpirationStatus().value, domain.getRegisterType().value,
				domain.getDetails().getGrantNumber().v(), domain.getDetails().getUsedNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getOverLimitDays().isPresent()
						? domain.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
				domain.getDetails().getRemainingNumber().v(), domain.getYearMonth().v(), domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(), domain.getClosureDate().getLastDayOfMonth() ? 1 : 0);
	}

	public ReserveLeaveGrantRemainHistoryData toDomain() {
		return new ReserveLeaveGrantRemainHistoryData(this.rvsLeaId, this.sid, this.grantDate, this.deadline,
				this.expStatus, this.registerType, this.grantDays, this.usedDays, this.overLimitDays,
				this.remainingDays, new YearMonth(this.yearMonth), this.closureId,
				new ClosureDate(this.closeDay, this.isLastDay == 1));
	}

	public KrcdtReserveLeaveRemainHist(String rvsLeaId, String sid, String cid, GeneralDate grantDate,
			GeneralDate deadline, int expStatus, int registerType, double grantDays, double usedDays,
			Double overLimitDays, double remainingDays, Integer yearMonth, Integer closureId, Integer closeDay,
			int isLastDay) {
		super();
		this.rvsLeaId = rvsLeaId;
		this.sid = sid;
		this.cid = cid;
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expStatus = expStatus;
		this.registerType = registerType;
		this.grantDays = grantDays;
		this.usedDays = usedDays;
		this.overLimitDays = overLimitDays;
		this.remainingDays = remainingDays;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closeDay = closeDay;
		this.isLastDay = isLastDay;
	}

	@Override
	protected Object getKey() {
		return this.rvsLeaId;
	}

}
