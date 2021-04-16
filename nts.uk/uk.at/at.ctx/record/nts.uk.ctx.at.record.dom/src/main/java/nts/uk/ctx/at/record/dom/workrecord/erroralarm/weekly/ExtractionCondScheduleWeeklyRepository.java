package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import java.util.List;

public interface ExtractionCondScheduleWeeklyRepository {
    List<ExtractionCondScheduleWeekly> getAll();
    
    /**
     * Get Schedule fix condition weekly
     * @param contractCode contract code
     * @param companyId company id
     * @param eralCheckIds error alarm check Id
     * @return schedule fix condition weekly
     */
    List<ExtractionCondScheduleWeekly> getScheAnyCond(String contractCode, String companyId, String eralCheckIds);
    
    /**
     * Add new 
     * @param contractCode contract code
     * @param companyId company id
     * @param domain ExtractionCondScheduleWeekly
     */
	void add(String contractCode, String companyId, ExtractionCondScheduleWeekly domain);
	
	/**
	 * Update
	 * @param contractCode contract code
	 * @param companyId company id
	 * @param domain ExtractionCondScheduleWeekly
	 */
	void update(String contractCode, String companyId, ExtractionCondScheduleWeekly domain);
	
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
	 * @param erAlCheckIds
	 */
	void delete(String contractCode, String companyId, String checkId);
}
