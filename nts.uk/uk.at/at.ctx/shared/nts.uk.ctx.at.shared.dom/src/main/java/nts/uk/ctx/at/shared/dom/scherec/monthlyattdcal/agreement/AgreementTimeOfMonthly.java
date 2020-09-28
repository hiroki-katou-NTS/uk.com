package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;

/**
 * 月別実績の36協定時間
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeOfMonthly implements Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 対象時間 */
	@Setter
	private AttendanceTimeMonth agreementTime;
	/** 閾値 */
	private OneMonthTime threshold;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeOfMonthly(){
		
		this.agreementTime = new AttendanceTimeMonth(0);
		this.threshold = new OneMonthTime(
				new OneMonthErrorAlarmTime(
						new AgreementOneMonthTime(0), 
						new AgreementOneMonthTime(0)), 
				new AgreementOneMonthTime(0));
	}
	
	/**	
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param threshold 閾値
	 * @return 月別実績の36協定時間
	 */
	public static AgreementTimeOfMonthly of(
			AttendanceTimeMonth agreementTime,
			OneMonthTime threshold){
		
		AgreementTimeOfMonthly domain = new AgreementTimeOfMonthly();
		domain.agreementTime = agreementTime;
		domain.threshold = threshold;
		return domain;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeOfMonthly target){
		
		this.agreementTime = this.agreementTime.addMinutes(target.agreementTime.v());
	}
}