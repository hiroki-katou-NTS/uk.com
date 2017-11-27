package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaNumericalCommand {
    
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 順番 */
    private int dispOrder;
    
    /** 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /** 演算子区分 */
    private int operatorAtr;
    
    /**
     * toDomainNumerical
     * @param companyId
     * @return
     */
    public FormulaNumerical toDomainNumerical(String companyId){
    	return FormulaNumerical.createFromJavatype(companyId, this.verticalCalCd, this.verticalCalItemId, this.dispOrder, this.externalBudgetCd, this.operatorAtr);
    }
}
