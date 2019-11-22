package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class EmpInsQualifyInfoQuery {
	
	@Inject
	private EmpInsHistRepository empInsHistRepo;

	/**
	 * 社員IDと期間から社員雇用保険履歴IDを取得する
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @param notificationCls
	 * @return historyId
	 */
	public DateHistoryItem getEmployeeInsuranceHistoryId(String employeeId, GeneralDate startDate, GeneralDate endDate, int notificationCls) {
		String companyId = AppContexts.user().companyId();
		Optional<EmpInsHist> optEmpInsHist = empInsHistRepo.getEmpInsHistById(companyId, employeeId);
		if (optEmpInsHist.isPresent()) {
			EmpInsHist empInsHist = optEmpInsHist.get();
			if (notificationCls == 0) {
				empInsHist.items().stream().filter(h -> startDate.beforeOrEquals(h.start()) && h.start().beforeOrEquals(endDate)).collect(Collectors.toList());
			} else {
				empInsHist.items().stream().filter(h -> startDate.beforeOrEquals(h.end()) && h.end().beforeOrEquals(endDate)).collect(Collectors.toList());
			}
			if (empInsHist.items().isEmpty()) {
				return null;
			}
			return empInsHist.items().get(0);
		} else {
			return null;
		}
	}
}
