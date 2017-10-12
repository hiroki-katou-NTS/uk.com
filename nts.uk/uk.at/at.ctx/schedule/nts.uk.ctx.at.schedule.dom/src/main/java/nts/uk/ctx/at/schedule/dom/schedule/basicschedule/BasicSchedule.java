package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * 勤務予定基本情報
 * 
 * @author sonnh1
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicSchedule extends AggregateRoot {
	// employeeId
	private String sId;
	// 年月日
	private GeneralDate date;
	// 勤務種類
	private String workTypeCode;
	// 就業時間帯
	private String workTimeCode;
	// 確定区分
	private ConfirmedAtr confirmedAtr;
	// 稼働日区分
	private WorkdayDivision workDayAtr;

	public BasicSchedule(String sId, GeneralDate date, String workTypeCode, String workTimeCode) {
		this.sId = sId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
	}

	public static BasicSchedule createFromJavaType(String sId, GeneralDate date, String workTypeCode,
			String workTimeCode) {
		return new BasicSchedule(sId, date, workTypeCode, workTimeCode);
	}
}
