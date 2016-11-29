package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Value;

@Value
public class DetailItemDto {

	/**
	 * category atr
	 */
	int categoryAtr;

	Integer itemAtr;

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

	Integer deductAtr;

	boolean isCreated;

	public static DetailItemDto fromDomain(int categoryAtr, Integer itemAtr, String itemCode, String itemName, Double value,
			int linePosition, int colPosition, Integer deductAtr, boolean isCreated) {
		return new DetailItemDto(categoryAtr, itemAtr, itemCode, itemName, value, linePosition, colPosition, deductAtr,
				isCreated);

	}
}
