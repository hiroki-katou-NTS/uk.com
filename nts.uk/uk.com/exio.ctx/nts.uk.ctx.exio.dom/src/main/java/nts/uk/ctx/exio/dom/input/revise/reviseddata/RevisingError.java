package nts.uk.ctx.exio.dom.input.revise.reviseddata;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;

/**
 * 
 * 編集エラー
 * 
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RevisingError {
	
	/** CSV行番号 */
	private int csvRawNumber;
	
	/** CSV項目名 */
	private String csvItemName;
	
	/** 受入項目名 */
	private String importItemName;
	
	/** 値 */
	private Object importValue;
	
	/** エラーメッセージ */
	private String errorMessageId;
	
	// 失敗ケースごとにコンストラクタメソッドを増やしていく
	// ↓　コレはベース
	public RevisingError faild(CsvItem csvItem) {
		return new RevisingError(
				csvItem.getCsvItemNo(), 
				csvItem.getCsvItemName(), 
				csvItem.getItemName(), 
				csvItem.getValue(), 
				"");
	}
}
