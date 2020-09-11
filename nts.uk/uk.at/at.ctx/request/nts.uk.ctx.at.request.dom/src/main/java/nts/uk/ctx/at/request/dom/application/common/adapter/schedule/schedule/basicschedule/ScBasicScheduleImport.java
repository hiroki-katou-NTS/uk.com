package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ScBasicScheduleImport {
	// BasicSchedule
	// 社員ID
	private String employeeId;
	// 年月日
	private GeneralDate date;
	// 勤務種類コード
	private String workTypeCode;

	// 就業時間帯コード
	private String workTimeCode;
	// 開始時刻1
	private TimeWithDayAttr scheduleStartClock1;
	// 終了時刻1
	private TimeWithDayAttr scheduleEndClock1;
	// 開始時刻2
	private TimeWithDayAttr scheduleStartClock2;
	// 終了時刻2
	private TimeWithDayAttr scheduleEndClock2;
//	育児時間
	private Integer childTime;
//	短時間勤務時間帯
	private List<ShortWorkingTimeSheetImport> listShortWorkingTimeSheetExport = new ArrayList<>();
}
