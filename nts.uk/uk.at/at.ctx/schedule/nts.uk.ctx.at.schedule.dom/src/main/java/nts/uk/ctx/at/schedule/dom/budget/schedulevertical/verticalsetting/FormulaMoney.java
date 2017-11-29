package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 金額計算式
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class FormulaMoney {
	/** 会社ID **/
    private String companyId;
    
    /**コード**/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID **/
    private String verticalCalItemId;
    
    /**カテゴリ区分 **/
    private CategoryIndicator categoryIndicator;
    
    /** 実績表示区分 */
    private ActualDisplayAtr actualDisplayAtr;
    
    private List<MoneyFunc> lstMoney;
    
    public static FormulaMoney createFromJavatype(String companyId, String verticalCalCd, String verticalCalItemId,
			int categoryIndicator, int actualDisplayAtr, List<MoneyFunc> lstMoney) {
		return new FormulaMoney(companyId, verticalCalCd, verticalCalItemId,
				EnumAdaptor.valueOf(categoryIndicator, CategoryIndicator.class), 
				EnumAdaptor.valueOf(actualDisplayAtr, ActualDisplayAtr.class),
				lstMoney);
	}
}
