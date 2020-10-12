package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

/**
 * Dto カテゴリのテーブル
 */
@Data
@AllArgsConstructor
public class TableListDto {
	
	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * カテゴリ名
	 */
	private String categoryName;

	/**
	 * システム種類
	 */
	private Integer systemType;

	/**
	 * データ保存処理ID
	 */
	private String dataStorageProcessingId;

	/**
	 * テーブルの情報
	 */
	private List<TableInfoDto> tableList;

	/**
	 * 保存セット名称
	 */
	private String saveSetName;

	/**
	 * 保存ファイル名
	 */
	private String saveFileName;

	/**
	 * 保存形態
	 */
	private String saveForm;

	/**
	 * 保存日付From
	 */
	private String saveDateFrom;

	/**
	 * 保存日付To
	 */
	private String saveDateTo;

	/**
	 * 保存時保存範囲
	 */
	private Integer storageRangeSaved;

	/**
	 * 保存期間区分
	 */
	private Integer retentionPeriodCls;

	/**
	 * 内部ファイル名
	 */
	private String internalFileName;

	/**
	 * 別会社区分
	 */
	private Integer anotherComCls;

	/**
	 * 圧縮ファイル名
	 */
	private String compressedFileName;

	/**
	 * 復旧対象可不可
	 */
	private Integer canNotBeOld;

	/**
	 * 復旧対象選択
	 */
	private Integer selectionTargetForRes;

	/**
	 * 画面保存期間
	 */
	private String screenRetentionPeriod;

	/**
	 * 補足説明
	 */
	private String supplementaryExplanation;

	/**
	 * 親テーブル有無
	 */
	private Integer hasParentTblFlg;

	/**
	 * 調査用保存
	 */
	private Integer surveyPreservation;
	
	/**
	 * パターンコード
	 */
	private String patternCode;

	public static TableListDto fromDomain(TableList domain) {
		List<TableInfoDto> tableList = Arrays.asList(new TableInfoDto(domain.getTableNo(), domain.getTableJapaneseName(), domain.getTableEnglishName()));
		return new TableListDto(
				domain.getCategoryId(), 
				domain.getCategoryName(), 
				domain.getSystemType().value,
				domain.getDataStorageProcessingId(), 
				tableList,
				domain.getSaveSetName(), 
				domain.getSaveFileName(), 
				domain.getSaveForm(), 
				domain.getSaveDateFrom().orElse(null),
				domain.getSaveDateTo().orElse(null), 
				domain.getStorageRangeSaved().value, 
				domain.getRetentionPeriodCls().value,
				domain.getInternalFileName(), 
				domain.getAnotherComCls().value, 
				domain.getCompressedFileName(),
				domain.getCanNotBeOld().orElse(null), 
				domain.getSelectionTargetForRes().orElse(null),
				domain.getScreenRetentionPeriod().orElse(null),
				domain.getSupplementaryExplanation().orElse(null),
				domain.getHasParentTblFlg().value,
				domain.getSurveyPreservation().value,
				domain.getPatternCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TableListDto) {
			return ((TableListDto) obj).getCategoryId().equals(categoryId)
					&& ((TableListDto) obj).getSystemType().equals(systemType);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return categoryId.hashCode() + systemType.hashCode();
	}
}
