package nts.uk.ctx.at.record.dom.weekly;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitWeek;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;

/**
 * 週別の36協定時間
 * @author shuichu_ishida
 */
@Getter
public class AgreementTimeOfWeekly implements Cloneable {

	/** 36協定時間 */
	private AttendanceTimeMonth agreementTime;
	/** 限度エラー時間 */
	private LimitWeek limitErrorTime;
	/** 限度アラーム時間 */
	private LimitWeek limitAlarmTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeOfWeekly(){
		
		this.agreementTime = new AttendanceTimeMonth(0);
		this.limitErrorTime = new LimitWeek(0);
		this.limitAlarmTime = new LimitWeek(0);
		this.status = AgreementTimeStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param limitErrorTime 限度エラー時間
	 * @param limitAlarmTime 限度アラーム時間
	 * @param status 状態
	 * @return 週別の36協定時間
	 */
	public static AgreementTimeOfWeekly of(
			AttendanceTimeMonth agreementTime,
			LimitWeek limitErrorTime,
			LimitWeek limitAlarmTime,
			AgreementTimeStatusOfMonthly status){
		
		AgreementTimeOfWeekly domain = new AgreementTimeOfWeekly();
		domain.agreementTime = agreementTime;
		domain.limitErrorTime = limitErrorTime;
		domain.limitAlarmTime = limitAlarmTime;
		domain.status = status;
		return domain;
	}
	
	@Override
	public AgreementTimeOfWeekly clone() {
		AgreementTimeOfWeekly cloned = new AgreementTimeOfWeekly();
		try {
			cloned.agreementTime = new AttendanceTimeMonth(this.agreementTime.v());
			cloned.limitErrorTime = new LimitWeek(this.limitErrorTime.v());
			cloned.limitAlarmTime = new LimitWeek(this.limitAlarmTime.v());
			cloned.status.value = this.status.value;
		}
		catch (Exception e){
			throw new RuntimeException("AgreementTimeByPeriod clone error.");
		}
		return cloned;
	}
}
