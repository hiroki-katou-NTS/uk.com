package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

public interface SpecHolidayCheckConRepository {
	Optional<SpecHolidayCheckCon> getSpecHolidayCheckConById(String errorAlarmCheckID); 

	void addSpecHolidayCheckCon (SpecHolidayCheckCon specHolidayCheckCon);
	
	void updateSpecHolidayCheckCon (SpecHolidayCheckCon specHolidayCheckCon);
	
	void deleteSpecHolidayCheckCon (String errorAlarmCheckID);
}
