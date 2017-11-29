package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaMoney;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormulaMoneyDto {
    private String companyId;
    
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /**カテゴリ区分 */
    private int categoryIndicator;
    
    /** 実績表示区分 */
    private int actualDisplayAtr;
    
    private List<MoneyFuncDto> lstMoney;
    
    /**
     * fromDomain
     * @param money
     * @return
     */
    	public static FormulaMoneyDto fromDomain(FormulaMoney money){
    		if(money == null) {return null;}
    		List<MoneyFuncDto> items = money.getLstMoney().stream()
    				.map(x-> MoneyFuncDto.fromDomain(x))
    				.collect(Collectors.toList());
    		return new FormulaMoneyDto(
    				money.getCompanyId(),
    				money.getVerticalCalCd(),
    				money.getVerticalCalItemId(),
    				money.getCategoryIndicator().value,
    				money.getActualDisplayAtr().value,
    				items
    				);
    	}

}
