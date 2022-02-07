package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 処理単位分割日
 * @author shuichu_ishida
 */
@Getter
public class DividedDayEachProcess {

	/** 終了日 */
	private AnnualNextDayAfterPeriodEndWork 	endDay;
	/** 消滅（消滅情報WORK） */
	private AnnualLeaveLapsedWork lapsedWork;
	/** 年月日 */
	private GeneralDate ymd;
	/** 付与（付与情報WORK） */
	private AnnualLeaveGrantWork grantWork;
	/** 付与前か付与後か */
	private GrantBeforeAfterAtr grantPeriodAtr;

//	/** 次回年休付与 */
//	private Optional<NextSpecialLeaveGrant> nextSpecialLeaveGrant;

	/**
	 * コンストラクタ
	 * @param endDay 終了日
	 * @param lapsedWork 消滅（消滅情報WORK）
	 * @param ymd 年月日
	 * @param grantWork 付与（付与情報WORK）
	 * @param grantPeriodAtr 付与前か付与後か
	 * @return  処理単位分割日
	 */
	public DividedDayEachProcess(GeneralDate ymd){

		this.endDay = new AnnualNextDayAfterPeriodEndWork();
		this.lapsedWork = new AnnualLeaveLapsedWork();
		this.ymd = ymd;
		this.grantWork = new AnnualLeaveGrantWork();
//		this.nextSpecialLeaveGrant = Optional.empty();
		this.grantPeriodAtr = GrantBeforeAfterAtr.BEFORE_GRANT;
	}
	
}
