package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.List;

@AllArgsConstructor
@Getter
public class AlarmScheduleCheckCdt implements ExtractionCondition {

    private List<String> listExtractionIDs;

    private List<String> listOptionalIDs;

}
