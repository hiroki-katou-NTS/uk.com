package nts.uk.ctx.at.record.app.find.monthly.root;

import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ClosureDateDto;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;

@Data
/** 月別実績（WORK） */
@AttendanceItemRoot(isContainer = true)
public class MonthlyRecordWorkDto extends MonthlyItemCommon {

	/** 年月: 年月 */
	private YearMonth yearMonth;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 締めID: 締めID */
	private int closureId;

	/** 締め日: 日付 */
	private ClosureDateDto closureDate;

	/** 月別実績の所属情報: 月別実績の所属情報 */
	@AttendanceItemLayout(jpPropertyName = "月別実績の所属情報", layout = "B")
	private AffiliationInfoOfMonthlyDto affiliation;

	/** 月別実績の勤怠時間: 月別実績の勤怠時間 */
	@AttendanceItemLayout(jpPropertyName = "月別実績の勤怠時間", layout = "A")
	private AttendanceTimeOfMonthlyDto attendanceTime;

	/** 月別実績の任意項目 */
	@AttendanceItemLayout(jpPropertyName = "月別実績の任意項目", layout = "C")
	private AnyItemOfMonthlyDto anyItem;
	
	/** 年休月別残数データ: 年休月別残数データ */
	@AttendanceItemLayout(jpPropertyName = "年休月別残数データ", layout = "D")
	private AnnLeaRemNumEachMonthDto annLeave;

	/** 積立年休月別残数データ: 積立年休月別残数データ */
	@AttendanceItemLayout(jpPropertyName = "積立年休月別残数データ", layout = "E")
	private RsvLeaRemNumEachMonthDto rsvLeave;
	
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public YearMonth yearMonth() {
		return yearMonth;
	}

}
