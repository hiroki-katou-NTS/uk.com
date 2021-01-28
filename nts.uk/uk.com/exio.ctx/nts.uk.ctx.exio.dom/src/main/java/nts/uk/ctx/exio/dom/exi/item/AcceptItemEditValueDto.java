package nts.uk.ctx.exio.dom.exi.item;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;

@AllArgsConstructor
@Data
public class AcceptItemEditValueDto {
	/**
	 * 項目型
	 *//*
	private ItemType itemType;
	*//**
	 * 文字編集値
	 *//*
	private Optional<String> chrValue;
	*//**
	 * 時刻・時間・数字編集値
	 *//*
	private Optional<Double> timeValue;
	*//**
	 * 日付編集値
	 *//*
	private Optional<GeneralDate> dateValue;*/
	private Object editValue;
	
	/**
	 * チェック結果：True：エラー、False：エラーなし
	 */
	private boolean resultCheck;
	/**
	 * チェックのエラー内容
	 */
	private String editError;

}
