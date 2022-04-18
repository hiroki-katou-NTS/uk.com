package nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod;

import java.util.List;
import java.util.Optional;

public interface ErrorAlarmAnyPeriodRepository {
	
	public void add(ErrorAlarmAnyPeriod target);
	
	public void delete(String companyId, int category, String code);
	
	public void update(ErrorAlarmAnyPeriod target);
	
	public List<ErrorAlarmAnyPeriod> gets(String companyId, int category, List<String> codes);
	
	public Optional<ErrorAlarmAnyPeriod> get(String companyId, int category, String errorAlarmConditionCode);
}
