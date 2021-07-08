package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.RangeOfValue;

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
	public Object revise(String target) {
		
		String strResult = target;
		
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			if(rangeOfValue.isPresent()) {
				strResult = this.rangeOfValue.get().extract(strResult);
			}
		}
		
		if(useFixedLength) {
			// 固定長編集をする場合
			if(fixedLength.isPresent()) {
				strResult = this.fixedLength.get().fix(strResult);
			}
		}
		
		return strResult;
	}
}
