package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Value;

@Value
public class StdAcceptCondSetCommand {

	/**
	 * システム種類
	 */
	private int systemType;
	
	/**
	 * 外部受入条件コード
	 */
	private String conditionSettingCode;

	/**
	 * 外部受入条件名称
	 */
	private String conditionSettingName;
	
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
	private Integer csvDataItemLineNumber;
	
	/**
	 * CSVデータの取込開始行
	 */
	private Integer csvDataStartLine;
	
	/**
	 * 既存データの削除方法
	 */
	private Integer deleteExistDataMethod;
	
	private int action;

	
}
