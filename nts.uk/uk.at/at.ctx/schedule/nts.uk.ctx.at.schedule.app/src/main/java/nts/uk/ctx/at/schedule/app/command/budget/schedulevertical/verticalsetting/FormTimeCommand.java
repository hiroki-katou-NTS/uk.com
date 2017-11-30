package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTime;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTimeFunc;

/**
 * 
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormTimeCommand {
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** カテゴリ区分 */
    private int categoryIndicator;
    
    /** 実績表示区分 */
    private int actualDisplayAtr;
    
    private List<FormTimeFuncCommand> lstFormTimeFunc;
    
    /**
     * toDomainFormTime
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @return
     */
    public FormTime toDomainFormTime(String companyId, String verticalCalCd, String verticalCalItemId){
    	List<FormTimeFunc> formFormTimeFuncLst = this.lstFormTimeFunc != null
    			? this.lstFormTimeFunc.stream().map(c -> c.toDomainFunc(companyId, 
																	c.getVerticalCalCd(), 
																	c.getVerticalCalItemId(),
																	c.getDispOrder(),
																	c.getPresetItemId(),
																	c.getAttendanceItemId(),
																	c.getExternalBudgetCd(),
																	c.getOperatorAtr())).collect(Collectors.toList())
				: null;
																	
		return FormTime.createFromJavaType(companyId, verticalCalCd, verticalCalItemId, categoryIndicator, actualDisplayAtr, formFormTimeFuncLst);
    }	
}
