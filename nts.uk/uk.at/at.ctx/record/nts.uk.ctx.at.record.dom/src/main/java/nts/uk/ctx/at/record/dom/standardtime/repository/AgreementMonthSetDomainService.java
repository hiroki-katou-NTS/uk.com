package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

public interface AgreementMonthSetDomainService {

	List<String> add(AgreementMonthSetting agreementMonthSetting, Optional<WorkingConditionItem> workingConditionItem);
	
	List<String> update(AgreementMonthSetting agreementMonthSetting, Optional<WorkingConditionItem> workingConditionItem, Integer yearMonthValueOld);
	
	void delete(String employeeId, BigDecimal yearMonthValue);
	
	boolean checkExistData(String employeeId, BigDecimal yearMonthValue);
}
