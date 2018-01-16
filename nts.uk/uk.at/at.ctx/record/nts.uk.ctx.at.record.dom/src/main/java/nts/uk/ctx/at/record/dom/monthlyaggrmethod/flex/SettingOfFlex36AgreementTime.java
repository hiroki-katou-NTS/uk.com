package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

import lombok.Getter;
import lombok.val;

/**
 * フレックス36協定時間の設定
 * @author shuichu_ishida
 */
@Getter
public class SettingOfFlex36AgreementTime {

	/** 集計方法 */
	private AggrMethodOfFlex36AgreementTime aggregateMethod;

	/**
	 * コンストラクタ
	 */
	public SettingOfFlex36AgreementTime(){
		
		this.aggregateMethod = AggrMethodOfFlex36AgreementTime.FROM_STATUTORY_WORKING_TIME;
	}
	
	/**
	 * ファクトリー
	 * @param aggregateMethod 集計方法
	 * @return フレックス36協定時間の設定
	 */
	public static SettingOfFlex36AgreementTime of(
			AggrMethodOfFlex36AgreementTime aggregateMethod){
		
		val domain = new SettingOfFlex36AgreementTime();
		domain.aggregateMethod = aggregateMethod;
		return domain;
	}
}
