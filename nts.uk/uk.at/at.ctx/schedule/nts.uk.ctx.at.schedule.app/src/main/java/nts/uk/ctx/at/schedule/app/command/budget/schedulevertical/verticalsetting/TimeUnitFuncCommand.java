package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.TimeUnitFunc;
@Data
@AllArgsConstructor
public class TimeUnitFuncCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 順番 */
    private int dispOrder;
    
    /* 勤怠項目ID */
    private String attendanceItemId;
    
    /* 予定項目ID */
    private String presetItemId;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    public TimeUnitFunc toDomainUnitFunc(String companyId, String verticalCalCd, String verticalCalItemId, int dispOrder, String attendanceItemId, String presetItemId, int operatorAtr){
    	return TimeUnitFunc.createFromJavatype(companyId, verticalCalCd, verticalCalItemId, dispOrder, attendanceItemId, presetItemId, operatorAtr);
    }
}
