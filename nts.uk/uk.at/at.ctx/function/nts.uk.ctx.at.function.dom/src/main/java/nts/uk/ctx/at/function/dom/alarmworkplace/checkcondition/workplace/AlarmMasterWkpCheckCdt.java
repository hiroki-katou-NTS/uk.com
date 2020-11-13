package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.List;

@AllArgsConstructor
@Getter
public class AlarmMasterWkpCheckCdt implements ExtractionCondition {

    private List<String> alarmCheckWkpID;

}
