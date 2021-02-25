package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/**
 * 36協定年間時間
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeYear implements Cloneable {

	/** 法定上限対象時間 */
	private AgreementTimeOfYear limitTime;
	/** 36協定対象時間 */
	private AgreementTimeOfYear recordTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeYear() {
		this.limitTime = new AgreementTimeOfYear();
		this.recordTime = new AgreementTimeOfYear();
		this.status = AgreementTimeStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param limitTime 法定上限対象時間
	 * @param recordTime 36協定対象時間
	 * @param status 状態
	 * @return 36協定年間時間
	 */
	public static AgreementTimeYear of(
			AgreementTimeOfYear limitTime,
			AgreementTimeOfYear recordTime,
			AgreementTimeStatusOfMonthly status){
		
		AgreementTimeYear domain = new AgreementTimeYear();
		domain.limitTime = limitTime;
		domain.recordTime = recordTime;
		domain.status = status;
		return domain;
	}
	
	@Override
	public AgreementTimeYear clone() {
		AgreementTimeYear cloned = new AgreementTimeYear();
		try {
			cloned.limitTime = AgreementTimeOfYear.of(
											new AgreementOneYearTime(this.limitTime.getTargetTime().v()),
											this.limitTime.getThreshold());
			cloned.recordTime = AgreementTimeOfYear.of(
											new AgreementOneYearTime(this.recordTime.getTargetTime().v()),
											this.recordTime.getThreshold());
			cloned.status = this.status;
		}
		catch (Exception e){
			throw new RuntimeException("AgreementTimeYear clone error.");
		}
		return cloned;
	}
}
