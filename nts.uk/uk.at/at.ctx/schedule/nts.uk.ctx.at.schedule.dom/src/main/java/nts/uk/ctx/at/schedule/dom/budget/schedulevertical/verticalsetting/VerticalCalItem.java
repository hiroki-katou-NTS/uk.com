package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
@AllArgsConstructor
@Getter
public class VerticalCalItem extends AggregateRoot{
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
 	private FormPeople formPeople;
 	
 	private FormulaAmount formulaAmount;
 	
//	public VerticalCalItem(String companyId, String verticalCalCd, String itemId, String itemName,
//			CalculateAtr calculateAtr, DisplayAtr displayAtr, CumulativeAtr cumulativeAtr, Attributes attributes,
//			Rounding rounding, int dispOrder, FormPeople formPeople, List<FormPeopleFunc> lstPeopleFunc) {
//		
//		this.companyId = companyId;
//		this.verticalCalCd = verticalCalCd;
//		this.itemId = itemId;
//		this.itemName = itemName;
//		this.calculateAtr = calculateAtr;
//		this.displayAtr = displayAtr;
//		this.cumulativeAtr = cumulativeAtr;
//		this.attributes = attributes;
//		this.rounding = rounding;
//		this.dispOrder = dispOrder;
//	}
 	
 	/**
 	 * author: Hoang Yen
 	 */
 	public static VerticalCalItem createFromJavatype(String companyId, String verticalCalCd, 
 													String itemId, String itemName, 
 													int calculateAtr, int displayAtr, 
 													int cumulativeAtr, int attributes, 
 													int rounding, int dispOrder, 
 													FormPeople formPeople,
 													FormulaAmount formulaAmount){
 		return new VerticalCalItem(companyId, verticalCalCd, itemId, itemName, 
 				EnumAdaptor.valueOf(calculateAtr, CalculateAtr.class), 
 				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class), 
 				EnumAdaptor.valueOf(cumulativeAtr, CumulativeAtr.class), 
 				EnumAdaptor.valueOf(attributes, Attributes.class),
 				EnumAdaptor.valueOf(rounding, Rounding.class),
 				dispOrder,
 				formPeople,
 				formulaAmount);
 	}
}
