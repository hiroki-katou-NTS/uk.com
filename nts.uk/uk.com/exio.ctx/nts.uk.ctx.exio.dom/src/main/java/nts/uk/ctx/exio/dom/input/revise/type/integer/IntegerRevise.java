package nts.uk.ctx.exio.dom.input.revise.type.numeric;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.RevisingValueType;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

/**
 * 
 * 整数・実数型編集
 *
 */
@Getter
@AllArgsConstructor
public class NumericRevise implements RevisingValueType {
	
	/** 固定値として受け入れる */
	private boolean useFixedValue;
	
	/** 固定値の値 */
	private Optional<Integer> fixedValue;
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private RangeOfValue rangeOfValue;
	
	/** 固定長編集する */
	private boolean useFixedLength;
	
	

	@Override
	public RevisedValueResult revise(CsvItem target) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
