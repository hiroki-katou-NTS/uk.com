package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto UKDesign.UniversalK.共通.CMF_補助機能.CMF004_データ復旧.F:データ復旧期間変更.F:アルゴリズム.画面項目セット.パラメータ.項目セット
 */
@Data
@AllArgsConstructor
public class ItemSetDto {
	
	/**
	 * カテゴリのテーブル
	 */
	private TableListDto categoryTable;
	
	/**
	 * システム担当区分
	 */
	private boolean systemChargeCategory;
	
}
