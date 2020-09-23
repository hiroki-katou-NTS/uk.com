package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;

/**
 * The Interface DivergenceTimeRepository.
 */
/**
 * @author NWS_ANHHTH_PC
 *
 */
public interface DivergenceTimeRepository {

	/**
	 * Gets the all div time.
	 *
	 * @param companyId the company id
	 * @return the all div time
	 */
	List<DivergenceTimeRoot> getAllDivTime(String companyId);

	/**
	 * Gets the div time list by no.
	 *
	 * @param company the company
	 * @param divTimeNo the div time no
	 * @return the div time list by no
	 */
	// Pls don't use this method, will be removed
	List<DivergenceTimeRoot> getDivTimeListByNo(String companyId,List<Integer> divTimeNo);

	/**
	 * Gets the div time info.
	 *
	 * @param companyId the company id
	 * @param divTimeNo the div time no
	 * @return the div time info
	 */
	Optional<DivergenceTimeRoot> getDivTimeInfo(String companyId, int divTimeNo);

	/**
	 * Find attendance id.
	 *
	 * @param companyId the company id
	 * @param divTimeNo the div time no
	 * @return the list
	 */
	List<Integer> findAttendanceId(String companyId, int divTimeNo);

	/**
	 * Update.
	 *
	 * @param divTimeDomain the div time domain
	 */
	void update (DivergenceTimeRoot divTimeDomain);
	
	/* 使用している乖離時間を取得する */
	/**
	 * Get all div time by use classification
	 * 
	 * @param companyId
	 * @return div time list
	 */
	List<DivergenceTimeRoot> getDivTimeListByUseSet(String companyId);
	
	/**
	 * Gets the div time list by no.
	 *
	 * @param company the company
	 * @param divTimeNo the div time no
	 * @return the div time list by no
	 */
	List<DivergenceTimeRoot> getUsedDivTimeListByNoV2(String companyId, List<Integer> divTimeNo);
	
	/**
	 * Gets all the div time list by no.
	 *
	 * @param company the company
	 * @param divTimeNo the div time no
	 * @return the div time list by no
	 */
	List<DivergenceTimeRoot> getUsedDivTimeListByNoV3(String companyId, List<Integer> divTimeNo);

}
