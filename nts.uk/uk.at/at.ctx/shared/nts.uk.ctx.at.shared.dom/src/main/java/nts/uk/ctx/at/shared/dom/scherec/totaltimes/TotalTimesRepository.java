/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import java.util.List;
import java.util.Optional;

/**
 * The Interface TotalTimesRepository.
 */
public interface TotalTimesRepository {

	/**
	 * Update.
	 *
	 * @param totalTimes the total times
	 */
    void update(TotalTimes totalTimes);

	/**
	 * Gets the total times detail.
	 *
	 * @param companyId the company id
	 * @param totalCountNo the total count no
	 * @return the total times detail
	 */
	Optional<TotalTimes> getTotalTimesDetail(String companyId, Integer totalCountNo);
	
	/**
	 * Gets the total times detail by list frameNo
	 */
	List<TotalTimes> getTotalTimesDetailByListNo(String companyId, List<Integer> totalCountNos);

	/**
	 * Gets the all total times.
	 *
	 * @param companyId the company id
	 * @return the all total times
	 */
	List<TotalTimes> getAllTotalTimes(String companyId);
	
	/**
	 * 対応するドメインモデル「回数集計」を取得する Nhận domain model 「回数集計」tương ứng
	 *
	 * @param companyId 会社ID
	 * @param useCls 使用区分
	 * @return List＜回数集計＞
	 */
	List<TotalTimes> findByCompanyIdAndUseCls(String companyId, int useCls);

}
