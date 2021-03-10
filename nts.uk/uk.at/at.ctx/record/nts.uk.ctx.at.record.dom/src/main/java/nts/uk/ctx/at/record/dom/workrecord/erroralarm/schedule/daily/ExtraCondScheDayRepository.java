package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import java.util.List;

public interface ExtraCondScheDayRepository {
    List<ExtractionCondScheduleDay> getAll(String cid);
    
    /**
     * Get Schedule fix condition day
     * @param contractCode contract code
     * @param companyId company id
     * @return list of schedule fix condition day
     */
    List<ExtractionCondScheduleDay> getScheAnyCondDay(String contractCode, String companyId);
    
    /**
     * Get Schedule fix condition day
     * @param contractCode contract code
     * @param companyId company id
     * @param eralCheckIds error alarm check Id
     * @return list of schedule fix condition day
     */
    List<ExtractionCondScheduleDay> getScheAnyCondDay(String contractCode, String companyId, String eralCheckIds);
    
    /**
     * Add new 
     * @param contractCode contract code
     * @param companyId company id
     * @param domain ExtractionCondScheduleDay
     */
	void add(String contractCode, String companyId, ExtractionCondScheduleDay domain);
	
	/**
	 * Update
	 * @param contractCode contract code
	 * @param companyId company id
	 * @param domain ExtractionCondScheduleDay
	 */
	void update(String contractCode, String companyId, ExtractionCondScheduleDay domain);
	
	/**
     * Delete schedule any condition day
     * @param contractCode contract code
     * @param companyId company id
     * @param erAlCheckIds list of error alarm check id
     */
    void delete(String contractCode, String companyId, List<String> erAlCheckIds);
}
