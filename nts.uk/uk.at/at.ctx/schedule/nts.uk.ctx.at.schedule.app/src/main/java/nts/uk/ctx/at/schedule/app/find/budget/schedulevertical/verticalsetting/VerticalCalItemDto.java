package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerticalCalItemDto {
	/** 会社ID */
    private String companyId;
    
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String itemId;
    
    /** 項目名 */
    private String itemName;
    
    /** 計算区分 */
    private int calculateAtr;
    
    /** 表示区分 */
    private int displayAtr;
    
    /** 累計区分 */
    private int cumulativeAtr;
    
    /** 属性 */
    private int attributes;
    
    /**端数処理 */
 	private int rounding;
 	
 	/** 順番 */
 	private int dispOrder;
 	
 	// B
  	private FormBuiltDto formBuilt;
  	
  	// C
  	private FormTimeDto formTime;
  	
  	// D
  	private FormPeopleDto formPeople;
  	
  	// E
  	private FormulaAmountDto formulaAmount;
  	
  	// F
  	private FormulaNumericalDto numerical;

	public static VerticalCalItemDto fromDomain(VerticalCalItem domain) {
		FormBuiltDto formBuiltDto = null;
		FormTimeDto formTimeDto = null;
		FormPeopleDto formPeopleDto = null;
		FormulaAmountDto formulaAmountDto = null;
		FormulaNumericalDto numericalDto = null;
		
		if(domain.getFormBuilt() != null) {
			formBuiltDto =  FormBuiltDto.fromDomain(domain.getFormBuilt());
		}
		
		if(domain.getFormTime() != null) {
			formTimeDto =  FormTimeDto.fromDomain(domain.getFormTime());
		}
		
		if(domain.getFormPeople() != null) {
			formPeopleDto =  FormPeopleDto.fromDomain(domain.getFormPeople());
		}
		
		if(domain.getFormulaAmount() != null) {
			formulaAmountDto =  FormulaAmountDto.fromDomain(domain.getFormulaAmount());
		}
		
		if(domain.getNumerical() != null) {
			numericalDto =  FormulaNumericalDto.fromDomain(domain.getNumerical());
		}
		
		return new VerticalCalItemDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getItemId(),
				domain.getItemName(),
				domain.getCalculateAtr().value,
				domain.getDisplayAtr().value,
				domain.getCumulativeAtr().value,
				domain.getAttributes().value,
				domain.getRounding().value,
				domain.getDispOrder(),
				formBuiltDto,
				formTimeDto,
				formPeopleDto,
				formulaAmountDto,
				numericalDto
			);
	}
}
