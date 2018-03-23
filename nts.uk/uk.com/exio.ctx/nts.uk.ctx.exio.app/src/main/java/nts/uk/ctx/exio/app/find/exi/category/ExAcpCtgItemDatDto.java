package nts.uk.ctx.exio.app.find.exi.category;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 外部受入カテゴリ項目データ
 */
@AllArgsConstructor
@Value
public class ExAcpCtgItemDatDto {

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * 項目NO
	 */
	private int itemNo;

	/**
	 * 項目名
	 */
	private String itemName;

	private int requiredCls;

}
