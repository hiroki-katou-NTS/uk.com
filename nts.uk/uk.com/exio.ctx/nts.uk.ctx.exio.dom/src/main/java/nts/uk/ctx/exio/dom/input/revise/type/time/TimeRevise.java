package nts.uk.ctx.exio.dom.input.revise.type.time;

import java.util.Optional;

import nts.uk.ctx.exio.dom.dataformat.value.DecimalDigitNumber;
import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

public class TimeRevise implements ReviseValue {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<RangeOfValue> rangeOfValue;
	
	/** 整数値を小数として受け入れる */
	private boolean isDecimalization;
	
	/** 桁数 */
	private Optional<DecimalDigitNumber> length;

	@Override
	public RevisedValueResult revise(String target) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
