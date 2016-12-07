package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Getter;
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
	 * 修正フラグ
	 */
	int correctFlag;
	
	/**
	 * 行
	 */
	int linePosition;

	/**
	 * 列
	 */
	int columnPosition;

	Integer deductAtr;

	Integer displayAtr;

	/**
	 * 課税区分
	 */
	Integer taxAtr;

	private Integer limitAmount;

	@Getter
	private Double commuteAllowTaxImpose;

	@Getter
	private Double commuteAllowMonth;

	@Getter
	private Double commuteAllowFraction;

	boolean isCreated;

	public static DetailItemDto fromDomain(
			int categoryAtr, 
			Integer itemAtr, 
			String itemCode, 
			String itemName,
			Double value, 
			int correctFlag,
			int linePosition, 
			int colPosition, 
			Integer deductAtr, 
			Integer displayAtr, 
			Integer taxAtr,
			Integer limitAmount, 
			Double commuteAllowTaxImpose, 
			Double commuteAllowMonth, 
			Double commuteAllowFraction,
			boolean isCreated) {
		return new DetailItemDto(categoryAtr, itemAtr, itemCode, itemName, value, correctFlag, linePosition, colPosition, deductAtr,
				displayAtr, taxAtr, limitAmount, commuteAllowTaxImpose, commuteAllowMonth, commuteAllowFraction,
				isCreated);

	}
}
