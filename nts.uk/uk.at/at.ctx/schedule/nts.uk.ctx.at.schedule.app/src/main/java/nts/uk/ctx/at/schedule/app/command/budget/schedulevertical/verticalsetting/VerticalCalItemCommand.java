package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerticalCalItemCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String itemId;
    
    /* 項目名 */
    private String itemName;
    
    /* 計算区分 */
    private int calculateAtr;
    
    /* 表示区分 */
    private int displayAtr;
    
    /* 累計区分 */
    private int cumulativeAtr;
    
    /* 属性 */
    private int attributes;
    
    // 端数処理
 	private int rounding;
 	
 	private int dispOrder;
}
