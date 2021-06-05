package nts.uk.ctx.exio.dom.input.revise.type.integer;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

/**
 * 整数型編集
 */
@AllArgsConstructor
public class IntegerRevise implements ReviseValue {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<RangeOfValue> rangeOfValue;
	
	@Override
	public RevisedValueResult revise(String target) {
		String resultStr = target;
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			resultStr = this.rangeOfValue.get().extract(target);
		}
		return stringToInt(resultStr);
	}
	
	// 文字列→整数変換
	private RevisedValueResult stringToInt(String resultStr) {
		try {
			Long resultInt = Long.parseLong(resultStr);
			return RevisedValueResult.succeeded(resultInt);
		}
		catch(Exception e){
			return RevisedValueResult.failed("Msg_1017");
		}
		
	}
}
