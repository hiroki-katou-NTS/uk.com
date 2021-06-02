package nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExtBudgetActualValuesExport {
    private ExtBudgetType extBudgetType;
    private Integer value;
}
