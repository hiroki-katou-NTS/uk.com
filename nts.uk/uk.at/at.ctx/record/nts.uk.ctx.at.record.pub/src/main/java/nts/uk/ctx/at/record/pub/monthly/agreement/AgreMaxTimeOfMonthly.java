package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.onemonth.AgreementOneMonth;

/**
 * 月別実績の36協定上限時間
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxTimeOfMonthly {

	/** 36協定時間 */
	@Setter
	private AttendanceTimeMonth agreementTime;
	/** 上限時間 */
	private AgreementOneMonth maxTime;
	/** 状態 */
	private AgreMaxTimeStatusOfMonthly status;

	/**
	 * コンストラクタ
	 */
	public AgreMaxTimeOfMonthly(){
		
		this.agreementTime = new AttendanceTimeMonth(0);
		this.maxTime = new AgreementOneMonth(0);
		this.status = AgreMaxTimeStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param maxTime 上限時間
	 * @param status 状態
	 * @return 月別実績の36協定上限時間
	 */
	public static AgreMaxTimeOfMonthly of(
			AttendanceTimeMonth agreementTime,
			AgreementOneMonth maxTime,
			AgreMaxTimeStatusOfMonthly status){

		AgreMaxTimeOfMonthly domain = new AgreMaxTimeOfMonthly();
		domain.agreementTime = agreementTime;
		domain.maxTime = maxTime;
		domain.status = status;
		return domain;
	}
}
