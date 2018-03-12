package nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;

/**
 * 個人情報区分
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
public enum PerInfoAtr {
	/* 雇用 */
	EMPLOYEMENT(0),
	/* 職場 */
	WORKPLACE(1),
	/* 分類 */
	CLASSIFICATION(2),
	/* 職位 */
	JOB_TITLE(3),
	/* 資格 */
	QUALIFICATION(4),
	/* チーム  */
	TEAM(5),
	/* ランク  */
	RANK(6),
	/* 保険加入状況  */
	INSURANCE_STATUS(7),
	/* プロフィール  */
	PROFILE(8);
	
	public final int value;
}
