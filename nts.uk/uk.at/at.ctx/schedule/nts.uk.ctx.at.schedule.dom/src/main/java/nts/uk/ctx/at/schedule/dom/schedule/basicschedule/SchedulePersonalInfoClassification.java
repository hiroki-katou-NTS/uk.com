package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;

/**
 * スケジュール個人情報区分
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum SchedulePersonalInfoClassification {
	// 雇用
	EMPLOYMENT(0),
	// 職場
	WORKPLACE(1),
	// 分類
	CLASSIFICATION(2),
	// 職位
	POSITION(3),
	// 資格
	QUALIFICATION(4),
	// チーム
	TEAM(5),
	// ランク
	RANK(6),
	// 保険加入状況
	INSURANCE_STATUS(7),
	// プロフィール
	PROFILE(8);

	public final int value;
}
