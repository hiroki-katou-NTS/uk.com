package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別勤怠の臨時出退勤
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.出退勤時刻.日別勤怠の臨時出退勤
 * @author tutk
 *
 */
@Getter
@Setter
public class TemporaryTimeOfDailyAttd implements DomainObject {
	
	// 1 ~ 10
	//出退勤
	private List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();

	public TemporaryTimeOfDailyAttd(List<TimeLeavingWork> timeLeavingWorks) {
		super();
		this.timeLeavingWorks = timeLeavingWorks;
	}

	/**
	 * 打刻漏れ状態チェック
	 * @return 打刻漏れ状態
	 */
	public List<StampLeakStateEachWork> checkStampLeakState() {
		List<StampLeakStateEachWork> result = new ArrayList<>();
		for (TimeLeavingWork timeLeavingWork : this.timeLeavingWorks) {
			result.add(new StampLeakStateEachWork(timeLeavingWork.getWorkNo(), timeLeavingWork.checkStampLeakState()));
		}
		return result;
	}
	
	/**
	 *一番時刻が遅い退勤時刻を取得する 
	 */
	public Optional<TimeWithDayAttr> getLastLeaveStamp(){
		val stamps = new ArrayList<TimeWithDayAttr>(); 
		this.timeLeavingWorks.forEach(work ->{
			work.getLeaveTime().ifPresent(s -> stamps.add(s));
		});
		return stamps.stream().max(Comparator.comparing(TimeWithDayAttr::v));
	}
	
	/**
	 *一番時刻が早い出勤時刻を取得する 
	 */
	public Optional<TimeWithDayAttr> getFirstAttendanceStamp(){
		val stamps = new ArrayList<TimeWithDayAttr>(); 
		this.timeLeavingWorks.forEach(work ->{
			work.getAttendanceTime().ifPresent(s -> stamps.add(s));
		});
		return stamps.stream().min(Comparator.comparing(TimeWithDayAttr::v));
	}
}
