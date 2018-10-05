package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 36協定時間内訳
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeBreakdown {

	/** 残業時間 */
	private AttendanceTimeMonth overTime;
	/** 振替残業時間 */
	private AttendanceTimeMonth transferOverTime;
	/** 休出時間 */
	private AttendanceTimeMonth holidayWorkTime;
	/** 振替時間 */
	private AttendanceTimeMonth transferTime;
	/** フレックス法定内時間 */
	private AttendanceTimeMonth flexLegalTime;
	/** フレックス法定外時間 */
	private AttendanceTimeMonth flexIllegalTime;
	/** 所定内割増時間 */
	private AttendanceTimeMonth withinPrescribedPremiumTime;
	/** 週割増時間 */
	private AttendanceTimeMonth weeklyPremiumTime;
	/** 月割増時間 */
	private AttendanceTimeMonth monthlyPremiumTime;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeBreakdown(){
		
		this.overTime = new AttendanceTimeMonth(0);
		this.transferOverTime = new AttendanceTimeMonth(0);
		this.holidayWorkTime = new AttendanceTimeMonth(0);
		this.transferTime = new AttendanceTimeMonth(0);
		this.flexLegalTime = new AttendanceTimeMonth(0);
		this.flexIllegalTime = new AttendanceTimeMonth(0);
		this.withinPrescribedPremiumTime = new AttendanceTimeMonth(0);
		this.weeklyPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyPremiumTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param overTime 残業時間
	 * @param transferOverTime 振替残業時間
	 * @param holidayWorkTime 休出時間
	 * @param transferTime 振替時間
	 * @param flexLegalTime フレックス法定内時間
	 * @param flexIllegalTime フレックス法定外時間
	 * @param withinPrescribedPremiumTime 所定内割増時間
	 * @param weeklyPremiumTime 週割増時間
	 * @param monthlyPremiumTime 月割増時間
	 * @return 36協定時間内訳
	 */
	public static AgreementTimeBreakdown of(
			AttendanceTimeMonth overTime,
			AttendanceTimeMonth transferOverTime,
			AttendanceTimeMonth holidayWorkTime,
			AttendanceTimeMonth transferTime,
			AttendanceTimeMonth flexLegalTime,
			AttendanceTimeMonth flexIllegalTime,
			AttendanceTimeMonth withinPrescribedPremiumTime,
			AttendanceTimeMonth weeklyPremiumTime,
			AttendanceTimeMonth monthlyPremiumTime){
		
		AgreementTimeBreakdown domain = new AgreementTimeBreakdown();
		domain.overTime = overTime;
		domain.transferOverTime = transferOverTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.transferTime = transferTime;
		domain.flexLegalTime = flexLegalTime;
		domain.flexIllegalTime = flexIllegalTime;
		domain.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		domain.weeklyPremiumTime = weeklyPremiumTime;
		domain.monthlyPremiumTime = monthlyPremiumTime;
		return domain;
	}
	
	/**
	 * 合計時間を取得
	 * @return 合計時間
	 */
	public AttendanceTimeMonth getTotalTime(){
		
		AttendanceTimeMonth totalTime = new AttendanceTimeMonth(0);
		totalTime = totalTime.addMinutes(this.overTime.v());
		totalTime = totalTime.addMinutes(this.transferOverTime.v());
		totalTime = totalTime.addMinutes(this.holidayWorkTime.v());
		totalTime = totalTime.addMinutes(this.transferTime.v());
		totalTime = totalTime.addMinutes(this.flexLegalTime.v());
		totalTime = totalTime.addMinutes(this.flexIllegalTime.v());
		totalTime = totalTime.addMinutes(this.withinPrescribedPremiumTime.v());
		totalTime = totalTime.addMinutes(this.weeklyPremiumTime.v());
		totalTime = totalTime.addMinutes(this.monthlyPremiumTime.v());
		return totalTime;
	}
}
