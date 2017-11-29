package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * TanLV
 * 計算区分
 * 
 */
@AllArgsConstructor
@Getter
public class FormBuilt {
	/** 会社ID  */
    private String companyId;
    
    /** コード**/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 設定方法 */
    private SettingMethod settingMethod1;
    
    private String verticalCalItem1;
    
    /** 縦計入力項目 */
    private String verticalInputItem1;
    
    /** 設定方法 */
    private SettingMethod settingMethod2;
    
    private String verticalCalItem2;
    
    /** 縦計入力項目 */
    private String verticalInputItem2;
    
    /** 演算子区分 */
    private OperatorAtr operatorAtr;

    /**
     * Create From Javatype
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @param settingMethod1
     * @param verticalCalItem1
     * @param verticalInputItem1
     * @param settingMethod2
     * @param verticalCalItem2
     * @param verticalInputItem2
     * @param operatorAtr
     * @return
     */
	public static FormBuilt createFromJavaTypeFormBuilt(String companyId, String verticalCalCd, String verticalCalItemId, int settingMethod1, String verticalCalItem1, 
			String verticalInputItem1, int settingMethod2, String verticalCalItem2, String verticalInputItem2, int operatorAtr) {

		return new FormBuilt(companyId, 
				verticalCalCd, 
				verticalCalItemId, 
				EnumAdaptor.valueOf(settingMethod1, SettingMethod.class),
				verticalCalItem1, 
				verticalInputItem1, 
				EnumAdaptor.valueOf(settingMethod2, SettingMethod.class),
				verticalCalItem2, 
				verticalInputItem2, 
				EnumAdaptor.valueOf(operatorAtr, OperatorAtr.class));
	}
}
