package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.proto.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

@Getter
@Setter
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
	int correctFlag;

	/**
	 * 社保対象区分
	 */
	int socialInsuranceAtr;

	/**
	 * 労保対象区分
	 */
	int laborInsuranceAtr;

	int linePosition;

	int columnPosition;

	Integer deductAtr;
	
	Integer displayAtr;
	
	boolean created;

	public DetailItem toDomain(Integer linePosition) {
		if (displayAtr == DisplayAtr.NO_DISPLAY.value) {
			this.linePosition = -1;
		}else {
			this.linePosition = linePosition;
		}
		
		if ("".equals(this.value)) {
			throw new BusinessException("入力にエラーがあります。");	
		}
		
		return DetailItem.createFromJavaType(
				this.itemCode, 
				this.value, 
				this.correctFlag, 
				this.socialInsuranceAtr, 
				this.laborInsuranceAtr,
				this.categoryAtr,
				this.deductAtr,
				this.linePosition,
				this.columnPosition
				);
	}
}
