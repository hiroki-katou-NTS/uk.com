/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import lombok.Data;

/**
 * @author nam.lh
 *
 */
@Data
public class DataStorageMngDto {
	/**
	 * データ保存処理ID
	 */
	public String storeProcessingId;

	/**
	 * 中断するしない
	 */
	public int doNotInterrupt;

	/**
	 * カテゴリカウント
	 */
	public int categoryCount;

	/**
	 * カテゴリトータルカウント
	 */
	public int categoryTotalCount;

	/**
	 * エラー件数
	 */
	public int errorCount;

	/**
	 * 動作状態
	 */
	public int operatingCondition;
}
