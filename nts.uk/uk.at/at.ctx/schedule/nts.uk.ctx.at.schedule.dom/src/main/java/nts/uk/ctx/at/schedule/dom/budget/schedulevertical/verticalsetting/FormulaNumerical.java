package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 数値計算式
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class FormulaNumerical {
	/** 会社ID **/
    private String companyId;
    
    /**コード**/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID **/
    private String verticalCalItemId;
    
    /** 順番 **/
    private int dispOrder;
    
    /** 外部予算実績項目コード **/
    private String externalBudgetCd;
    
    /** 演算子区分 **/
    private OperatorAtr operatorAtr;
    
    public static FormulaNumerical createFromJavatype(String companyId, String verticalCalCd, String verticalCalItemId, int dispOrder,
			String externalBudgetCd, int operatorAtr) {
		return new FormulaNumerical(companyId, verticalCalCd, verticalCalItemId,
				dispOrder,
				externalBudgetCd, 
				EnumAdaptor.valueOf(operatorAtr, OperatorAtr.class));
	}
}
