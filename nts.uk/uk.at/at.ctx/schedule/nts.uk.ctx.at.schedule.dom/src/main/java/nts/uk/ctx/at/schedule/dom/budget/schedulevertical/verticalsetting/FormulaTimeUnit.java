package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FormulaTimeUnit {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 単位 */
    private RoundingTime roundingTime;
    
    /* 端数処理 */
    private Rounding roundingAtr;
    
    /* 単価 */
    private UnitPrice unitPrice;
    
    /* 単価 */
    private ActualDisplayAtr actualDisplayAtr;
}
