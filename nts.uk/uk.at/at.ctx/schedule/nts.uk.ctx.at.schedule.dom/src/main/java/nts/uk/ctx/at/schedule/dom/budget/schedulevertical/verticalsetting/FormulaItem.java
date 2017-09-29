package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FormulaItem {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 縦計入力項目 */
    private int verticalInputItem;
    
    /* 設定方法 */
    private SettingMethod settingMethod;
    
    /* 演算子区分 */
    private OperatorAtr operatorAtr;
    
    /* 順番 */
    private int dispOrder;
}
