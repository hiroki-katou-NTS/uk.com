package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@AllArgsConstructor
@Value
public class SurfaceItemDto {

	/**
	 * 圧縮ファイル名
	 */
	private String compressedFileName;
	/**
	 * パターンコード
	 */
	private String patternCode;
	/**
	 * 保存セット名称
	 */
	private String saveSetName;
	/**
	 * 補足説明
	 */
	private String supplementaryExplanation;
	/**
	 * 別会社区分
	 */
	private int anotherComCls;
	/**
	 * カテゴリID
	 */
	private String categoryId;
	/**
	 * テーブルNo
	 */
	private int tableNo;
	/**
	 * データ保存処理ID
	 */
	private String dataStorageProcessingId;
	/**
	 * カテゴリ名
	 */
	private String categoryName;
	/**
	 * 保存期間区分
	 */
	private int retentionPeriodCls;
	/**
	 * 保存日付From
	 */
	private String saveDateFrom;
	/**
	 * 保存日付To
	 */
	private String saveDateTo;
	/**
	 * 復旧対象可不可
	 */
	private int canNotBeOld;
	/**
	 * 保存時保存範囲
	 */
	private int storageRangeSaved;
	/**
	 * 保存形態
	 */
	private String saveForm;
	
	/**
	 * システム種類
	 */
	private int systemType;

	public static SurfaceItemDto fromDomain(TableList domain) {
		return new SurfaceItemDto(domain.getCompressedFileName(), domain.getPatternCode(),
				domain.getSaveSetName(), domain.getSupplementaryExplanation().orElse(""),
				domain.getAnotherComCls().value, domain.getCategoryId(), domain.getTableNo(),
				domain.getDataStorageProcessingId(), domain.getCategoryName(), domain.getRetentionPeriodCls().value,
				domain.getSaveDateFrom().orElse(null), domain.getSaveDateTo().orElse(null),
				domain.getCanNotBeOld().orElse(0), domain.getStorageRangeSaved().value, domain.getSaveForm(),
				domain.getSystemType().value);
	}
}
