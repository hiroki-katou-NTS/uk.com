package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaMoney;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.MoneyFunc;
@Data
@AllArgsConstructor
public class FormulaMoneyCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /*カテゴリ区分 */
    private int categoryIndicator;
    
    /* 実績表示区分 */
    private int actualDisplayAtr;
    
    private List<MoneyFuncCommand> lstMoney;
    

	public FormulaMoney toDomainFormMoney(String companyId, String verticalCalCd, String verticalCalItemId){
		 List<MoneyFunc> formPeopleLst = this.lstMoney != null
	    			? this.lstMoney.stream().map(c -> c.toDomainMoney(companyId, 
	    					c.getVerticalCalCd(), 
	    					c.getVerticalCalItemId(), 
	    					c.getDispOrder(), 
	    					c.getExternalBudgetCd(), 
	    					c.getAttendanceItemId(), 
	    					c.getPresetItemId(), 
	    					c.getOperatorAtr())).collect(Collectors.toList())
					: null;
			return FormulaMoney.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, categoryIndicator, actualDisplayAtr, formPeopleLst);
	    }
}
