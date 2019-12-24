package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class WorkingConditionItemWithEnumList {
	private WorkingConditionItem workingItem;
	private Map<String, Object> enumLst;
}
