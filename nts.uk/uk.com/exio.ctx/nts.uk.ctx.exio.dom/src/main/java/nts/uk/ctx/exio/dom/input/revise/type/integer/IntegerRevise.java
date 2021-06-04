package nts.uk.ctx.exio.dom.input.revise.type.integer;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.RevisingValueType;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;

/**
 * 整数型編集
 */
@AllArgsConstructor
public class IntegerRevise implements RevisingValueType {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private RangeOfValue rangeOfValue;
	
	/** コード変換コード */
	private Optional<CodeConvertCode> codeConvertCode;
	
	

	@Override
	public RevisedValueResult revise(String target) {
		
		int result;
		try {
			result = Integer.parseInt(target);
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
		
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			result = this.rangeOfValue.extract(result);
		}
		
		if(codeConvertCode.isPresent()) {
			// コード変換を実施する場合
			// TODO 既存処理で未対応
		}
		return RevisedValueResult.succeeded(result);
	}
}
