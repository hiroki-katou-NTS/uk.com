package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;

/** 年別実績の36協定時間 */
@Getter
public class AgreementTimeOfYear implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	/** 対象時間 */
	private AgreementOneYearTime targetTime;
	/** 閾値 */
	private OneYearTime threshold;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeOfYear(){
		
		this.targetTime = new AgreementOneYearTime(0);
		this.threshold = new OneYearTime();
	}
	
	/**	
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param threshold 閾値
	 * @return 月別実績の36協定時間
	 */
	public static AgreementTimeOfYear of(
			AgreementOneYearTime agreementTime,
			OneYearTime threshold){
		
		AgreementTimeOfYear domain = new AgreementTimeOfYear();
		domain.targetTime = agreementTime;
		domain.threshold = threshold;
		return domain;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeOfYear target){
		
		this.targetTime = this.targetTime.addMinutes(target.targetTime.v());
	}
}
