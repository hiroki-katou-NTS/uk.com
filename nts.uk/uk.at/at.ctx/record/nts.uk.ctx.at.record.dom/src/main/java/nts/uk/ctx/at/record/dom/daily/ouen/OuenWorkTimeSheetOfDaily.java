package nts.uk.ctx.at.record.dom.daily.ouen;


import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@Getter
/** 日別実績の応援作業別勤怠時間帯 */
public class OuenWorkTimeSheetOfDaily extends AggregateRoot {

	/** 社員ID: 社員ID */
	private String empId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 応援時間帯: 日別勤怠の応援作業時間帯 */
	private List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet;

	public OuenWorkTimeSheetOfDaily(String empId, GeneralDate ymd, List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet) {
		super();
		this.empId = empId;
		this.ymd = ymd;
		this.ouenTimeSheet = ouenTimeSheet;
	}
	
	public static OuenWorkTimeSheetOfDaily create(String empId, GeneralDate ymd,
			List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet) {

		return new OuenWorkTimeSheetOfDaily(empId, ymd, ouenTimeSheet);
	}
	
	/**
	 * @name [1] 変更する
	 * @input 時間帯リスト	List<日別勤怠の応援作業時間帯>
	 * @output	変更する勤怠項目
	 */
	public AttendanceItemToChange change(List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet) {
		//@応援時間帯 = 時間帯リスト			
		this.ouenTimeSheet = ouenTimeSheet;
		return null;
	}
	
	/**
	 * @name [2] 応援時間帯に対応する勤怠項目ID一覧
	 * @output 	勤怠項目リスト	List<勤怠項目ID>
	 */
	public List<String> getAttendanceId() {
		return null;
	}
}
