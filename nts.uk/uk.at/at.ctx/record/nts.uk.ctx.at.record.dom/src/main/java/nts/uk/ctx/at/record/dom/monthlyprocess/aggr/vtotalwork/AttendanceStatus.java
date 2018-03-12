package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.vtotalwork;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
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
	/** 出退勤 */
	private List<TimeLeavingWork> timeLeavingWorks;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public AttendanceStatus(GeneralDate ymd){
		
		this.ymd = ymd;
		this.totalTime = new AttendanceTime(0);
		this.timeLeavingWorks = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param totalTime 総労働時間
	 * @param timeLeavingWorks 出退勤リスト
	 * @return 出勤状態
	 */
	public static AttendanceStatus of(
			GeneralDate ymd, AttendanceTime totalTime, List<TimeLeavingWork> timeLeavingWorks){
		
		val domain = new AttendanceStatus(ymd);
		domain.totalTime = totalTime;
		domain.timeLeavingWorks = timeLeavingWorks;
		return domain;
	}
	
	/**
	 * 2回目の打刻が存在するか
	 * @return true：存在する、false：存在しない
	 */
	public boolean isTwoTimesStampExists(){
		
		boolean returnVal = false;
		for (val timeLeavingWork : this.timeLeavingWorks){
			if (timeLeavingWork.getWorkNo().v() != 2) continue;
			if (timeLeavingWork.getAttendanceStamp().isPresent()) returnVal = true;
			if (timeLeavingWork.getLeaveStamp().isPresent()) returnVal = true;
		}
		return returnVal;
	}
}
