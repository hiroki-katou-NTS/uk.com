package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

import lombok.Getter;
import lombok.val;

/**
 * 時間外超過設定
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideTimeSetting {

	/** 時間外超過対象設定 */
	private ExcessOutsideTimeTargetSetting excessOutsideTimeTargetSet;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideTimeSetting(){
		
		this.excessOutsideTimeTargetSet = ExcessOutsideTimeTargetSetting.ONLY_ILLEGAL_FLEX;
	}
	
	/**
	 * ファクトリー
	 * @param excessOutsideTimeTargetSetting 時間外超過対象設定
	 * @return 時間外超過設定
	 */
	public static ExcessOutsideTimeSetting of(
			ExcessOutsideTimeTargetSetting excessOutsideTimeTargetSetting){
		
		val domain = new ExcessOutsideTimeSetting();
		domain.excessOutsideTimeTargetSet = excessOutsideTimeTargetSetting;
		return domain;
	}
}
