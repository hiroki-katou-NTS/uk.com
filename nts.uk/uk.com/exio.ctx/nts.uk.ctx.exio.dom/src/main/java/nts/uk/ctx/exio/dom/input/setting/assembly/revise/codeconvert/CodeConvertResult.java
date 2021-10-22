package nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * コードの変換結果
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeConvertResult {
	
	/** 変換に成功 */
	private final boolean success;
	
	/** 変換値 */
	private final Optional<CodeConvertValue> convertedValue;
	
	/**
	 * 変換に成功
	 * @param dataItem
	 * @return
	 */
	public static CodeConvertResult succeeded(CodeConvertValue value) {
		return new CodeConvertResult(true, Optional.of(value));
	}
	
	/**
	 * 変換に失敗
	 * @param errorMessageId
	 * @return
	 */
	public static CodeConvertResult failed() {
		return new CodeConvertResult(false, Optional.empty());
	}
}
