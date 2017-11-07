package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormBuilt;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeople;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTime;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaAmount;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;

@Data
@AllArgsConstructor
public class VerticalCalItemCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String itemId;
    
    /* 項目名 */
    private String itemName;
    
    /* 計算区分 */
    private int calculateAtr;
    
    /* 表示区分 */
    private int displayAtr;
    
    /* 累計区分 */
    private int cumulativeAtr;
    
    /* 属性 */
    private int attributes;
    
    // 端数処理
 	private int rounding;
 	
 	private int dispOrder;
 	
 	private FormBuiltCommand formBuilt;
 	
 	private FormTimeCommand formTime;
 	
 	private FormPeopleCommand formPeople;
 	
 	private FormulaAmountCommand formulaAmount;
 	
 	private FormulaNumericalCommand formulaNumerical;
 	
 	
 	public VerticalCalItem toDomainCalItem(String companyId, String verticalCalCd, String itemId){
 		FormBuilt built = this.formBuilt != null
 				? this.formBuilt.toDomainFormBuilt(companyId, verticalCalCd, itemId)
				: null;
		FormTime time = this.formTime != null
 				? this.formTime.toDomainFormTime(companyId, verticalCalCd, itemId)
				: null;
 		FormulaAmount amount = this.formulaAmount != null
 				? this.formulaAmount.toDomainFormAmount(companyId, verticalCalCd, itemId)
				: null;
 		FormPeople formPeople1 = this.formPeople != null
 				? this.formPeople.toDomainFormPeople(companyId, verticalCalCd, itemId)
				: null;
 		FormulaNumerical formula = this.formulaNumerical != null
 				? this.formulaNumerical.toDomainNumerical(companyId, verticalCalCd, itemId)
 						: null;
		return VerticalCalItem.createFromJavatype(companyId, verticalCalCd, itemId, itemId, calculateAtr, displayAtr, cumulativeAtr, attributes, rounding, 
				dispOrder, built, time, formPeople1,amount);
 	}
}
