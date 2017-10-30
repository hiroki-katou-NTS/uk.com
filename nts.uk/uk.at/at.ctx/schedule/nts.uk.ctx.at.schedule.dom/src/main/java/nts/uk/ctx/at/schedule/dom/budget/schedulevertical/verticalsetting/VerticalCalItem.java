package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.Getter;

@Getter
public class VerticalCalItem {
	/** 会社ID **/
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
 	
 	private int dispOrder;

	public VerticalCalItem(String companyId, String verticalCalCd, String itemId, String itemName,
			CalculateAtr calculateAtr, DisplayAtr displayAtr, CumulativeAtr cumulativeAtr, Attributes attributes,
			Rounding rounding, int dispOrder) {
		
		this.companyId = companyId;
		this.verticalCalCd = verticalCalCd;
		this.itemId = itemId;
		this.itemName = itemName;
		this.calculateAtr = calculateAtr;
		this.displayAtr = displayAtr;
		this.cumulativeAtr = cumulativeAtr;
		this.attributes = attributes;
		this.rounding = rounding;
		this.dispOrder = dispOrder;
	}
}
