/**
 * 5:44:27 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.app.find.approvalmanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.domainservice.OneMonthApprovalSttDomainService;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.OneMonthApprovalStatusDto;

/**
 * @author hungnm
 *
 */
@Stateless
public class OneMonthApprovalStatusFinder {

	@Inject
	private OneMonthApprovalSttDomainService oneMonthApprovalSttDomainService;

	public OneMonthApprovalStatusDto getDatePeriod(int closureId) {
		return oneMonthApprovalSttDomainService.getDatePeriod(closureId);
	}

	public OneMonthApprovalStatusDto getOneMonthApprovalStatus(Integer closureIdParam, GeneralDate startDateParam,
			GeneralDate endDateParam) {
		return oneMonthApprovalSttDomainService.getOneMonthApprovalStatus(closureIdParam, startDateParam, endDateParam);
	}

}
