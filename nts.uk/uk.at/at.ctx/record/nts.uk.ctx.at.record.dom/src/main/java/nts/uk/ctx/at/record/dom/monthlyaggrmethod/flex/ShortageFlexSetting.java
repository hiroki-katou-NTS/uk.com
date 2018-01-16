package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

import lombok.Getter;
import lombok.val;

/**
 * フレックス不足設定
 * @author shuichu_ishida
 */
@Getter
public class ShortageFlexSetting {

	/** 繰越設定 */
	private CarryforwardSetInShortageFlex carryforwardSet;

	/**
	 * コンストラクタ
	 */
	public ShortageFlexSetting(){
		
		this.carryforwardSet = CarryforwardSetInShortageFlex.CURRENT_MONTH_INTEGRATION;
	}
	
	/**
	 * ファクトリー
	 * @param carryforwardSet 繰越設定
	 * @return フレックス不足設定
	 */
	public static ShortageFlexSetting of(
			CarryforwardSetInShortageFlex carryforwardSet){
		
		val domain = new ShortageFlexSetting();
		domain.carryforwardSet = carryforwardSet;
		return domain;
	}
}
