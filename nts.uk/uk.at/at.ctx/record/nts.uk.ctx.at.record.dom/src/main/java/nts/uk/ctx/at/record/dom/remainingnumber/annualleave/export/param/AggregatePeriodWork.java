package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.GrantPeriodAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.NextDayAfterPeriodEndWork;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantWork;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveLapsedWork;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

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

//	/**付与前か付与後か */
//	private GrantPeriodAtr grantPeriodAtr;


//	/** 期間終了内 */
//	private boolean dayBeforePeriodEnd;
//	/** 期間終了後翌日 */
//	private boolean nextDayAfterPeriodEnd;
//
//	/** 付与フラグ */
//	private boolean grantAtr;
//	/** 何回目の付与なのか */
//	private int grantNumber = 0;
//	/** 付与後 */
//	private boolean afterGrant;
//
//	/** 消滅フラグ */
//	//private boolean lapsedAtr;
//
//	/** 年休付与 */
//	private Optional<NextAnnualLeaveGrant> annualLeaveGrant;

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
			AnnualNextDayAfterPeriodEndWork endWork, GrantPeriodAtr grantPeriodAtr) {
		super();
		this.period = period;
		this.lapsedAtr = lapsedAtr;
		this.grantWork = grantWork;
		this.endWork = endWork;
		this.grantWork.setGrantPeriodAtr(grantPeriodAtr);
	}

}
