package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.MoneyFunc;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyFuncCommand {
	/** コード */
	private String verticalCalCd;

	/** 汎用縦計項目ID */
	private String verticalCalItemId;

	/** 順番 */
	private int dispOrder;

	/** 外部予算実績項目コード */
	private String externalBudgetCd;

	/** 勤怠項目ID */
	private String attendanceItemId;

	/** 予定項目ID */
	private String presetItemId;

	/** 演算子区分 */
	private int operatorAtr;
	
	/**
	 * toDomainMoney
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @param dispOrder
	 * @param externalBudgetCd
	 * @param attendanceItemId
	 * @param presetItemId
	 * @param operatorAtr
	 * @return
	 */
    public MoneyFunc toDomainMoney(String companyId, String verticalCalCd, String verticalCalItemId,  int dispOrder, String externalBudgetCd
    		,String attendanceItemId, String presetItemId, int operatorAtr){
    	return MoneyFunc.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, dispOrder, externalBudgetCd,attendanceItemId,presetItemId,operatorAtr);
    }

	
	
}
