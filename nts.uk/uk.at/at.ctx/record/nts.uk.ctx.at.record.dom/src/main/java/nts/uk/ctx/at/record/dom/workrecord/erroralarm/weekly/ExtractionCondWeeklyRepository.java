package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import java.util.List;

public interface ExtractionCondWeeklyRepository {

    /**
     * Get fix condition weekly
     * @param cid
     * @param category
     * @param code
     * @return schedule fix condition weekly
     */
	ExtractionCondWeekly getAnyCond(String cid, int category, String code);
    
    /**
     * Add new 
     * @param contractCode contract code
     * @param companyId company id
     * @param domain ExtractionCondScheduleWeekly
     */
	void add(String contractCode, String companyId, ExtractionCondWeekly domain);
	
	/**
	 * Update
	 * @param contractCode contract code
	 * @param companyId company id
	 * @param domain ExtractionCondScheduleWeekly
	 */
	void update(String contractCode, String companyId, ExtractionCondWeekly domain);
	
	/**
	 * Remove
	 * @param cid
	 * @param category
	 * @param code
	 */
	void delete(String cid, int category, String code);
}
