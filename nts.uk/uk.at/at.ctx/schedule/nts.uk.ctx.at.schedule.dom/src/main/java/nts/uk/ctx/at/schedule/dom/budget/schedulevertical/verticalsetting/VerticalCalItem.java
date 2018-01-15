package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;

/**
 * TanLV
 * 汎用縦計項目
 */
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
    private int cumulativeAtr;
    
    /* 属性 */
    private Attributes attributes;
    
    // 端数処理
 	private int rounding;
 	
 	private int roundingProcessing;
 	
 	private int dispOrder;
 	
 	// B
 	private FormBuilt formBuilt;
 	
 	// C
 	private FormTime formTime;
 	
 	// D
 	private FormPeople formPeople;
 	
 	// E
 	private FormulaAmount formulaAmount;
 	
 	// F
 	private List<FormulaNumerical> numerical;
 	
 	//G
 	private FormulaUnitprice unitprice;
 	
 	/**
 	 * author: Hoang Yen
 	 */
 	public static VerticalCalItem createFromJavatype(String companyId, String verticalCalCd, 
 													String itemId, String itemName, 
 													int calculateAtr, int displayAtr, 
 													int cumulativeAtr, int attributes, 
 													int rounding, int roundingProcessing,
 													int dispOrder, 
 													FormBuilt formBuilt,
 													FormTime formTime,
 													FormPeople formPeople,
 													FormulaAmount formulaAmount,
 													List<FormulaNumerical> numerical,
 													FormulaUnitprice unitprice){
 		return new VerticalCalItem(companyId, verticalCalCd, itemId, itemName, 
 				EnumAdaptor.valueOf(calculateAtr, CalculateAtr.class), 
 				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class), 
 				cumulativeAtr, 
 				EnumAdaptor.valueOf(attributes, Attributes.class),
 				rounding,
 				roundingProcessing,
 				dispOrder,
 				formBuilt,
 				formTime,
 				formPeople,
 				formulaAmount,
 				numerical,
 				unitprice);
 	}
 	
 	/**
 	 * Validate
 	 * @param index
 	 */
 	public void validate(int index) {
 		if(this.calculateAtr == CalculateAtr.FORMULA_SETTING) {
 			if (this.formBuilt == null) {
				throw new BusinessException("Msg_418", String.valueOf(index));
			}
 		} else {
 			switch (this.attributes) {
				case TIME:
					if (this.formTime == null || CollectionUtil.isEmpty(this.formTime.getLstFormTimeFunc())) {
						throw new BusinessException("Msg_418", String.valueOf(index));
					}
					break;
					
				case AMOUNT:
					if (this.formulaAmount == null || CollectionUtil.isEmpty(this.formulaAmount.getMoneyFunc().getLstMoney()) && CollectionUtil.isEmpty(this.formulaAmount.getTimeUnit().getLstTimeUnitFuncs())) {
						throw new BusinessException("Msg_418", String.valueOf(index));
					}
					break;
					
				case NUMBER_OF_PEOPLE:
					if (this.formPeople == null || CollectionUtil.isEmpty(this.formPeople.getLstPeopleFunc())) {
						throw new BusinessException("Msg_418", String.valueOf(index));
					}
					break;
					
				case NUMBER:
					if (CollectionUtil.isEmpty(this.numerical)) {
						throw new BusinessException("Msg_418", String.valueOf(index));
					}
					break;
					
				case AVERAGE_PRICE:
					if (this.unitprice == null) {
						throw new BusinessException("Msg_418", String.valueOf(index));
					}
					break;
					
				default:
					break;
			}
 		}
 	}
}
