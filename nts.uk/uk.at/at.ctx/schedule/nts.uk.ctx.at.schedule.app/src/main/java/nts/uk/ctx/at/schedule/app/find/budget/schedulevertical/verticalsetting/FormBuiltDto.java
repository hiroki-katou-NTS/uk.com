package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormBuilt;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormBuiltDto {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 設定方法 */
    private int settingMethod1;
    
    private String verticalCalItem1;
    
    /* 縦計入力項目 */
    private String verticalInputItem1;
    
    /* 設定方法 */
    private int settingMethod2;
    
    private String verticalCalItem2;
    
    /* 縦計入力項目 */
    private String verticalInputItem2;
    
    /* 演算子区分 */
    private int operatorAtr;

    /**
     * FormBuiltDto
     * @param domain
     * @return
     */
	public static FormBuiltDto fromDomain(FormBuilt domain) {
		return new FormBuiltDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getVerticalCalItemId(),
				domain.getSettingMethod1().value,
				domain.getVerticalCalItem1(),
				domain.getVerticalInputItem1(),
				domain.getSettingMethod2().value,
				domain.getVerticalCalItem2(),
				domain.getVerticalInputItem2(),
				domain.getOperatorAtr().value
			);
	}
}
