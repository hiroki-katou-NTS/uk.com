package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpCounterLaborCostAndTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWkpLaborCostAndTimeCommand {

    private int LaborCostAndTimeType;

    /**
     * 	利用区分
     */
    private int useClassification;

    /**
     * 	時間
     */
    private int time;

    /**
     * 人件費
     */
    private int laborCost;

    /**
     * 予算
     */
    private Integer budget;

}
