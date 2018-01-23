package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;

public interface AgreementMonthSettingRepository {

	List<AgreementMonthSetting> find(String employeeId);
	
	void add(AgreementMonthSetting agreementMonthSetting);
	
	void update(AgreementMonthSetting agreementMonthSetting);
	
	void delete(String employeeId, BigDecimal yearMonthValue);
	
	boolean checkExistData(String employeeId, BigDecimal yearMonthValue);
	
}
