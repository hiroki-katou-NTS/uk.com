package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import java.util.List;


public interface ExtractionCondScheduleYearRepository {
    List<ExtractionCondScheduleYear> getAll();
    
    /**
     * Get Schedule fix condition month
     * @param contractCode contract code
     * @param companyId company id
     * @param eralCheckIds error alarm check Id
     * @return schedule fix condition month
     */
    List<ExtractionCondScheduleYear> getScheAnyCond(String contractCode, String companyId, String eralCheckIds);
    
    /**
     * Add new 
     * @param contractCode contract code
     * @param companyId company id
     * @param domain ExtractionCondScheduleDay
     */
	void add(String contractCode, String companyId, ExtractionCondScheduleYear domain);
	
	/**
	 * Update
	 * @param contractCode contract code
	 * @param companyId company id
	 * @param domain ExtractionCondScheduleDay
	 */
	void update(String contractCode, String companyId, ExtractionCondScheduleYear domain);
	
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
