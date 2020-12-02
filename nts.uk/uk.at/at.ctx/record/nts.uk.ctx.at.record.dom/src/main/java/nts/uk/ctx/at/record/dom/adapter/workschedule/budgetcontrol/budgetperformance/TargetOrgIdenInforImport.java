package nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TargetOrgIdenInforImport {

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
