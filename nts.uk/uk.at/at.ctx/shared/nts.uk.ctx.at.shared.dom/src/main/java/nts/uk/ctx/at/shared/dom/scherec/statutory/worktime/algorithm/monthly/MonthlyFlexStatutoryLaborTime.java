package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

@AllArgsConstructor
@Getter
public class MonthlyFlexStatutoryLaborTime {

	/** 法定時間 */
    private MonthlyEstimateTime statutorySetting;
	
    /** 所定時間 */
    private MonthlyEstimateTime specifiedSetting;
    
    /** 週平均時間 */
    private MonthlyEstimateTime weekAveSetting;
    
    /**
     * 法定労働時間、所定労働時間、週平均時間すべてに0を返す
     * @return
     */
    public static MonthlyFlexStatutoryLaborTime zeroMonthlyFlexStatutoryLaborTime() {
    	return new MonthlyFlexStatutoryLaborTime(
    			new MonthlyEstimateTime(0),
    			new MonthlyEstimateTime(0),
    			new MonthlyEstimateTime(0));
    }
}
