package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import lombok.Getter;
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

	/**
	 * 修正フラグ
	 */
	private int correctFlag;

	/**
	 * 社保対象区分
	 */
	private int socialInsuranceAtr;

	/**
	 * 労保対象区分
	 */
	private int laborInsuranceAtr;

	private DetailItemPositionDto itemPostion;

	public static DetailItemDto fromDomain(int categoryAtr, String itemCode, String itemName, ) {
		return new DetailItemDto(categoryAtr, itemCode, itemName, value, correctFlag, socialInsuranceAtr,
				laborInsuranceAtr, itemPostion);

	}

}
