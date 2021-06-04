package nts.uk.ctx.exio.dom.input.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.RevisingValueType;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;

@AllArgsConstructor
public class StringRevise implements RevisingValueType {
	
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
	public RevisedValueResult revise(String target) {
		
		String result = target;
		
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
		
		return RevisedValueResult.succeeded(result);
	}

}
