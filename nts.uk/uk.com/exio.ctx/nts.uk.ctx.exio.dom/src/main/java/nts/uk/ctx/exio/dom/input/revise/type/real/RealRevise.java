package nts.uk.ctx.exio.dom.input.revise.type.real;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.RevisingValueType;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

public class RealRevise implements RevisingValueType {
	
	/** 固定値として受け入れる */
	private boolean useFixedValue;
	
	/** 固定値の値 */
	private Optional<FixedRealValue> fixedValue;
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<RangeOfValue> rangeOfValue;
	
	/** 小数編集する */
	private boolean useDecimalRevise;
	
	/** 小数編集 */
	private Optional<DecimalRevise> decimalRevise;
	

	@Override
	public RevisedValueResult revise(CsvItem target) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
