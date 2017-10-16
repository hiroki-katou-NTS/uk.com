package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;

@AllArgsConstructor
@Getter
public class VerticalCalItem {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String itemId;
    
    /* 項目名 */
    private String itemName;
    
    /* 計算区分 */
    private CalculateAtr calculateAtr;
    
    /* 表示区分 */
    private DisplayAtr displayAtr;
    
    /* 累計区分 */
    private CumulativeAtr cumulativeAtr;
    
    /* 属性 */
    private Attributes attributes;
    
    // 端数処理
 	private Rounding rounding;
}
