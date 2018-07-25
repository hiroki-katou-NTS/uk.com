package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;

@AllArgsConstructor
@Value
public class CtgItemDataTableDto {
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

	public static CtgItemDataTableDto fromDomain(CtgItemData domain) {
		CtgItemDataTableDto dto = new CtgItemDataTableDto(domain.getCategoryId().v(), domain.getItemNo().v(),
				domain.getTableName(), domain.getDisplayTableName(), domain.getItemName(), domain.getDataType().value,
				domain.getSearchValueCd().orElse(null));
		return dto;
	}
}
