package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 */
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PredExcessTimeflexAtr {

	/** フレックス超過を計算しない*/
	DO_NOT_CALC_FLEX_EXCESS(0),
	/** フレックス超過を計算する*/
	CALC_FLEX_EXCESS(1);

	public final int value;
}
