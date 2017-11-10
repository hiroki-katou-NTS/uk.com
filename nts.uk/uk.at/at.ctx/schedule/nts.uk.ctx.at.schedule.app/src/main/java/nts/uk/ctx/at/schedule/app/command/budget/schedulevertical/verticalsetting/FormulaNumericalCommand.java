package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;
@Data
@AllArgsConstructor
public class FormulaNumericalCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    /* 順番 */
    private int dispOrder;
    
    public FormulaNumerical toDomainNumerical(String companyId, String verticalCalCd, String verticalCalItemIdr){
    	return FormulaNumerical.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, externalBudgetCd, operatorAtr, dispOrder);
    }
}
