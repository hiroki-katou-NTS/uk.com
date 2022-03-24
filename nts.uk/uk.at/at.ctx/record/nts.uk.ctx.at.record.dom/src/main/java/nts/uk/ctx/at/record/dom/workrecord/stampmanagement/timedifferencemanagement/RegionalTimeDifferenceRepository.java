package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement;

import java.util.List;
import java.util.Optional;

/**
 * 地域別時差管理Repository
 * @author chungnt
 *
 */

public interface RegionalTimeDifferenceRepository {

	/**
	 * [1] insert(地域別時差管理)
	 * @param RegionalTimeDifference
	 */
	public void insert(RegionalTimeDifference regionalTimeDifference);

	/**
	 * [2] get
	 * @param contractCode  契約コード
	 * @param code			コード
	 * @return
	 */
	public Optional<RegionalTimeDifference> get(String contractCode, int code);
	
	/**
	 * [3] get*
	 * @param contractCode
	 * @return
	 */
	public List<RegionalTimeDifference> getAll(String contractCode);
	
}
