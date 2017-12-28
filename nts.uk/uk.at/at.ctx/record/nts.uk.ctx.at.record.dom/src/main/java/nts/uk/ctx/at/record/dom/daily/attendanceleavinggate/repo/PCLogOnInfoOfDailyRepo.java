package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;

public interface PCLogOnInfoOfDailyRepo {

	public Optional<PCLogOnInfoOfDaily> find(String employeeId, GeneralDate baseDate);

	public List<PCLogOnInfoOfDaily> find(String employeeId, List<GeneralDate> baseDate);
	
	public List<PCLogOnInfoOfDaily> find(String employeeId);

	public void update(PCLogOnInfoOfDaily domain);

	public void add(PCLogOnInfoOfDaily domain);
}
