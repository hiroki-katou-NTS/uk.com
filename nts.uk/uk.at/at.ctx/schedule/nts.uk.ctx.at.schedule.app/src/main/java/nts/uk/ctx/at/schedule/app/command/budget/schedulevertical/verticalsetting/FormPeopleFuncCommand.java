package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.ActualDisplayAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CategoryAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeopleFunc;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.OperatorAtr;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class FormPeopleFuncCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* カテゴリ区分 */
    private int categoryAtr;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    /* 順番 */
    private int dispOrder;
    
    public FormPeopleFunc toDomainFunc(String companyId, String verticalCalCd, String verticalCalItemId, String externalBudgetCd, int categoryAtr, int operatorAtr, int dispOrder){
    	return FormPeopleFunc.createFromJavaType(companyId, verticalCalCd, verticalCalItemId, externalBudgetCd, categoryAtr, operatorAtr, dispOrder);
    }
}
