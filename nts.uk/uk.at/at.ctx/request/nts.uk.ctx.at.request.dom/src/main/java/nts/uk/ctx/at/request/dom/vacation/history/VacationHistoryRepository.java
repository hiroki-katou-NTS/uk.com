/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.vacation.history;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface VacationHistoryRepository.
 */
public interface VacationHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param vacationHistory the vacation history
	 */
	void add(PlanVacationHistory vacationHistory);

	/**
	 * Update.
	 *
	 * @param vacationHistory the vacation history
	 */
	void update(PlanVacationHistory vacationHistory);

    /**
     * Removes the vacation history.
     *
     * @param companyId the company id
     * @param historyId the history id
     * @param workTypeCode the work type code
     */
    void removeVacationHistory(String companyId, String historyId, String workTypeCode);

    /**
     * Find by work type code.
     *
     * @param companyId the company id
     * @param workTypeCode the work type code
     * @return the list
     */
    List<PlanVacationHistory> findByWorkTypeCode(String companyId, String workTypeCode);
    
    /**
     * Count by date period.
     *
     * @param companyId the company id
     * @param workTypeCode the work type code
     * @param datePeriod the date period
     * @param histId the hist id
     * @return the integer
     */
    public Integer countByDatePeriod(String companyId, String workTypeCode, DatePeriod datePeriod,
			String histId);

    /**
     * Find history.
     *
     * @param companyId the company id
     * @param historyId the history id
     * @return the optional
     */
    public List<PlanVacationHistory> findHistory(String companyId, String historyId);
    
    /**
     * Find history by period.
     *
     * @param companyId the company id
     * @param period the period
     * @return the list
     */
    public List<PlanVacationHistory> findHistoryByBaseDate(String companyId, GeneralDate baseDate);
    /**
     * 
     * @param cid 会社ID = INPUT．会社ID
     * @param workTypeCode ・勤務種類コード = INPUT．勤務種類コード AND
     * @param dateData・INPUT．開始日 <= 期間．終了日 AND
     * 			 ・INPUT．終了日 >= 期間．開始日
     * @return 
     */
    List<PlanVacationHistory> findByWorkTypeAndPeriod(String cid, String workTypeCode, DatePeriod dateData);
}
