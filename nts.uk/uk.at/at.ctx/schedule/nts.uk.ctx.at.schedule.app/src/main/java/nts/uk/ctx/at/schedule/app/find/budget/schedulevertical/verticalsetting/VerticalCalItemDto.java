package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;

/**
 * TanLV
 *
 */
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
 	
 	private int roundingProcessing;
 	
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
  	private List<FormulaNumericalDto> numerical;
  	
  	// G
  	private FormulaUnitpriceDto unitPrice;

  	/**
  	 * fromDomain
  	 * @param domain
  	 * @return
  	 */
	public static VerticalCalItemDto fromDomain(VerticalCalItem domain) {
		FormBuiltDto formBuiltDto = null;
		FormTimeDto formTimeDto = null;
		FormPeopleDto formPeopleDto = null;
		FormulaAmountDto formulaAmountDto = null;
		List<FormulaNumericalDto> numericalDto = null;
		FormulaUnitpriceDto unitPriceDto = null;
		
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
			numericalDto = domain.getNumerical().stream()
					.map(x-> FormulaNumericalDto.fromDomain(x))
					.collect(Collectors.toList());
		}
		
		if(domain.getUnitprice() != null) {
			unitPriceDto =  FormulaUnitpriceDto.fromDomain(domain.getUnitprice());
		}
		
		return new VerticalCalItemDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getItemId(),
				domain.getItemName(),
				domain.getCalculateAtr().value,
				domain.getDisplayAtr().value,
				domain.getCumulativeAtr(),
				domain.getAttributes().value,
				domain.getRounding(),
				domain.getRoundingProcessing(),
				domain.getDispOrder(),
				formBuiltDto,
				formTimeDto,
				formPeopleDto,
				formulaAmountDto,
				numericalDto,
				unitPriceDto
			);
	}
}
