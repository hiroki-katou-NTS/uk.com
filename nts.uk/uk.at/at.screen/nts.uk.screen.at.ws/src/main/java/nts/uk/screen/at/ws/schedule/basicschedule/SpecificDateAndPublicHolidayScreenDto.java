package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class SpecificDateAndPublicHolidayScreenDto {
	List<GeneralDate> listWkpSpecificDate;
	List<GeneralDate> listComSpecificDate;
	List<GeneralDate> listPublicHoliday;
}
