package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon;

import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計PCログオン時刻
 * @author shuichu_ishida
 */
@Getter
public class AggrPCLogonClock {

	/** 合計日数 */
	private AttendanceDaysMonth totalDays;
	/** 合計時刻 */
	private AttendanceTimeMonth totalClock;
	/** 平均時刻 */
	private AttendanceTimeMonth averageClock;
	
	/**
	 * コンストラクタ
	 */
	public AggrPCLogonClock(){
		
		this.totalDays = new AttendanceDaysMonth(0.0);
		this.totalClock = new AttendanceTimeMonth(0);
		this.averageClock = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * @param totalDays 合計日数
	 * @param totalClock 合計時刻
	 * @param averageClock 平均時刻
	 * @return 集計PCログオン時刻
	 */
	public static AggrPCLogonClock of(
			AttendanceDaysMonth totalDays,
			AttendanceTimeMonth totalClock,
			AttendanceTimeMonth averageClock){
		
		AggrPCLogonClock domain = new AggrPCLogonClock();
		domain.totalDays = totalDays;
		domain.totalClock = totalClock;
		domain.averageClock = averageClock;
		return domain;
	}
	
	/**
	 * 集計
	 * @param pcLogonInfoOpt 日別実績のPCログオン情報 
	 * @param isLogon ログオン集計するか
	 */
	public void aggregate(Optional<PCLogOnInfoOfDaily> pcLogonInfoOpt, boolean isLogon){
		
		if (!pcLogonInfoOpt.isPresent()) return;
		val pcLogonInfo = pcLogonInfoOpt.get();

		if (isLogon){
			boolean isExistLogon = false;
			
			// ログオン時刻を合計
			for (val logonInfo : pcLogonInfo.getLogOnInfo()){
				if (!logonInfo.getLogOn().isPresent()) continue;
				this.totalClock = this.totalClock.addMinutes(logonInfo.getLogOn().get().v());
				isExistLogon = true;
			}
			
			// 合計日数を加算
			if (isExistLogon) this.totalDays = this.totalDays.addDays(1.0);
		}
		else {
			boolean isExistLogoff = false;
			
			// ログオフ時刻を合計
			for (val logonInfo : pcLogonInfo.getLogOnInfo()){
				if (!logonInfo.getLogOff().isPresent()) continue;
				this.totalClock = this.totalClock.addMinutes(logonInfo.getLogOff().get().v());
				isExistLogoff = true;
			}
			
			// 合計日数を加算
			if (isExistLogoff) this.totalDays = this.totalDays.addDays(1.0);
		}
		
		// 平均時刻を計算
		this.averageClock = new AttendanceTimeMonth(0);
		if (this.totalDays.v() != 0.0){
			this.averageClock = new AttendanceTimeMonth(this.totalClock.v() / this.totalDays.v().intValue());
		}
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AggrPCLogonClock target){
		
		this.totalDays = this.totalDays.addDays(target.totalDays.v());
		this.totalClock = this.totalClock.addMinutes(target.totalClock.v());
		
		this.averageClock = new AttendanceTimeMonth(0);
		if (this.totalDays.v() != 0.0){
			this.averageClock = new AttendanceTimeMonth(this.totalClock.v() / this.totalDays.v().intValue());
		}
	}
}
