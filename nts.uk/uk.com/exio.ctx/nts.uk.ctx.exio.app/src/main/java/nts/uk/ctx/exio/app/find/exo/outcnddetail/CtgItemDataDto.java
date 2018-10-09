package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;

@AllArgsConstructor
@Value
public class CtgItemDataDto {
	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * 項目NO
	 */
	private int itemNo;

	/**
	 * テーブル
	 */
	private String tableName;

	/**
	 * 表示テーブル名
	 */
	private String displayTableName;

	/**
	 * 項目名
	 */
	private String itemName;

	/**
	 * データ型
	 */
	private int dataType;

	/**
	 * 検索値コード
	 */
	private String searchValueCd;

	public static CtgItemDataDto fromDomain(CtgItemData domain) {
		CtgItemDataDto dto = new CtgItemDataDto(domain.getCategoryId().v(), domain.getItemNo().v(),
				domain.getTableName(), domain.getDisplayTableName(), domain.getItemName().v(), domain.getDataType().value,
				domain.getSearchValueCd().orElse(null));
		return dto;
	}
}
