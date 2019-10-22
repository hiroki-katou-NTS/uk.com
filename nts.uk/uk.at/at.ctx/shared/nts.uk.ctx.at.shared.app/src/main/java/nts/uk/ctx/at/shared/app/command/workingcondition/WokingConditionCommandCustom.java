package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WokingConditionCommandCustom {
	private WorkingConditionItem workingConditionItem;
	private List<MyCustomizeException>  ex = new ArrayList<>();
}
