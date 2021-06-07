package nts.uk.ctx.exio.dom.input.revise;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertResult;

/**
 * 項目の編集結果
 */
@AllArgsConstructor
public class RevisedItemResult {
	
	/** 受入項目NO */
	@Getter
	private final int itemNo;
	
	/** 値の編集結果 */
	private final RevisedValueResult revisedValue;
	
	/** コード変換結果 */
	private final Optional<CodeConvertResult> convertedValue;
	
	/**
	 * 項目の編集に成功しているか
	 * @return
	 */
	public boolean isSuccess() {
		if(convertedValue.isPresent()) {
			return revisedValue.isSuccess() && convertedValue.get().isSuccess();
		}
		return revisedValue.isSuccess();
	}
	
	/**
	 * 値の取得
	 * @return
	 */
	public Optional<Object> getObjectValue() {
		if(convertedValue.isPresent()) {
			return Optional.of(convertedValue.get().getConvertedValue().get().toString());
		}
		return revisedValue.getRevisedvalue();
	}
}
