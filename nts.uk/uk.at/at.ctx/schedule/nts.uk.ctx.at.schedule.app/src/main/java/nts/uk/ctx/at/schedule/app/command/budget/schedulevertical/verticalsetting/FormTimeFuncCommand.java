package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTimeFunc;

/**
 * 
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormTimeFuncCommand {
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 順番 */
    private int dispOrder;
    
    /* 予定項目ID */
    private String presetItemId;
    
    /* 勤怠項目ID */
    private String attendanceItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    public FormTimeFunc toDomainFunc(String companyId, String verticalCalCd, String verticalCalItemId, int dispOrder, String presetItemId, String attendanceItemId, 
    		String externalBudgetCd, int operatorAtr){
    	return FormTimeFunc.createFromJavaType(companyId, verticalCalCd, verticalCalItemId, dispOrder, presetItemId, attendanceItemId, 
        		externalBudgetCd, operatorAtr);
    }
}
