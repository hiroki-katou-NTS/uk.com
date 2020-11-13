package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlarmMasterBasicCheckCdt implements ExtractionCondition {

    private List<String> alarmCheckWkpID;

}
