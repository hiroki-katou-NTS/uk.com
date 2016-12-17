package nts.uk.pr.file.infra.paymentdata.result;

import lombok.Data;
import lombok.Getter;

@Data
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

	int calculationMethod;

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

	@Getter
	boolean isCreated;

	@Getter
	public int itemType;

	public DetailItemDto(int categoryAtr, Integer itemAtr, String itemCode, String itemName, Double value,
			int calculationMethod, int correctFlag, int linePosition, int columnPosition, Integer deductAtr,
			Integer displayAtr, Integer taxAtr, Integer limitAmount, Double commuteAllowTaxImpose,
			Double commuteAllowMonth, Double commuteAllowFraction, boolean isCreated) {
		super();
		this.categoryAtr = categoryAtr;
		this.itemAtr = itemAtr;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.value = value;
		this.calculationMethod = calculationMethod;
		this.correctFlag = correctFlag;
		this.linePosition = linePosition;
		this.columnPosition = columnPosition;
		this.deductAtr = deductAtr;
		this.displayAtr = displayAtr;
		this.taxAtr = taxAtr;
		this.limitAmount = limitAmount;
		this.commuteAllowTaxImpose = commuteAllowTaxImpose;
		this.commuteAllowMonth = commuteAllowMonth;
		this.commuteAllowFraction = commuteAllowFraction;
		this.isCreated = isCreated;
	}

	public static DetailItemDto fromData(
			int categoryAtr, 
			Integer itemAtr, 
			String itemCode, 
			String itemName,
			Double value, 
			int calculationMethod, 
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
		return new DetailItemDto(
				categoryAtr, 
				itemAtr, 
				itemCode, 
				itemName, 
				value, 
				calculationMethod, 
				correctFlag,
				linePosition, 
				colPosition, 
				deductAtr, 
				displayAtr, 
				taxAtr, 
				limitAmount, 
				commuteAllowTaxImpose,
				commuteAllowMonth, 
				commuteAllowFraction, 
				isCreated);

	}

	public static DetailItemDto toData(
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
		return new DetailItemDto(
				categoryAtr, 
				itemAtr, 
				itemCode, 
				itemName, 
				value, 
				-1,
				correctFlag,
				linePosition, 
				colPosition, 
				deductAtr, 
				displayAtr, 
				taxAtr, 
				limitAmount, 
				commuteAllowTaxImpose,
				commuteAllowMonth, 
				commuteAllowFraction, 
				isCreated);
	}
	
	/**
	 * settup item type
	 */
	public void setItemType() {
		switch (this.itemAtr) {
		case 0:
			// 手入力
			if (this.calculationMethod == 0) {
				this.itemType = 0;
			} else {
				this.itemType = -1;
			}
			break;
		case 1:
			// 手入力
			if (this.calculationMethod == 0) {
				// 通勤費リンク
				if (this.taxAtr == 3 || this.taxAtr == 4) {
					this.itemType = 1;
				} else {
					this.itemType = 0;
				}
			} else {
				this.itemType = -1;
			}
			break;
		case 2:
		case 3:
			// 手入力
			if (this.calculationMethod == 0) {
				// 通勤費リンク
				if (this.taxAtr == 3 || this.taxAtr == 4) {
					this.itemType = 1;
				} else {
					this.itemType = 0;
				}
			} else {
				this.itemType = -1;
			}
			break;
		default:
			break;
		}
	}
}
