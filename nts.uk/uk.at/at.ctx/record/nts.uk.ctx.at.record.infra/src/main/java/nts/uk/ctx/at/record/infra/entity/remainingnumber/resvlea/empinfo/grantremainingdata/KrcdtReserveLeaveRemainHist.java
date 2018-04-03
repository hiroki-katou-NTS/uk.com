package nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.KrcmtReverseLeaRemain;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * 
 * @author HungTT - 積立年休付与残数履歴データ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_RVSLEA_REMAIN_HIST")
public class KrcdtReserveLeaveRemainHist extends KrcmtReverseLeaRemain {

	// 年月
	@Column(name = "YM")
	public YearMonth yearMonth;

	// 締めID
	@Column(name = "CLOSURE_ID")
	public Integer closureId;

	// 締め日.日
	@Column(name = "CLOSURE_DAY")
	public Integer closeDay;

	// 締め日.末日とする
	@Column(name = "IS_LAST_DAY")
	public Integer isLastDay;

	public KrcdtReserveLeaveRemainHist(YearMonth yearMonth, Integer closureId, Integer closeDay, Integer isLastDay) {
		super();
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closeDay = closeDay;
		this.isLastDay = isLastDay;
	}

	public static KrcdtReserveLeaveRemainHist fromDomain(ReserveLeaveGrantRemainHistoryData domain) {
		return new KrcdtReserveLeaveRemainHist(domain.getYearMonth(), domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(), domain.getClosureDate().getLastDayOfMonth() ? 1 : 0);
	}

	public ReserveLeaveGrantRemainHistoryData toDomain() {
		return new ReserveLeaveGrantRemainHistoryData(this.yearMonth, this.closureId,
				new ClosureDate(this.closeDay, this.isLastDay == 1));
	}

}
