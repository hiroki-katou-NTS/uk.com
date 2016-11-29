package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

@Value
public class DetailItemCommandBase {

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
	int correctFlag = 0;

	/**
	 * 社保対象区分
	 */
	int socialInsuranceAtr;

	/**
	 * 労保対象区分
	 */
	int laborInsuranceAtr;

	int linePostion;

	int columnPosition;

	Integer deductAtr;
	
	boolean created;

	public DetailItem toDomain() {
		return DetailItem.createFromJavaType(
				this.itemCode, 
				this.value, 
				this.correctFlag, 
				this.socialInsuranceAtr, 
				this.laborInsuranceAtr,
				this.categoryAtr,
				this.deductAtr,
				this.itemAtr
				);
	}
}
