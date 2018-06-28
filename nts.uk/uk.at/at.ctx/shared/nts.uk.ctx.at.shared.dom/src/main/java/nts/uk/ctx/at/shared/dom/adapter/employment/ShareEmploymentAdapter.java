/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employment;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


public interface ShareEmploymentAdapter {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	// RequestList #89
	List<EmpCdNameImport> findAll(String companyId);
	
	/**
	 * Find by emp codes.
	 *
	 * @param companyId the company id
	 * @param empCodes the emp codes
	 * @return the list
	 */
	Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId, GeneralDate baseDate);
	/**
	 * 社員ID（List）と指定期間から社員の雇用履歴を取得
	 * @param sids 社員IDリスト
	 * @param datePeriod　期間	
	 * @return
	 */
	public List<SharedSidPeriodDateEmploymentImport> getEmpHistBySidAndPeriod(List<String> sids , DatePeriod datePeriod);
}
