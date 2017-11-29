package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormBuilt;

/**
 * 
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormBuiltCommand {

	/**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 設定方法 */
    private int settingMethod1;
    
    private String verticalCalItem1;
    
    /** 縦計入力項目 */
    private String verticalInputItem1;
    
    /** 設定方法 */
    private int settingMethod2;
    
    private String verticalCalItem2;
    
    /** 縦計入力項目 */
    private String verticalInputItem2;
    
    /** 演算子区分 */
    private int operatorAtr;
    
    /**
     * toDomainFormBuilt
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @return
     */
    public FormBuilt toDomainFormBuilt(String companyId, String verticalCalCd, String verticalCalItemId){
    	return FormBuilt.createFromJavaTypeFormBuilt(companyId, verticalCalCd, verticalCalItemId, settingMethod1, verticalCalItem1, verticalInputItem1, settingMethod2,
    			verticalCalItem2, verticalInputItem2, operatorAtr);
    }	
}
