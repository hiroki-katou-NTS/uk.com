package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaAmount;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaMoney;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaTimeUnit;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaAmountCommand {

	/** コード */
	private String verticalCalCd;

	/** 計算式 */
	private String verticalCalItemId;
	
	/** 計算方法区分 */
	private int calMethodAtr;
	
	private FormulaMoneyCommand moneyFunc;
	
	private FormulaTimeUnitCommand timeUnit;
	
	/**
	 * toDomainFormAmount
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @return
	 */
	public FormulaAmount toDomainFormAmount(String companyId, String verticalCalCd, String verticalCalItemId){
		FormulaMoney moneyFunc = this.moneyFunc != null
 				? this.moneyFunc.toDomainFormMoney(companyId, verticalCalCd, verticalCalItemId)
				: null;
		FormulaTimeUnit formulaAmount = this.timeUnit != null
 				? this.timeUnit.toDomainFormTimeUnit(companyId, verticalCalCd, verticalCalItemId)
				: null;
		return FormulaAmount.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, calMethodAtr, moneyFunc, formulaAmount);
}
}
