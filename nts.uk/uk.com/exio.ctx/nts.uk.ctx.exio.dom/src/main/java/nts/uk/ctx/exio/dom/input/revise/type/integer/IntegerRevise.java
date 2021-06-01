package nts.uk.ctx.exio.dom.input.revise.type.integer;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.revise.ItemType;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.RevisingValueType;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;

/**
 * 整数型編集
 */
@AllArgsConstructor
public class IntegerRevise implements RevisingValueType {
	
	/** 固定値として受け入れる */
	private boolean useFixedValue;
	
	/** 固定値の値 */
	private Optional<FixedIntegerValue> fixedValue;
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private RangeOfValue rangeOfValue;
	
	/** コード変換コード */
	private Optional<CodeConvertCode> codeConvertCode;
	
	

	@Override
	public RevisedValueResult revise(CsvItem target) {
		if(target.getType() != ItemType.INT) {
			throw new RuntimeException("編集しようとしている項目は整数型ではありません。");
		}
		
		int result;
		try {
			result = Integer.parseInt(target.getValue());
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
		
		if(useFixedValue) {
			// 固定値を使用する場合
			result = this.fixedValue.get().v().intValue();
			return RevisedValueResult.succeeded(DataItem.of(target.getItemNo(), result));
		}
		
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			result = this.rangeOfValue.extract(result);
		}
		
		if(codeConvertCode.isPresent()) {
			// コード変換を実施する場合
			// TODO 既存処理で未対応
		}
		return RevisedValueResult.succeeded(DataItem.of(target.getItemNo(), result));
	}
}
