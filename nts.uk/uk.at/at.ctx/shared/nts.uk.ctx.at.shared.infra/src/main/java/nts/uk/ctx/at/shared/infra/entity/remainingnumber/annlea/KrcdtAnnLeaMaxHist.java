package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.MaxMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.MaxTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 年休上限履歴データ
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_ANNLEA_MAX_HIST")
public class KrcdtAnnLeaMaxHist extends UkJpaEntity {

	@Id
	@Column(name = "SID")
	public String sid;

	@Column(name = "CID")
	public String cid;

	@Column(name = "MAX_TIMES")
	@Basic(optional = true)
	public Integer maxTimes;

	@Column(name = "USED_TIMES")
	@Basic(optional = true)
	public Integer usedTimes;

	@Column(name = "REMAINING_TIMES")
	@Basic(optional = true)
	public Integer remainingTimes;

	@Column(name = "MAX_MINUTES")
	@Basic(optional = true)
	public Integer maxMinutes;

	@Column(name = "USED_MINUTES")
	@Basic(optional = true)
	public Integer usedMinutes;

	@Column(name = "REMAINING_MINUTES")
	@Basic(optional = true)
	public Integer remainingMinutes;

	// 年月
	@Column(name = "YM")
	@Basic(optional = false)
	public Integer yearMonth;

	// 締めID
	@Column(name = "CLOSURE_ID")
	@Basic(optional = false)
	public int closureId;

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
		return sid;
	}

	public KrcdtAnnLeaMaxHist(String sid, String cid, Integer maxTimes, Integer usedTimes, Integer remainingTimes,
			Integer maxMinutes, Integer usedMinutes, Integer remainingMinutes, int yearMonth, int closureId,
			Integer closeDay, Integer isLastDay) {
		super();
		this.sid = sid;
		this.cid = cid;
		this.maxTimes = maxTimes;
		this.usedTimes = usedTimes;
		this.remainingTimes = remainingTimes;
		this.maxMinutes = maxMinutes;
		this.usedMinutes = usedMinutes;
		this.remainingMinutes = remainingMinutes;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closeDay = closeDay;
		this.isLastDay = isLastDay;
	}

	public static KrcdtAnnLeaMaxHist fromDomain(AnnualLeaveMaxHistoryData domain) {
		return new KrcdtAnnLeaMaxHist(domain.getEmployeeId(), domain.getCompanyId(),
				domain.getHalfdayAnnualLeaveMax().isPresent()
						? domain.getHalfdayAnnualLeaveMax().get().getMaxTimes().v() : null,
				domain.getHalfdayAnnualLeaveMax().isPresent()
						? domain.getHalfdayAnnualLeaveMax().get().getUsedTimes().v() : null,
				domain.getHalfdayAnnualLeaveMax().isPresent()
						? domain.getHalfdayAnnualLeaveMax().get().getRemainingTimes().v() : null,
				domain.getTimeAnnualLeaveMax().isPresent() ? domain.getTimeAnnualLeaveMax().get().getMaxMinutes().v()
						: null,
				domain.getTimeAnnualLeaveMax().isPresent() ? domain.getTimeAnnualLeaveMax().get().getUsedMinutes().v()
						: null,
				domain.getTimeAnnualLeaveMax().isPresent()
						? domain.getTimeAnnualLeaveMax().get().getRemainingMinutes().v() : null,
				domain.getYearMonth().v(), domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v().intValue(),
				domain.getClosureDate().getLastDayOfMonth() ? 1 : 0);
	}

	public AnnualLeaveMaxHistoryData toDomain() {
		HalfdayAnnualLeaveMax halfdayMax = null;
		if (this.maxTimes != null && this.usedTimes != null && this.remainingTimes != null)
			halfdayMax = new HalfdayAnnualLeaveMax(new MaxTimes(this.maxTimes), new UsedTimes(this.usedTimes),
					new RemainingTimes(this.remainingTimes));
		TimeAnnualLeaveMax timeMax = null;
		if (this.maxMinutes != null && this.usedMinutes != null && this.remainingMinutes != null)
			timeMax = new TimeAnnualLeaveMax(new MaxMinutes(1), new UsedMinutes(1), new RemainingMinutes(0));
		return new AnnualLeaveMaxHistoryData(this.sid, this.cid, halfdayMax, timeMax, new YearMonth(this.yearMonth),
				this.closureId, new ClosureDate(this.closeDay, new Boolean(this.isLastDay == 1)));
	}
}
