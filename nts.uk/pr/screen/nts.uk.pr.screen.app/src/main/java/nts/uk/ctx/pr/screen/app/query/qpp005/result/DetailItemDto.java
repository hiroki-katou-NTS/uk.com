package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Value;

@Value
public class DetailItemDto {

	/**
	 * category atr
	 */
	int categoryAtr;

	/**
	 * 項目コード
	 */
	String itemCode;

	/**
	 * 項目名
	 */
	String itemName;

	/**
	 * 値
	 */
	Double value;

	DetailItemPositionDto itemPosition;

	boolean isCreated;

	public static DetailItemDto fromDomain(int categoryAtr, String itemCode, String itemName, Double value,
			DetailItemPositionDto itemPosition, boolean isCreated) {
		return new DetailItemDto(categoryAtr, itemCode, itemName, value, itemPosition, isCreated);

	}
}
