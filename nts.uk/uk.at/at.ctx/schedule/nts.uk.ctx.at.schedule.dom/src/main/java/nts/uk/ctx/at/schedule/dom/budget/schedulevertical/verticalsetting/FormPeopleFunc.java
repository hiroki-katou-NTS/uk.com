package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FormPeopleFunc {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* カテゴリ区分 */
    private CategoryAtr categoryAtr;
    
    /* 演算子区分 */
    private OperatorAtr operatorAtr;
    
    /* 順番 */
    private int dispOrder;
}
