package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Setter
@Getter
@AllArgsConstructor
/**
 * データ削除動作管理
 */
public class ManagementDeletion extends AggregateRoot {

	// データ削除処理ID
	/** The deletion Id. */
	public String delId;

	// 中断するしない
	/** The interrupted flag. */
	public boolean isInterruptedFlg;
				
	// カテゴリトータルカウント
	/** The total category count. */
	public int totalCategoryCount;
		
	// カテゴリカウント
	/** The category count. */
	public int categoryCount;
		
	// エラー件数
	/** The errorCount. */
	public int errorCount;
		
	// 動作状態
	/** The operating condition. */
	public OperatingCondition operatingCondition;
		
	public static ManagementDeletion createFromJavatype(String delId, boolean isInterruptedFlg, int totalCategoryCount, 
			int categoryCount, int errorCount, int operatingCondition) {
		return new ManagementDeletion(delId, isInterruptedFlg, totalCategoryCount,categoryCount, errorCount,
				OperatingCondition.valueOf(operatingCondition));
	}
}
