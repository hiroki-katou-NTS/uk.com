package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import lombok.Setter;

/**
 * 終了日の翌日情報WORK（特別休暇で使用）
 */
@Getter
@Setter
public class NextDayAfterPeriodEndWork {
	/** 終了日の期間フラグ*/
	private boolean periodEndAtr;
	/** 終了日の翌日期間フラグ*/
	private boolean  nextPeriodEndAtr;

	/**
	 * コンストラクタ
	 */
	public NextDayAfterPeriodEndWork(){
		this.periodEndAtr = false;
		this.nextPeriodEndAtr = false;
	}
	/**
	 * ファクトリー
	 * @param periodEndAtr 終了日の期間フラグ
	 * @param nextPeriodEndAtr 終了日の翌日期間フラグ
	 * @return 終了日の翌日情報WORK
	 */
	public static NextDayAfterPeriodEndWork of(
			boolean periodEndAtr,
			boolean  nextPeriodEndAtr) {

		NextDayAfterPeriodEndWork domain = new NextDayAfterPeriodEndWork();
		domain.periodEndAtr = periodEndAtr;
		domain.nextPeriodEndAtr = nextPeriodEndAtr;
		return domain;
	}
}
