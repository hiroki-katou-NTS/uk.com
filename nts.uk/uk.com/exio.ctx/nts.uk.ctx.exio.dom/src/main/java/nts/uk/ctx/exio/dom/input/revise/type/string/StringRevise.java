package nts.uk.ctx.exio.dom.input.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

@AllArgsConstructor
public class StringRevise implements ReviseValue {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<RangeOfValue> rangeOfValue;
	
	/** 固定長編集する */
	private boolean useFixedLength;
	
	/** 固定長編集内容 */
	private Optional<FixedLength> fixedLength;
	
	@Override
	public RevisedValueResult revise(String target) {
		
		String strResult = target;
		
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			strResult = this.rangeOfValue.get().extract(strResult);
		}
		
		if(useFixedLength) {
			// 固定長編集をする場合
			strResult = this.fixedLength.get().fix(strResult);
		}
		
		return RevisedValueResult.succeeded(strResult);
	}
}
