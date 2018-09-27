package nts.uk.ctx.at.record.dom.byperiod;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 期間別の36協定時間
 * @author shuichu_ishida
 */
@Getter
public class AgreementTimeByPeriod implements Cloneable {

	/** 36協定時間 */
	private AttendanceTimeMonth agreementTime;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeByPeriod(){
		
		this.agreementTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @return 期間別の36協定時間
	 */
	public static AgreementTimeByPeriod of(
			AttendanceTimeMonth agreementTime){
		
		AgreementTimeByPeriod domain = new AgreementTimeByPeriod();
		domain.agreementTime = agreementTime;
		return domain;
	}
	
	@Override
	public AgreementTimeByPeriod clone() {
		AgreementTimeByPeriod cloned = new AgreementTimeByPeriod();
		try {
			cloned.agreementTime = new AttendanceTimeMonth(this.agreementTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("AgreementTimeByPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * 集計処理
	 * @param excessOutside 期間別の時間外超過
	 */
	public void aggregate(
			ExcessOutsideByPeriod excessOutside){
		
		// 時間外超過を取得
		int totalExcessMinutes = 0;
		for (val excessOutsideItem : excessOutside.getExcessOutsideItems().values()){
			
			// 超過時間を合計
			totalExcessMinutes += excessOutsideItem.getExcessTime().v();
		}
		
		// 合計した時間を36協定時間とする
		this.agreementTime = new AttendanceTimeMonth(totalExcessMinutes);
	}
}
