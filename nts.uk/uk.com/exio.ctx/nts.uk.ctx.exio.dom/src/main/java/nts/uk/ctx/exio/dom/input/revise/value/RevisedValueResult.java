package nts.uk.ctx.exio.dom.input.revise.value;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItem;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RevisedValueResult {
		
	/** 編集に成功 */
	private final boolean success;
	
	/** 1項目分のデータ */
	private final Optional<DataItem> dataItem;
	
	/** エラーメッセージID */
	private final Optional<String> errorMessageId;
	
	/**
	 * 編集に成功
	 * @param dataItem
	 * @return
	 */
	public RevisedValueResult succeeded(DataItem dataItem) {
		return new RevisedValueResult(true, Optional.of(dataItem), Optional.empty());
	}
	
	/**
	 * 編集に失敗
	 * @param errorMessageId
	 * @return
	 */
	public RevisedValueResult failed(String errorMessageId) {
		return new RevisedValueResult(false, Optional.empty(), Optional.of(errorMessageId));
	}
}
