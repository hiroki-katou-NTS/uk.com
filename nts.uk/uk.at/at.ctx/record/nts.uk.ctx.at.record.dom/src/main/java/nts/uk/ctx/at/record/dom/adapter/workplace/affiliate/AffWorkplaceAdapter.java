/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.workplace.affiliate;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface AffWorkplaceAdapter.
 */
public interface AffWorkplaceAdapter {

	/**
	 * Find by sid.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the aff workplace dto
	 */
	Optional<AffWorkplaceDto> findBySid(String employeeId, GeneralDate baseDate);

	/**
	 * KIF 001
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<AffWorkPlaceSidImport> findBySidAndDate(String employeeId, GeneralDate baseDate);

	/**
	 * 社員ID（List）と基準日から所属職場IDを取得
	 */
	List<AffAtWorkplaceImport> findBySIdAndBaseDate(List<String> sids, GeneralDate baseDate);

	/**
	 * 所属職場を含む上位階層の職場IDを取得
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @return 社員ID（List）と基準日から所属職場IDを取得
	 */
	List<String> findAffiliatedWorkPlaceIdsToRoot(String companyId, String employeeId, GeneralDate baseDate);

}
