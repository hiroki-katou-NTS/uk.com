package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class CalculateGrantHdTblParam {
	List<CalculateDateDto> grantHolidayTblList;
	int useSimultaneousGrant;
	GeneralDate referDate;
	GeneralDate simultaneousGrantDate;
}
