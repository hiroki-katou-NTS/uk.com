package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class 子の看護暫定データ
 */
@Getter
public class ChildTempCareData extends AggregateRoot {

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 年月日
	 */
	private GeneralDate ymd;

	/**
	 * 勤務種類
	 */
	private WorkTypeCode workTypeCode;

	/**
	 * 使用日数
	 */
	private ManagementDays usedDays;

	/**
	 * 使用時間
	 */
	private TimeHoliday usedTime;

	/**
	 * 予定実績区分
	 */
	private ScheduleRecordAtr scheduleRecordAtr;

	public ChildTempCareData(String employeeId, GeneralDate ymd, WorkTypeCode workTypeCode, ManagementDays usedDays,
			TimeHoliday usedTime, ScheduleRecordAtr scheduleRecordAtr) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workTypeCode = workTypeCode;
		this.usedDays = usedDays;
		this.usedTime = usedTime;
		this.scheduleRecordAtr = scheduleRecordAtr;
	}

	public static ChildTempCareData createFromJavaType(String employeeId, GeneralDate ymd, String workTypeCode, double usedDays,
			int usedTime, int scheduleRecordAtr) {
		return new ChildTempCareData(employeeId, ymd, new WorkTypeCode(workTypeCode), new ManagementDays(usedDays),
				new TimeHoliday(usedTime), EnumAdaptor.valueOf(scheduleRecordAtr, ScheduleRecordAtr.class));
	}

}
