package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import lombok.Value;

@Value
public class DetailItemDto {

	/**
	 * category atr
	 */
	private int categoryAtr;

	/**
	 * 項目コード
	 */
	private String itemCode;

	/**
	 * 項目名
	 */
	private String itemName;

	/**
	 * 値
	 */
	private Double value;

	private DetailItemPositionDto itemPosition;

	public static DetailItemDto fromDomain(int categoryAtr, String itemCode, String itemName, Double value,
			DetailItemPositionDto itemPosition) {
		return new DetailItemDto(categoryAtr, itemCode, itemName, value, itemPosition);

	}
}
