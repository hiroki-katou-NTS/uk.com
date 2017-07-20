package nts.uk.ctx.at.shared.dom.dailypattern;

import java.util.List;


public interface DailyPatternRepository {
	/**
	 * get all Pattern Calendar
	 * @param companyId
	 * @return
	 */
	List<DailyPattern> getAllPattCalendar(String companyId);
	
	 /**
     * Adds the.
     *
     * @param patternCalendar the pattern Calendar
     */
    void add(DailyPattern patternCalendar);
    
    /**
     * Update.
     *
     * @param patternCalendar the pattern Calendar
     */
    void update(DailyPattern patternCalendar);
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the list
     */
    List<DailyPattern> findByCompanyId(String companyId);
}
