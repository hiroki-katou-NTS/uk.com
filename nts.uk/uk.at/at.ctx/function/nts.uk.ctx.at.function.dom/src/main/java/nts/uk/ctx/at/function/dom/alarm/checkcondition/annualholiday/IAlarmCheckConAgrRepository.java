package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import java.util.Optional;

public interface IAlarmCheckConAgrRepository {
	
	public Optional<AlarmCheckConAgr> findByKey(String companyId, int category, String code);
	
	public void insert(String companyId, int category, String code, AlarmCheckConAgr alarmCheckConAgr);
	
	public void update(String companyId, int category, String code, AlarmCheckConAgr alarmCheckConAgr);
	
	public void delete(String companyId, int category, String code);

}
