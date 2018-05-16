package nts.uk.ctx.sys.assist.app.find.deletedata;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletion;


@AllArgsConstructor
@Value
public class ManagementDelDto {

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
	public int operatingCondition;

	public static ManagementDelDto fromDomain(ManagementDeletion domain) {
		return new ManagementDelDto(domain.getDelId(), domain.isInterruptedFlg(),
				domain.getTotalCategoryCount(), domain.getCategoryCount(), domain.getErrorCount(),
				domain.getOperatingCondition().value);
	}

}
