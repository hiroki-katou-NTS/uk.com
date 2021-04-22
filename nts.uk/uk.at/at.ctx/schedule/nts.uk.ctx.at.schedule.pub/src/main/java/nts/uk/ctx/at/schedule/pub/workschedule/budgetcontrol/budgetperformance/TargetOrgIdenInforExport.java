package nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TargetOrgIdenInforExport {

    /**
     * 単位
     */
    private int unit;

    /**
     * 職場ID
     */
    private Optional<String> workplaceId;

    /**
     * 職場グループID
     */
    private Optional<String> workplaceGroupId;

}
