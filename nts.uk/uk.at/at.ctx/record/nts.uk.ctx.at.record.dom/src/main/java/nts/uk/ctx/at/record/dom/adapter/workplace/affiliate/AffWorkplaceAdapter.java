/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.workplace.affiliate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.WorkPlaceConfig;

/**
 * The Interface AffWorkplaceAdapter.
 */
public interface AffWorkplaceAdapter {
	
	/**
	 * Find by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the aff workplace dto
	 */
	Optional<AffWorkplaceDto> findBySid(String employeeId, GeneralDate baseDate);
	
	/**
	 * KIF 001
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<AffWorkPlaceSidImport> findBySidAndDate(String employeeId, GeneralDate baseDate);
	
	
	/**
	 * 所属職場を含む上位階層の職場IDを取得
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	List<String> findAffiliatedWorkPlaceIdsToRoot(String companyId, String employeeId, GeneralDate baseDate);
	
	Map<GeneralDate, Map<String, List<String>>> findAffiliatedWorkPlaceIdsToRoot(String companyId, List<String> employeeId, DatePeriod baseDate);
	
	List<AffAtWorkplaceImport> findBySIdAndBaseDate(List<String> employeeIds, GeneralDate baseDate);
	
	/**
	 * KIF 001 - update response reqList 485
	 * @param companyId
	 * @param period
	 * @return
	 */
	List<DatePeriod> getLstPeriod(String companyId, DatePeriod period);
	
	/**
	 * [No.647期間に対応する職場構成を取得する
	 *
	 * @param companyId
	 * @param datePeriod
	 * @return List<職場構成>
	 */
	List<WorkPlaceConfig> findByCompanyIdAndPeriod(String companyId, DatePeriod datePeriod);
	
	/**
	 * [No.569]職場の上位職場を取得する
	 * @param companyID
	 * @param workplaceID
	 * @param date
	 * @return
	 */
	List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date);
	
}
