package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.ExternalImportCodeConvert;

/**
 * 文字型編集
 */
@AllArgsConstructor
@Getter
public class StringRevise implements ReviseValue {
	
	/** 固定長編集する */
	private boolean usePadding;
	
	/** 固定長編集内容 */
	private Optional<Padding> padding;
	
	/** コード変換 */
	private Optional<ExternalImportCodeConvert> codeConvert;
	
	@Override
	public Object revise(String target) {
		
		String strResult = target;
		
		if(usePadding) {
			// 固定長編集をする場合
			if(padding.isPresent()) {
				strResult = this.padding.get().fix(strResult);
			}
		}
		
		if(codeConvert.isPresent()) {
			strResult = this.codeConvert.get().convert(strResult).toString();
		}
		
		return strResult;
	}
}
