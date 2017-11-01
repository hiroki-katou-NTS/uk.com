package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HalfDay;

@AllArgsConstructor
@Getter
public class FormPeople {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 実績表示区分 */
    private ActualDisplayAtr actualDisplayAtr;
    
    /**
     * create From Java Type
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @param actualDisplayAtr
     * @return
     * author: Hoang Yen
     */
    public static FormPeople createFromJavaType(String companyId, 
												String verticalCalCd, 
												String verticalCalItemId, 
												int actualDisplayAtr){
    	return new FormPeople(companyId, verticalCalCd, 
    							verticalCalItemId, EnumAdaptor.valueOf(actualDisplayAtr, ActualDisplayAtr.class));
    }
}
