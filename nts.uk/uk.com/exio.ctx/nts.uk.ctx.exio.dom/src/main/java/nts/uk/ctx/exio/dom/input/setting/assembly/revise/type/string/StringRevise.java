package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.CodeConvertValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert.ExternalImportCodeConvert;
import nts.gul.util.Either;

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
	public Either<ErrorMessage, ?> revise(ReviseValue.Require require, String target) {
		
		String strResult = target;
		
		if(usePadding && !require.getImportableItem().isZeroPaddedCode()) {
			// 固定長編集をする場合（ゼロ埋めコードであれば設定を無視する）
			strResult = this.padding.get().fix(strResult);
		}
		
		if(codeConvert.isPresent()) {
			return this.codeConvert.get().convert(strResult)
					.map(CodeConvertValue::v);
		}
		
		return Either.right(strResult);
	}
}
