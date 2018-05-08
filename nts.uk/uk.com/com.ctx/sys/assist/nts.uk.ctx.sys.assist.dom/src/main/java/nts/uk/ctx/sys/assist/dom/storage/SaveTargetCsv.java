/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.sys.assist.dom.category.CategoryName;
import nts.uk.ctx.sys.assist.dom.category.RecoverFormCompanyOther;
import nts.uk.ctx.sys.assist.dom.category.RecoveryStorageRange;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author nam.lh
 *
 */
@AllArgsConstructor
@Getter
public class SaveTargetCsv {
	/**
	 * データ保存処理ID
	 */
	public String storeProcessingId;

	/**
	 * 保存形態
	 */
	private StorageForm saveForm;

	/**
	 * 保存セットコード
	 */
	private SaveSetCode saveSetCode;

	/**
	 * 保存名称
	 */
	private SaveName saveName;

	/**
	 * 補足説明
	 */
	private Explanation suppleExplanation;

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * カテゴリ名
	 */
	private CategoryName categoryName;

	/**
	 * 保存期間区分
	 */
	private TimeStore timeStore;

	/**
	 * 復旧保存範囲
	 */
	private RecoveryStorageRange recoveryStorageRange;

	/**
	 * 調査用保存
	 */
	private NotUseAtr saveForInvest;

	/**
	 * 別会社区分
	 */
	private RecoverFormCompanyOther otherCompanyCls;

	
}
