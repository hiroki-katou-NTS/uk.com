package nts.uk.ctx.exio.dom.input.revise.type.real;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.dataformat.value.DecimalDigitNumber;
import nts.uk.ctx.exio.dom.dataformat.value.Rounding;

/**
 * 小数編集
 */
@AllArgsConstructor
public class DecimalRevise {
	
	/** 桁数 */
	private DecimalDigitNumber length;
	
	/** 端数処理 */
	private Rounding rounding;
	
	/** 小数点を省略する */
	private boolean omitDecimalPoint;
	
	public Double revise(Double target) {
		return null;
	}
	
}
