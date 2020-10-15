package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定
 * @author kingo
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ScWorkScheduleExport {

	// 社員ID
	private String employeeId;

	// 年月日
	private GeneralDate date;

	// 勤務種類コード
	private String workTypeCode;

	// 就業時間帯コード
	private String workTimeCode;
	
	// 勤務予定時間帯 
	private List<ScheduleTimeSheetExport> listScheduleTimeSheetExport = new ArrayList<>();
	
	//短時間勤務時間帯
	private List<ShortWorkingTimeSheetExport> listShortWorkingTimeSheetExport = new ArrayList<>();

	public ScWorkScheduleExport(String employeeId, GeneralDate date, String workTypeCode, String workTimeCode,
			List<ScheduleTimeSheetExport> listScheduleTimeSheetExport,
			List<ShortWorkingTimeSheetExport> listShortWorkingTimeSheetExport) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.listScheduleTimeSheetExport = listScheduleTimeSheetExport;
		this.listShortWorkingTimeSheetExport = listShortWorkingTimeSheetExport;
	}
	
	
}
