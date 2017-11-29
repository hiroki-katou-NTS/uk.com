package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.TimeUnitFunc;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeUnitFuncDto {
	/** 会社ID */
    private String companyId;
    
    /**コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 順番 */
    private int dispOrder;
    
    /** 勤怠項目ID */
    private String attendanceItemId;
    
    /** 予定項目ID */
    private String presetItemId;
    
    /** 演算子区分 */
    private int operatorAtr;
    
    /**
     * fromDomain
     * @param func
     * @return
     */
    public static TimeUnitFuncDto fromDomain (TimeUnitFunc func ){
    	return new TimeUnitFuncDto(
    			func.getCompanyId(),
    			func.getVerticalCalCd(),
    			func.getVerticalCalItemId(),
    			func.getDispOrder(),
    			func.getAttendanceItemId(),
    			func.getPresetItemId(),
    			func.getOperatorAtr().value
    			);
    }
}
