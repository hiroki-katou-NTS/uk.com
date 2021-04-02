package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import lombok.Setter;

/**
 * 終了日の翌日情報WORK）
 */
@Getter
@Setter
public class RsvLeaNextDayAfterPeriodEndWork {

	/** 終了日の期間フラグ*/
	private boolean periodEndAtr;
	/** 終了日の翌日期間フラグ*/
	private boolean  nextPeriodEndAtr;

	/**
	 * コンストラクタ　AnnualLeaveRemainingNumber
	 */
	public RsvLeaNextDayAfterPeriodEndWork(){
		this.periodEndAtr = false;
		this.nextPeriodEndAtr = false;
	}
	/**
	 * ファクトリー
	 * @param periodEndAtr 終了日の期間フラグ
	 * @param nextPeriodEndAtr 終了日の翌日期間フラグ
	 * @return 終了日の翌日情報WORK
	 */
	public static RsvLeaNextDayAfterPeriodEndWork of(
			boolean periodEndAtr,
			boolean  nextPeriodEndAtr) {

		RsvLeaNextDayAfterPeriodEndWork domain = new RsvLeaNextDayAfterPeriodEndWork();
		domain.periodEndAtr = periodEndAtr;
		domain.nextPeriodEndAtr = nextPeriodEndAtr;
		return domain;
	}
}
