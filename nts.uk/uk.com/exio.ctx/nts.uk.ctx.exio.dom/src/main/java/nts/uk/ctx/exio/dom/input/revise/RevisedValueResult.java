package nts.uk.ctx.exio.dom.input.revise;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 値の編集結果
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RevisedValueResult {
		
	/** 編集に成功 */
	private final boolean success;
	
	/** 編集値 */
	private final Optional<Object> revisedvalue;
	
	/** エラーメッセージID */
	private final Optional<String> errorMessageId;
	
	/**
	 * 編集に成功
	 * @param dataItem
	 * @return
	 */
	public static RevisedValueResult succeeded(String value) {
		return new RevisedValueResult(true, Optional.of(value), Optional.empty());
	}
	
	public static RevisedValueResult succeeded(long value) {
		return new RevisedValueResult(true, Optional.of(value), Optional.empty());
	}
	
	public static RevisedValueResult succeeded(BigDecimal value) {
		return new RevisedValueResult(true, Optional.of(value), Optional.empty());
	}
	
	public static RevisedValueResult succeeded(GeneralDate value) {
		return new RevisedValueResult(true, Optional.of(value), Optional.empty());
	}
	
	/**
	 * 編集に失敗
	 * @param errorMessageId
	 * @return
	 */
	public static RevisedValueResult failed(String errorMessageId) {
		return new RevisedValueResult(false, Optional.empty(), Optional.of(errorMessageId));
	}
}
