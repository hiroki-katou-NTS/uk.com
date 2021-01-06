package nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkpcounterlaborcostandtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaborCostAndTime {

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
