package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

public interface AgreementYearSetDomainService {

	List<String> add(AgreementYearSetting agreementYearSetting, Optional<WorkingConditionItem> workingConditionItem);
	
	List<String> update(AgreementYearSetting agreementYearSetting, Optional<WorkingConditionItem> workingConditionItem, Integer yearMonthValueOld);
	
	void delete(String employeeId, int yearValue);
	
	boolean checkExistData(String employeeId, BigDecimal yearValue);
	
}
