package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * TanLV
 *
 */
@AllArgsConstructor
@Getter
public class FormPeople {
	/** 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 実績表示区分 */
    private ActualDisplayAtr actualDisplayAtr;
    private List<FormPeopleFunc> lstPeopleFunc;
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
												int actualDisplayAtr,
												List<FormPeopleFunc> lstPeopleFunc){
    	return new FormPeople(companyId, verticalCalCd, 
    							verticalCalItemId, EnumAdaptor.valueOf(actualDisplayAtr, ActualDisplayAtr.class),
    							lstPeopleFunc);
    }
}
