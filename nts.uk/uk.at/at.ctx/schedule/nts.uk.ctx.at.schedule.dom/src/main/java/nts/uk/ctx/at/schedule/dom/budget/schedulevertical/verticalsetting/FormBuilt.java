package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

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
    private BigDecimal verticalInputItem1;
    
    /* 設定方法 */
    private SettingMethod settingMethod2;
    
    private int verticalCalItem2;
    
    /* 縦計入力項目 */
    private BigDecimal verticalInputItem2;
    
    /* 演算子区分 */
    private OperatorAtr operatorAtr;

	public static FormBuilt createFromJavaTypeFormBuilt(String companyId, String verticalCalCd, String verticalCalItemId, int settingMethod1, int verticalCalItem1, 
			BigDecimal verticalInputItem1, int settingMethod2, int verticalCalItem2, BigDecimal verticalInputItem2, int operatorAtr) {

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
