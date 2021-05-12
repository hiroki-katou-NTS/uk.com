package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.NumberOfPeopleByEachWorkMethod;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggreratePeopleMethodOutput {

	public Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<String>>> countWork;

	public Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<ShiftMasterCode>>> shift;
}
