package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * TanLV
 * 人数計算式
 * 
 */
@AllArgsConstructor
@Getter
public class FormPeopleFunc {
	/** 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 順番 */
    private int dispOrder;
    
    /** 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /** カテゴリ区分 */
    private CategoryAtr categoryAtr;
    
    /** 演算子区分 */
    private OperatorAtr operatorAtr;
    
    /**
     * create From Java Type
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @param externalBudgetCd
     * @param categoryAtr
     * @param operatorAtr
     * @param dispOrder
     * @return
     * author: Hoang Yen
     */
    public static FormPeopleFunc createFromJavaType(String companyId, String verticalCalCd, 
    												String verticalCalItemId, int dispOrder, String externalBudgetCd, 
    												int categoryAtr, int operatorAtr){
    	return new FormPeopleFunc(companyId, 
    								verticalCalCd, 
    								verticalCalItemId,
    								dispOrder,
    								externalBudgetCd, 
    								EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class), 
    								EnumAdaptor.valueOf(operatorAtr, OperatorAtr.class));
    }
}
