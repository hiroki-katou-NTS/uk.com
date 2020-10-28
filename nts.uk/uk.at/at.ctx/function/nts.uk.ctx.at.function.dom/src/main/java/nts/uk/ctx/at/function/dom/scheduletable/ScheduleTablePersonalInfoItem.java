package nts.uk.ctx.at.function.dom.scheduletable;

/**
 * スケジュール表の個人情報項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.スケジュール表.スケジュール表の個人情報項目
 * @author dan_pv
 *
 */
public enum ScheduleTablePersonalInfoItem {

	/** 社員名 */
	EMPLOYEE_NAME(0),

	/** 雇用 */
	EMPLOYMENT(1),
	
	/** 職位 */
	POSITION(2),
	
	/** 分類 */
	CLASSIFICATION(3),
	
	/** チーム */
	TEAM(4),
	
	/** ランク */
	RANK(5),
	
	/** 看護区分 */
	NURSE_CLASSIFICATION(6),
	
	/** 資格 */
	QUALIFICATION(7);
	
	public int value;
	
	private ScheduleTablePersonalInfoItem (int value) {
		this.value = value;
	}
	
}
