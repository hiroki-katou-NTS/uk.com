package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;

public interface AttendanceLeavingGateOfDailyRepo {

	public Optional<AttendanceLeavingGateOfDaily> find(String employeeId, GeneralDate baseDate);

	public List<AttendanceLeavingGateOfDaily> find(String employeeId, List<GeneralDate> baseDate);
	
	public List<AttendanceLeavingGateOfDaily> find(String employeeId);

	public void update(AttendanceLeavingGateOfDaily domain);

	public void add(AttendanceLeavingGateOfDaily domain);
}
