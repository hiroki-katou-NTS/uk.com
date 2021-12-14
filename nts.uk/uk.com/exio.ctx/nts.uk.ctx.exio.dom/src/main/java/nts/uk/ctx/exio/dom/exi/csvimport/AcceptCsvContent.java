package nts.uk.ctx.exio.dom.exi.csvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.condset.DeleteExistDataMethod;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AcceptCsvContent {
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
	 * 削除条件
	 */
	private DeleteExistDataMethod deleteCondFlg;

	/**
	 * 受入モード
	 */
	private AcceptMode acceptMode;
	
	/**
	 * 処理日時
	 */
	private GeneralDateTime processDate;
	
	/**
	 * テーブル名
	 */
	private String tableName;
	
	/**
	 * カラム名
	 */
	private String columnName;
	
	/**
	 * 値
	 */
	private String itemValue;
	
}
