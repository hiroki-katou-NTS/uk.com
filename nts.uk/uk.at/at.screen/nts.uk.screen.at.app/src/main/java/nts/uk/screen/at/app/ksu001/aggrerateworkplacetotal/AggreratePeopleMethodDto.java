package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.NumberOfPeopleByEachWorkMethod;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.WorkTimeSettingDto;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggreratePeopleMethodDto {

	public Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<WorkTimeSettingDto>>> countWork;

	public Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<ShiftMasterDto>>> shift;
}
