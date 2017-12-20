package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 */
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PredExcessTimeflexAtr {

	/** 実働時間のみで計算する*/
	CALC_FLEX_EXCESS(0),
	/** 実働時間以外も含めて計算する*/
	DO_NOT_CALC_FLEX_EXCESS(1);

	public final int value;
}
