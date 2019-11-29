/**
 * 8:55:35 AM Mar 22, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.OneMonthApprovalStatusDto;

/**
 * @author hungnm
 *
 */
public interface OneMonthApprovalSttDomainService {
	
	OneMonthApprovalStatusDto getDatePeriod(int closureId);
	OneMonthApprovalStatusDto getDatePeriod(int closureId,int currentYearMonth);
	OneMonthApprovalStatusDto getOneMonthApprovalStatus(Integer closureIdParam, Integer yearMonth);
	
	
	OneMonthApprovalStatusDto changeConditionExtract(Integer closureIdParam, Integer year);
}
