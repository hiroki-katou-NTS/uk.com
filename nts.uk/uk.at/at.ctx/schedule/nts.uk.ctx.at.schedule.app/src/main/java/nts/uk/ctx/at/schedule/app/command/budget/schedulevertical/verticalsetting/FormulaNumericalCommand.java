package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaNumericalCommand {
    
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /** 演算子区分 */
    private int operatorAtr;
    
    /** 順番 */
    private int dispOrder;
    
    public FormulaNumerical toDomainNumerical(String companyId, String verticalCalCd, String verticalCalItemIdr){
    	return FormulaNumerical.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, externalBudgetCd, operatorAtr, dispOrder);
    }
}
