package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.category.RecoverFormCompanyOther;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Data
@AllArgsConstructor
@Value
public class SurfaceItemDto {

	/**
	 * 圧縮ファイル名
	 */
	private String compressedFileName;
	/**
	 * 保存セットコード
	 */
	private String saveSetCode;
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
	private RecoverFormCompanyOther anotherComCls;
	/**
	 * カテゴリ名
	 */
	private String categoryName;
	/**
	 * 保存期間区分
	 */
	private TimeStore retentionPeriodCls;
	/**
	 * 保存日付From
	 */
	private GeneralDate saveDateFrom;
	/**
	 * 保存日付To
	 */
	private GeneralDate saveDateTo;
	/**
	 * 保存時保存範囲
	 */
	private String storageRangeSaved;

	public static SurfaceItemDto fromDomain(TableList domain) {
		return new SurfaceItemDto(domain.getCompressedFileName(), domain.getSaveSetCode(), domain.getSaveSetName(),
				domain.getSupplementaryExplanation(), domain.getAnotherComCls(), domain.getCategoryName(),
				domain.getRetentionPeriodCls(), domain.getSaveDateFrom(), domain.getSaveDateTo(),
				domain.getStorageRangeSaved());
	}
}
