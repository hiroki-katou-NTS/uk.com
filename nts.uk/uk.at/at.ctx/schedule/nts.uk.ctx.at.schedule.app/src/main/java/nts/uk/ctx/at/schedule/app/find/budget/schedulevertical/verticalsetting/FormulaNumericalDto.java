package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormulaNumericalDto {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    /* 順番 */
    private int dispOrder;

    /**
     * FormulaNumericalDto
     * @param domain
     * @return
     */
	public static FormulaNumericalDto fromDomain(FormulaNumerical domain) {
		return new FormulaNumericalDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getVerticalCalItemId(),
				domain.getExternalBudgetCd(),
				domain.getOperatorAtr().value,
				domain.getDispOrder()
			);
	}
}
