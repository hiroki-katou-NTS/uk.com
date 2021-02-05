package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Data;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
 * The class Standard acceptance condition setting command.<br>
 * Command 受入条件設定（定型）
 */
@Data
public class StdAcceptCondSetCommand implements StdAcceptCondSet.MementoGetter {

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 外部受入条件コード
	 */
	private String conditionSetCode;

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
	private Integer acceptMode;

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
	 * 文字コード
	 */
	private Integer characterCode;

	/**
	 * 既存データの削除方法
	 */
	private Integer deleteExistDataMethod;

	/**
	 * The action
	 */
	private int action;

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return null;
	}

	/**
	 * Gets check completed.
	 *
	 * @return the check completed
	 */
	@Override
	public Integer getCheckCompleted() {
		return null;
	}

}
