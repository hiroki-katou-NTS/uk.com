package nts.uk.shr.com.license.option;

/**
 * 就業オプション
 */
public interface AttendanceOptions {

	/** アラームリスト */
	boolean alarmList();
	
	/** 工数・作業管理 */
	boolean workload();
	
	/** 時間休暇 */
	boolean hourlyPaidLeave();
	
	/** 申請承認 */
	boolean application();
	
	/** 任意期間集計 */
	boolean anyPeriodAggregation();
	
	/** 複数回・臨時勤務 */
	boolean multipleWork();
	
	/** 予約 */
	boolean reservation();
	
	/** Web打刻 */
	WebTimeStamp webTimeStamp();
	
	public static interface WebTimeStamp {
		
		boolean isEnabled();
		
		/** NEC指打刻 */
		boolean fingerNEC();
	}
	
	/** スケジュール */
	Schedule schedule();
	
	public static interface Schedule {
		
		boolean isEnabled();
		
		/** 医療 */
		boolean medical();
		
		/** 介護 */
		boolean nursing();
	}
	
	
}
