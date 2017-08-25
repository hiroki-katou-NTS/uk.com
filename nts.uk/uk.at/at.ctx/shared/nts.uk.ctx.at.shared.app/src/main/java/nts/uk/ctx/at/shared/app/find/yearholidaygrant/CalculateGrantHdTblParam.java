package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class CalculateGrantHdTblParam {
	List<GrantHolidayTblDto> grantHolidayTblList;
	int useSimultaneousGrant;
	GeneralDate referDate;
	GeneralDate simultaneousGrantDate;
}
