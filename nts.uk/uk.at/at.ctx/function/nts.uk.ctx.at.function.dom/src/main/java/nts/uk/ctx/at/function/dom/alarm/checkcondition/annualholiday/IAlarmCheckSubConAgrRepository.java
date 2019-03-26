package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import java.util.Optional;

public interface IAlarmCheckSubConAgrRepository {
	
    public Optional<AlarmCheckSubConAgr> findByKey(String companyId, int category, String code);
	
	public void insert(String companyId, int category, String code, AlarmCheckSubConAgr alarmCheckSubConAgr);
	
	public void update(String companyId, int category, String code, AlarmCheckSubConAgr alarmCheckSubConAgr);
	
	public void delete(String companyId, int category, String code);
}
