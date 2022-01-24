package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 年休集計期間WORK
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AggregatePeriodWork {

	/** 期間 */
	private DatePeriod period;

	/**消滅 */
	private AnnualLeaveLapsedWork lapsedAtr;

	/**付与 */
	private AnnualLeaveGrantWork grantWork;

	/**終了日 */
	private AnnualNextDayAfterPeriodEndWork endWork;

	/**
	 * コンストラクタ
	 */
	public AggregatePeriodWork(){

		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.lapsedAtr = new AnnualLeaveLapsedWork(false);
		this.grantWork = new AnnualLeaveGrantWork();
		this.endWork = new AnnualNextDayAfterPeriodEndWork();

	}

	public AggregatePeriodWork(DatePeriod period, AnnualLeaveLapsedWork lapsedAtr, AnnualLeaveGrantWork grantWork,
			AnnualNextDayAfterPeriodEndWork endWork, GrantBeforeAfterAtr grantPeriodAtr) {
		super();
		this.period = period;
		this.lapsedAtr = lapsedAtr;
		this.grantWork = grantWork;
		this.endWork = endWork;
		this.grantWork.setGrantPeriodAtr(grantPeriodAtr);
	}


	/**
	 * 次の期間の付与前後を判断
	 * @param periodWorkList
	 * @return
	 */
	public GrantBeforeAfterAtr judgeNextGrantPeriodAtr(List<AggregatePeriodWork> periodWorkList){
		if(this.endWork.judgeNextPeriodEndAtr() || (periodWorkList.size()-1) < (periodWorkList.indexOf(this)+1)){
			return this.getGrantWork().judgeGrantPeriodAtr();
		}
		
		return  periodWorkList.get(periodWorkList.indexOf(this)+1).getGrantWork().judgeGrantPeriodAtr();
	}
}
