package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

public class AgeementTimeCommonSettingService {
	
	public static AgeementTimeCommonSetting getCommonService(RequireM1 require, String companyId, List<String> employeeIds, DatePeriod datePeriod){
		Set<GeneralDate> dates = new HashSet<>(datePeriod.datesBetween());
		return new AgeementTimeCommonSetting(AgreementDomainService.getBasicSet(require, companyId, employeeIds, datePeriod),
											require.workingConditionItem(employeeIds.stream().collect(Collectors.toMap(c -> c, c -> dates))));
	}
	
	public static interface RequireM1 extends AgreementDomainService.RequireM2 {
		
		Map<String, Map<GeneralDate, WorkingConditionItem>> workingConditionItem(Map<String, Set<GeneralDate>> params);
	}
}
