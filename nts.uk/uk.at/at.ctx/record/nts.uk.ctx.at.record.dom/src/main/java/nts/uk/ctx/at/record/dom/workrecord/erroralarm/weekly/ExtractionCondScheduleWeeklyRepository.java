package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import java.util.List;

public interface ExtractionCondScheduleWeeklyRepository {
    List<ExtractionCondWeekly> getAll();
    
    /**
     * Get fix condition weekly
     * @param contractCode contract code
     * @param companyId company id
     * @param eralCheckIds error alarm check Id
     * @return schedule fix condition weekly
     */
    List<ExtractionCondWeekly> getAnyCond(String contractCode, String companyId, String eralCheckIds);
    
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
	 * @param contractCode
	 * @param companyId
	 * @param erAlCheckIds
	 * @param alarmNo
	 */
	void delete(String contractCode, String companyId, String erAlCheckIds, int alarmNo);
	
	/**
	 * Remove all by error alarm check id
	 * @param contractCode
	 * @param companyId
	 */
	void delete(String contractCode, String companyId, String checkId);
}
