package nts.uk.ctx.at.shared.dom.monthly.agreement;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear;

/**
 * 36協定年間時間
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeYear implements Cloneable {

	/** 限度時間 */
	private LimitOneYear limitTime;
	/** 実績時間 */
	private AttendanceTimeYear recordTime;
	/** 状態 */
	private AgreTimeYearStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeYear() {
		this.limitTime = new LimitOneYear(0);
		this.recordTime = new AttendanceTimeYear(0);
		this.status = AgreTimeYearStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param limitTime 限度時間
	 * @param recordTime 実績時間
	 * @param status 状態
	 * @return 36協定年間時間
	 */
	public static AgreementTimeYear of(
			LimitOneYear limitTime,
			AttendanceTimeYear recordTime,
			AgreTimeYearStatusOfMonthly status){
		
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
			cloned.limitTime = new LimitOneYear(this.limitTime.v());
			cloned.recordTime = new AttendanceTimeYear(this.recordTime.v());
			cloned.status = this.status;
		}
		catch (Exception e){
			throw new RuntimeException("AgreementTimeYear clone error.");
		}
		return cloned;
	}
	
	/**
	 * 実績時間に分を加算する
	 * @param minutes 分
	 */
	public void addRecordTime(int minutes){
		this.recordTime = this.recordTime.addMinutes(minutes);
	}
	
	/**
	 * エラーチェック
	 */
	public void errorCheck(){
		this.status = AgreTimeYearStatusOfMonthly.NORMAL;
		if (this.recordTime.v() > this.limitTime.v()) {
			this.status = AgreTimeYearStatusOfMonthly.EXCESS_LIMIT;
		}
	}
}
