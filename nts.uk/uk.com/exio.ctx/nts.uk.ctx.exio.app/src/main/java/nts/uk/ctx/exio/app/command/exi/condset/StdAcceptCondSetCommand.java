package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class StdAcceptCondSetCommand {

	/**
	 * システム種類
	 */
	private int systemType;
	
	/**
	 * 外部受入条件コード
	 */
	private String conditionSetCd;

	/**
	 * 外部受入条件名称
	 */
	private String conditionSetName;
	
	/**
	 * 既存データの削除
	 */
	private int deleteExistData;

	/**
	 * 受入モード
	 */
	private int acceptMode;

	/**
	 * チェック完了
	 */
	private int checkCompleted;

	/**
	 * 外部受入カテゴリID
	 */
	private String categoryId;
	
	/**
	 * CSVデータの項目名行
	 */
	private int csvDataLineNumber;
	
	/**
	 * CSVデータの取込開始行
	 */
	private int csvDataStartLine;
	
	/**
	 * 既存データの削除方法
	 */
	private int deleteExtDataMethod;

}
