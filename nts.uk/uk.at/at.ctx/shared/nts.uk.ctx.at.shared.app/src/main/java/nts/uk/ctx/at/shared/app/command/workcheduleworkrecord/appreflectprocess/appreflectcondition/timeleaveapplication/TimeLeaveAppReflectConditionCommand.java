package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeLeaveAppReflectConditionCommand {
    /**
     * 60H超休
     */
    private int superHoliday60H;

    /**
     * 介護
     */
    private int nursing;

    /**
     * 子看護
     */
    private int childNursing;

    /**
     * 時間代休
     */
    private int substituteLeaveTime;

    /**
     * 時間年休
     */
    private int annualVacationTime;

    /**
     * 時間特別休暇
     */
    private int specialVacationTime;
    
    public static TimeLeaveAppReflectConditionCommand fromDomain(TimeLeaveAppReflectCondition timeLeaveAppReflectCondition) {
        return new TimeLeaveAppReflectConditionCommand(
                timeLeaveAppReflectCondition.getSuperHoliday60H().value, 
                timeLeaveAppReflectCondition.getNursing().value, 
                timeLeaveAppReflectCondition.getChildNursing().value, 
                timeLeaveAppReflectCondition.getSubstituteLeaveTime().value,
                timeLeaveAppReflectCondition.getAnnualVacationTime().value, 
                timeLeaveAppReflectCondition.getSpecialVacationTime().value
                );
    }
}
