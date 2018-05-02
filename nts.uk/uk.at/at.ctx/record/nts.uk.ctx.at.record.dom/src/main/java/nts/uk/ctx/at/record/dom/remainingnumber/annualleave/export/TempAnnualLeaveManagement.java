package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 暫定年休管理データ
 * @author shuichu_ishida
 */
@Getter
public class TempAnnualLeaveManagement extends AggregateRoot {

	/** 社員ID */
	private String employeeId;
	/** 年月日 */
	private GeneralDate ymd;
	
	/** 年休使用 */
	@Setter
	private ManagementDays annualLeaveUse;
	/** 時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUse;
	/** 出勤前時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUseBeforeWork;
	/** 退勤後時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUseAfterWork;
	/** 出勤前2時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUseBeforeWork2;
	/** 退勤後2時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUseAfterWork2;
	/** 私用外出時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUsePrivateGoOut;
	/** 組合外出時間年休使用 */
	@Setter
	private TimeHoliday timeAnnualLeaveUseUnionGoOut;
	/** 勤務種類 */
	@Setter
	private WorkTypeCode workType;
	/** 予定実績区分 */
	@Setter
	private ScheduleRecordAtr scheduleRecordAtr;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 */
	public TempAnnualLeaveManagement(String employeeId, GeneralDate ymd){
		
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		
		this.annualLeaveUse = new ManagementDays(0.0);
		this.timeAnnualLeaveUse = new TimeHoliday(0);
		this.timeAnnualLeaveUseBeforeWork = new TimeHoliday(0);
		this.timeAnnualLeaveUseAfterWork = new TimeHoliday(0);
		this.timeAnnualLeaveUseBeforeWork2 = new TimeHoliday(0);
		this.timeAnnualLeaveUseAfterWork2 = new TimeHoliday(0);
		this.timeAnnualLeaveUsePrivateGoOut = new TimeHoliday(0);
		this.timeAnnualLeaveUseUnionGoOut = new TimeHoliday(0);
		this.workType = new WorkTypeCode("non");
		this.scheduleRecordAtr = ScheduleRecordAtr.NOT_APPLICABLE;
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @param annualLeaveUse 年休使用
	 * @param timeAnnualLeaveUse 時間年休使用
	 * @param timeAnnualLeaveUseBeforeWork 出勤前時間年休使用
	 * @param timeAnnualLeaveUseAfterWork 退勤後時間年休使用
	 * @param timeAnnualLeaveUseBeforeWork2 出勤前2時間年休使用
	 * @param timeAnnualLeaveUseAfterWork2 退勤後2時間年休使用
	 * @param timeAnnualLeaveUsePrivateGoOut 私用外出時間年休使用
	 * @param timeAnnualLeaveUseUnionGoOut 組合外出時間年休使用
	 * @param workType 勤務種類
	 * @param scheduleRecordAtr 予定実績区分
	 * @return 暫定年休管理データ
	 */
	public static TempAnnualLeaveManagement of(
			String employeeId,
			GeneralDate ymd,
			ManagementDays annualLeaveUse,
			TimeHoliday timeAnnualLeaveUse,
			TimeHoliday timeAnnualLeaveUseBeforeWork,
			TimeHoliday timeAnnualLeaveUseAfterWork,
			TimeHoliday timeAnnualLeaveUseBeforeWork2,
			TimeHoliday timeAnnualLeaveUseAfterWork2,
			TimeHoliday timeAnnualLeaveUsePrivateGoOut,
			TimeHoliday timeAnnualLeaveUseUnionGoOut,
			WorkTypeCode workType,
			ScheduleRecordAtr scheduleRecordAtr){
		
		TempAnnualLeaveManagement domain = new TempAnnualLeaveManagement(employeeId, ymd);
		domain.annualLeaveUse = annualLeaveUse;
		domain.timeAnnualLeaveUse = timeAnnualLeaveUse;
		domain.timeAnnualLeaveUseBeforeWork = timeAnnualLeaveUseBeforeWork;
		domain.timeAnnualLeaveUseAfterWork = timeAnnualLeaveUseAfterWork;
		domain.timeAnnualLeaveUseBeforeWork2 = timeAnnualLeaveUseBeforeWork2;
		domain.timeAnnualLeaveUseAfterWork2 = timeAnnualLeaveUseAfterWork2;
		domain.timeAnnualLeaveUsePrivateGoOut = timeAnnualLeaveUsePrivateGoOut;
		domain.timeAnnualLeaveUseUnionGoOut = timeAnnualLeaveUseUnionGoOut;
		domain.workType = workType;
		domain.scheduleRecordAtr = scheduleRecordAtr;
		return domain;
	}
}
