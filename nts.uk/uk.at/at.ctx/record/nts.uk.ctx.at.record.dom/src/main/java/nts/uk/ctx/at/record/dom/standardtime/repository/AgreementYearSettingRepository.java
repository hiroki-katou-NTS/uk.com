package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

public interface AgreementYearSettingRepository {
	
	List<AgreementYearSetting> find(String employeeId);

	void add(AgreementYearSetting agreementYearSetting);
	
	void update(AgreementYearSetting agreementYearSetting);
	
	void delete(String employeeId, int yearvalue);

	boolean checkExistData(String employeeId, BigDecimal yearValue);

	void updateById(AgreementYearSetting agreementYearSetting, Integer yearMonthValueOld);

	Optional<AgreementYearSetting> findByKey(String employeeId, int yearMonth);

	List<AgreementYearSetting> findByKey(List<String> employeeIds, int yearMonth);

	void delete(AgreementYearSetting agreementYearSetting);

	List<AgreementYearSetting> findByListEmployee(List<String> employeeIds);

	Optional<AgreementYearSetting> findBySidAndYear(String employeeId, int year);
}
