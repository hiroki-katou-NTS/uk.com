package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;

@AllArgsConstructor
@Getter
public class VerticalCalItem extends DomainObject {
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
 	
 	// B
 	private FormBuilt formBuilt;
 	
 	// C
 	private FormTime formTime;
 	
 	// D
 	private FormPeople formPeople;
 	
 	// E
 	private FormulaAmount formulaAmount;
 	
 	private FormulaNumerical numerical;
 	
 	/**
 	 * author: Hoang Yen
 	 */
 	public static VerticalCalItem createFromJavatype(String companyId, String verticalCalCd, 
 													String itemId, String itemName, 
 													int calculateAtr, int displayAtr, 
 													int cumulativeAtr, int attributes, 
 													int rounding, int dispOrder, 
 													FormBuilt formBuilt,
 													FormTime formTime,
 													FormPeople formPeople,
 													FormulaAmount formulaAmount,
 													FormulaNumerical numerical){
 		return new VerticalCalItem(companyId, verticalCalCd, itemId, itemName, 
 				EnumAdaptor.valueOf(calculateAtr, CalculateAtr.class), 
 				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class), 
 				EnumAdaptor.valueOf(cumulativeAtr, CumulativeAtr.class), 
 				EnumAdaptor.valueOf(attributes, Attributes.class),
 				EnumAdaptor.valueOf(rounding, Rounding.class),
 				dispOrder,
 				formBuilt,
 				formTime,
 				formPeople,
 				formulaAmount,
 				numerical);
 	}
 	
 	public void validate(int index) {
 		if(this.calculateAtr == CalculateAtr.FORMULA_SETTING) {
 			if (this.formBuilt == null) {
				throw new BusinessException("Msg_111", String.valueOf(index));
			}
 		} else {
 			switch (this.attributes) {
				case TIME:
					if (this.formTime == null) {
						throw new BusinessException("Msg_111", String.valueOf(index));
					}
					break;
					
				case AMOUNT:
		//			if (this.formPeople == null) {
		//				throw new BusinessException("Msg_111", String.valueOf(index));
		//			}
					break;
					
				case NUMBER_OF_PEOPLE:
					if (this.formPeople == null) {
						throw new BusinessException("Msg_111", String.valueOf(index));
					}
					break;
					
				case NUMBER:
		//			if (this.formPeople == null) {
		//				throw new BusinessException("Msg_111", String.valueOf(index));
		//			}
					break;
					
				case AVERAGE_PRICE:
		//			if (this.formPeople == null) {
		//				throw new BusinessException("Msg_111", String.valueOf(index));
		//			}
					break;
					
				default:
					break;
			}
 		}
 	}
}
