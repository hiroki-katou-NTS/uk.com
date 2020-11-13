package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.List;

@AllArgsConstructor
@Getter
public class AlarmMonthlyCheckCdt implements ExtractionCondition {

    private List<String> listExtractionIDs;

    private List<String> listOptionalIDs;

}
