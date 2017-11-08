package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTimeFunc;

/**
 * 
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
public class FormTimeFuncCommand {
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 予定項目ID */
    private String presetItemId;
    
    /* 勤怠項目ID */
    private String attendanceItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    /* 順番 */
    private int dispOrder;
    
    public FormTimeFunc toDomainFunc(String companyId, String verticalCalCd, String verticalCalItemId, String presetItemId, String attendanceItemId, 
    		String externalBudgetCd, int operatorAtr, int dispOrder){
    	return FormTimeFunc.createFromJavaType(companyId, verticalCalCd, verticalCalItemId, presetItemId, attendanceItemId, 
        		externalBudgetCd, operatorAtr, dispOrder);
    }
}
