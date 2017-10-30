package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FormBuilt {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 設定方法 */
    private SettingMethod settingMethod1;
    
    private int verticalCalItem1;
    
    /* 縦計入力項目 */
    private int verticalInputItem1;
    
    /* 設定方法 */
    private SettingMethod settingMethod2;
    
    private int verticalCalItem2;
    
    /* 縦計入力項目 */
    private int verticalInputItem2;
    
    /* 演算子区分 */
    private OperatorAtr operatorAtr;
}
