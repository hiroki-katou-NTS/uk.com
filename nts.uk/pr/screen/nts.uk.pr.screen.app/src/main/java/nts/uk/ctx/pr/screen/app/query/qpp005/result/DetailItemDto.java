package nts.uk.ctx.pr.screen.app.query.qpp005.result;

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
	
	private boolean isCreated;

	public static DetailItemDto fromDomain(int categoryAtr, String itemCode, String itemName, Double value,
			DetailItemPositionDto itemPosition, boolean isCreated) {
		return new DetailItemDto(categoryAtr, itemCode, itemName, value, itemPosition, isCreated);

	}
}
