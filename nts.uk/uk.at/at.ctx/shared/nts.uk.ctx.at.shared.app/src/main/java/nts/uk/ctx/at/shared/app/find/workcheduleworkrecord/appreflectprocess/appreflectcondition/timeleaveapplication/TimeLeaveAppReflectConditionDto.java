package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeLeaveAppReflectConditionDto {
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
}
