package nts.uk.ctx.at.shared.dom.scherec.attdstatus;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 出勤状態
 * @author shuichu_ishida
 */
@Getter
public class AttendanceStatus {

	/** 年月日 */
	private GeneralDate ymd;
	/** 総労働時間 */
	@Setter
	private AttendanceTime totalTime;
	/** 出勤あり */
	@Setter
	private boolean existAttendance;
	/** 2回目の打刻がある */
	@Setter
	private boolean existTwoTimesStamp;

	/**
	 * コンストラクタ
	 */
	public AttendanceStatus(GeneralDate ymd){
		
		this.ymd = ymd;
		this.totalTime = new AttendanceTime(0);
		this.existAttendance = false;
		this.existTwoTimesStamp = false;
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param totalTime 総労働時間
	 * @param isExistAttendance 出勤あり
	 * @param isExistTwoTimesStamp 2回目の打刻がある
	 * @return 出勤状態
	 */
	public static AttendanceStatus of(
			GeneralDate ymd,
			AttendanceTime totalTime,
			boolean isExistAttendance,
			boolean isExistTwoTimesStamp){
		
		AttendanceStatus domain = new AttendanceStatus(ymd);
		domain.totalTime = totalTime;
		domain.existAttendance = isExistAttendance;
		domain.existTwoTimesStamp = isExistTwoTimesStamp;
		return domain;
	}
}
