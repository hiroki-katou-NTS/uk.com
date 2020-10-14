package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;


@Getter
@Setter
@AllArgsConstructor
/**
 * データ削除の対象社員
 */
public class CategoryDeletion {
	
	// データ削除処理ID
	/** The deletion Id */
	private String delId;

	// 社員ID
	/** The category Id. */
	private String categoryId;

	// 自動設定対象期間
	/** The period deletion. */
	private GeneralDate periodDeletion;
	
	/**
	 * システム種類
	 */
	private int systemType;
	
	public static CategoryDeletion createFromJavatype(String delId, 
			String categoryId, GeneralDate periodDeletion, int systemType) {
		return new CategoryDeletion(delId, categoryId, periodDeletion, systemType);
	}
}