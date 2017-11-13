package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeopleFunc;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormPeopleFuncCommand {
    
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /** カテゴリ区分 */
    private int categoryAtr;
    
    /** 演算子区分 */
    private int operatorAtr;
    
    /** 順番 */
    private int dispOrder;
    
    public FormPeopleFunc toDomainFunc(String companyId, String verticalCalCd, String verticalCalItemId, String externalBudgetCd, int categoryAtr, int operatorAtr, int dispOrder){
    	return FormPeopleFunc.createFromJavaType(companyId, verticalCalCd, verticalCalItemId, externalBudgetCd, categoryAtr, operatorAtr, dispOrder);
    }
}
