package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

@Value
public class DetailItemCommandBase {

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
	private int correctFlag = 0;

	/**
	 * 社保対象区分
	 */
	private int socialInsuranceAtr;

	/**
	 * 労保対象区分
	 */
	private int laborInsuranceAtr;

	private int deductionAtr;

	private int linePostion;

	private int columnPosition;

	private boolean created;

	public DetailItem toDomain() {
		return DetailItem.createFromJavaType(
				this.getItemCode(), 
				this.getValue(), 
				this.getCorrectFlag(), 
				this.getSocialInsuranceAtr(), 
				this.getLaborInsuranceAtr(),
				this.getCategoryAtr(),
				this.getDeductionAtr()
				);
	}
}
