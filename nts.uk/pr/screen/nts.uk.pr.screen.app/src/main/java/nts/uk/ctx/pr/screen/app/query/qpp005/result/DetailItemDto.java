package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Value;

@Value
public class DetailItemDto {

	/**
	 * category atr
	 */
	int categoryAtr;

	
	int itemAtr; 
	
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

	/**
	 * 行
	 */
	int linePosition;

	/**
	 * 列
	 */
	int columnPosition;

	boolean isCreated;
	
	public static DetailItemDto fromDomain(int categoryAtr, int itemAtr, String itemCode, String itemName, Double value,
			int linePosition, int colPosition, boolean isCreated) {
		return new DetailItemDto(categoryAtr, itemAtr, itemCode, itemName, value, linePosition, colPosition, isCreated);

	}
}
