package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;

/** 
 * 日別勤怠の応援作業時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別時間帯別勤怠.応援実績.時間帯.日別勤怠の応援作業時間帯
 **/
@Getter
@AllArgsConstructor
public class OuenWorkTimeSheetOfDailyAttendance implements DomainObject {

	/** 応援勤務枠No: 応援勤務枠No supportNo*/
	private SupportFrameNo workNo;
	
	/** 応援形式: 応援形式 */
	private SupportType supportType;

	/** 作業内容: 作業内容 */
	private WorkContent workContent;
	
	/** 時間帯: 時間帯別勤怠の時間帯 */
	private TimeSheetOfAttendanceEachOuenSheet timeSheet;
	
	/** 作業時間入力フラグ*/
	private Optional<Boolean> inputFlag;
	
	//実績のデータを作成する
	public static OuenWorkTimeSheetOfDailyAttendance create(
					SupportFrameNo workNo
				,	WorkContent workContent 
				,	TimeSheetOfAttendanceEachOuenSheet timeSheet
				,	Optional<Boolean> inputFlag ) {

		return new OuenWorkTimeSheetOfDailyAttendance(
					workNo
				,	SupportType.TIMEZONE
				,	workContent
				,	timeSheet
				,	inputFlag );
	}

	public void setWorkNo(int workNo) {
		this.workNo = SupportFrameNo.of(workNo);
	}

	public void update(OuenWorkTimeSheetOfDailyAttendance inputSheet) {
		this.workContent = inputSheet.getWorkContent();
		this.timeSheet = inputSheet.getTimeSheet();
	}
}
