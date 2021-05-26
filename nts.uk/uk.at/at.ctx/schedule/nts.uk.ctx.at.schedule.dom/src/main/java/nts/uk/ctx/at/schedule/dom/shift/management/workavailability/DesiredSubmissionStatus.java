package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.希望提出状況
 * @author dungbn
 *
 */
public enum DesiredSubmissionStatus {

	// 希望なし	
	NO_HOPE(0),
	
	
	// 休日希望	
	HOLIDAY_HOPE(1),
	
	
	// 出勤希望
	COMMUTING_HOPE(2);
	
	public final int value;
	
	private DesiredSubmissionStatus(int value) {
		this.value = value;
	}
}
