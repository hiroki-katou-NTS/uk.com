package nts.uk.ctx.at.shared.dom.worktime.service;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
/**
 * 就業時間帯の勤務形態
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.基本情報.関数アルゴリズム.就業時間帯勤務区分.勤務形態を取得する.クラス.就業時間帯の勤務形態
 *
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum WorkTimeForm {

	/**固定勤務 */
	FIXED(0, "固定勤務"),

	/**	フレックス勤務 */
	FLEX(1, "フレックス勤務"),

	/**流動勤務	 */
	FLOW(2,"流動勤務"),

	/**	時差勤務 */
	TIMEDIFFERENCE(3, "時差勤務");


	public final Integer value;
	public final String name;


	/**
	 * 勤務形態と就業時間帯の設定方法から取得する
	 * @param form 勤務形態区分
	 * @param method 就業時間帯の設定方法
	 * @return 就業時間帯の勤務形態
	 */
	public static WorkTimeForm from(WorkTimeDailyAtr form, WorkTimeMethodSet method) {

		// 勤務形態 -> フレックス勤務用
		if (form == WorkTimeDailyAtr.FLEX_WORK) {
			// フレックス勤務
			return WorkTimeForm.FLEX;
		}

		// 勤務形態 -> 通常勤務・変形労働用
		switch ( method ) {
			case FIXED_WORK:	// 就業時間帯の設定方法 -> 固定勤務
				// 固定勤務
				return WorkTimeForm.FIXED;
			case DIFFTIME_WORK:	// 就業時間帯の設定方法 -> 時差勤務
				// 時差勤務
				return WorkTimeForm.TIMEDIFFERENCE;
			case FLOW_WORK:		// 就業時間帯の設定方法 -> 流動勤務
				// 流動勤務
				return WorkTimeForm.FLOW;
		}

		throw new RuntimeException("Pattern is failure :" + form + " & " + method);

	}

	
	public boolean isFixed() {
		return FIXED.equals(this);
	}
	
	public boolean isFlex() {
		return FLEX.equals(this);
	}
	
	public boolean isFlow() {
		return FLOW.equals(this);
	}
	
	public boolean isTimedifference() {
		return TIMEDIFFERENCE.equals(this);
	}
}
