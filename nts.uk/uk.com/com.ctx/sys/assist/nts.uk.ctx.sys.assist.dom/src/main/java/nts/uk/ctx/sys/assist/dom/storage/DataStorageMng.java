package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * データ保存動作管理
 */
@AllArgsConstructor
@Getter
public class DataStorageMng extends AggregateRoot {

	/**
	 * データ保存処理ID
	 */
	public String storeProcessingId;

	/**
	 * 中断するしない
	 */
	public NotUseAtr doNotInterrupt;

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
	public OperatingCondition operatingCondition;

}
