package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

/**
 * @author laitv
 * 給与区分
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.給与区分
 */
public enum WageType {

	MONTHLY (0,"月給"),
	
	DAY_TO_MONTH (1,"日給月給"),
	
	DAILY_SALARY (2,"日給"),
	
	TIME_TO_GIVE (3,"時間給"),
	
	YEAR_STICK (4,"年棒");
	
	public int value;
	
	public String nameId;

	WageType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
