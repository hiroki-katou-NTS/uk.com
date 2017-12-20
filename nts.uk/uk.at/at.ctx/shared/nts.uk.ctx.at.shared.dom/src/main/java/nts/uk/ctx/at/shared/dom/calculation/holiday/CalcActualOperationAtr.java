package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 */
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalcActualOperationAtr {

	/** 実働時間のみで計算する*/
	CALC_ACTUAL_TIME(0),
	/** 実働時間以外も含めて計算する*/
	CALC_OTHER_ACTUAL_TIME(1);

	public final int value;
}
