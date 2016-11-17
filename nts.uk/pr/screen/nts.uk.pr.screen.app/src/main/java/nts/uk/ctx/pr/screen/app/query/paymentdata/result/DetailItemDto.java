package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import lombok.Getter;
import lombok.Value;

@Value
public class DetailItemDto {

	/**
	 * category atr
	 */
	@Getter
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
	@Getter
	private Double value;

	/**
	 * 修正フラグ
	 */
	@Getter
	private int correctFlag;

	/**
	 * 社保対象区分
	 */
	@Getter
	private int socialInsuranceAtr;

	/**
	 * 労保対象区分
	 */
	@Getter
	private int laborInsuranceAtr;

	@Getter
	private DetailItemPositionDto itemPostion;
	
	
	
}
