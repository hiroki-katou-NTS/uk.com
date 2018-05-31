package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

@AllArgsConstructor
@Getter
public class MonthlyFlexStatutoryLaborTime {

    private MonthlyEstimateTime statutorySetting;

    private MonthlyEstimateTime specifiedSetting;
    
    /**
     * 法定労働時間、所定労働時間ともに0を返す
     * @return
     */
    public static MonthlyFlexStatutoryLaborTime zeroMonthlyFlexStatutoryLaborTime() {
    	return new MonthlyFlexStatutoryLaborTime(new MonthlyEstimateTime(0),new MonthlyEstimateTime(0));
    }
    
}
