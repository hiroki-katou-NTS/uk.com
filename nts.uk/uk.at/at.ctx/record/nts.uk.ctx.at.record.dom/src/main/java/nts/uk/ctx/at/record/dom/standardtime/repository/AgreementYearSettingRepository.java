package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;

public interface AgreementYearSettingRepository {
	
	List<AgreementYearSetting> find(String employeeId);
	
	void add(AgreementYearSetting agreementYearSetting);
	
	void update(String employeeId, int yearvalue, BigDecimal errorOneYear, BigDecimal alarmOneYear);
	
	void delete(String employeeId, int yearvalue);
}
