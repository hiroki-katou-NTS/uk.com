package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;

/**
 * 乖離時間の自動計算区分
 * @author keisuke_hoshina
 *
 */
@Getter
public class AutoCalcSetOfDivergenceTime {
	private boolean divergence;
	
	/**
	 * 「計算する」に変更する
	 */
	public void changeCalcTrue() {
		this.divergence = true;
	}
}
