package nts.uk.ctx.exio.dom.input.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.revise.ItemType;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.RevisingValueType;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;

@AllArgsConstructor
public class StringRevise implements RevisingValueType {
	
	/** 固定値として受け入れる */
	private boolean useFixedValue;
	
	/** 固定値の値 */
	private Optional<FixedStringValue> fixedValue;
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private RangeOfValue rangeOfValue;
	
	/** 固定長編集する */
	private boolean useFixedLength;
	
	/** 固定長編集内容 */
	private FixedLength fixedLength;
		
	/** コード変換コード */
	private Optional<CodeConvertCode> codeConvertCode;

	@Override
	public RevisedValueResult revise(CsvItem target) {
		if(target.getType() != ItemType.STRING) {
			throw new RuntimeException("編集しようとしている項目は文字型ではありません。");
		}
		
		String result = target.getValue();
		
		if(useFixedValue) {
			// 固定値を使用する場合
			result = this.fixedValue.get().v();
			return RevisedValueResult.succeeded(DataItem.of(target.getItemNo(), result));
		}
		
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			result = this.rangeOfValue.extract(result);
		}
		
		if(useFixedLength) {
			// 固定長編集をする場合
			result = this.fixedLength.fix(result);
		}
		
		if(codeConvertCode.isPresent()) {
			// コード変換を実施する場合
			// TODO 既存処理で未対応
		}
		
		return RevisedValueResult.succeeded(DataItem.of(target.getItemNo(), result));
	}

}
