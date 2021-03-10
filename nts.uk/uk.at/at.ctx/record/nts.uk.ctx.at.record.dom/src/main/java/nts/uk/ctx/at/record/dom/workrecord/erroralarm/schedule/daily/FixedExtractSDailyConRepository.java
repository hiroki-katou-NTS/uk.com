package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import java.util.List;

public interface FixedExtractSDailyConRepository {
    List<FixedExtractionSDailyItems> getAll();
    
    /**
     * Get Schedule fix condition day
     * @param contractCode contract code
     * @param companyId company id
     * @return list of schedule fix condition day
     */
    List<FixedExtractionSDailyCon> getScheFixCondDay(String contractCode, String companyId);
    
    /**
     * Get Schedule fix condition day
     * @param contractCode contract code
     * @param companyId company id
     * @param eralCheckIds list of error alarm check Id
     * @return list of schedule fix condition day
     */
    List<FixedExtractionSDailyCon> getScheFixCondDay(String contractCode, String companyId, String eralCheckIds);
    
    /**
     * Add new スケジュール日次の固有抽出条件
     * @param contractCode contract code
     * @param companyId company id
     * @param domain FixedExtractionSDailyItems
     */
    void add(String contractCode, String companyId, FixedExtractionSDailyCon domain);
    
    /**
     * Update a スケジュール日次の固有抽出条件
     * @param contractCode contract code
     * @param companyId company id
     * @param domain FixedExtractionSDailyItems
     */
    void update(String contractCode, String companyId, FixedExtractionSDailyCon domain);
    
    /**
     * Delete a スケジュール日次の固有抽出条件
     * @param contractCode
     * @param companyId
     * @param erAlCheckIds
     */
    void delete(String contractCode, String companyId, List<String> erAlCheckIds);
}
