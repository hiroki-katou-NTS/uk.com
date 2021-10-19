package nts.uk.ctx.exio.dom.exi.csvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * workエラーログ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AcceptCsvErrorContent {
	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * 非同期タスクID
	 */
	private String asynTaskId;
	
	/**
	 * 受入行番号
	 */
	private int lineNumber;
	
	/**
	 * カテゴリ項目NO
	 */
	private int itemNo;
	
	/**
	 * 画面表示項目名
	 */
	private String itemName;
	
	/**
	 * CSV受入値
	 */
	private String itemValue;
	
	/**
	 * エラー内容
	 */
	private String errorContent;
	
}
