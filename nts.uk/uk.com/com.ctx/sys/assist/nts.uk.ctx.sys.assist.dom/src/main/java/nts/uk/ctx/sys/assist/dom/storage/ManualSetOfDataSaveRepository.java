package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
* データ保存の手動設定
*/
/**
 * @author nam.lh
 *
 */
public interface ManualSetOfDataSaveRepository {

	List<ManualSetOfDataSave> getAllManualSetOfDataSave();

	Optional<ManualSetOfDataSave> getManualSetOfDataSaveById(String cid, String storeProcessingId);

	/**
	 * @param domain
	 * @author nam.lh
	 */

	Optional<ManualSetOfDataSave> getManualSetOfDataSaveById(String storeProcessingId);

	void addManualSetting(ManualSetOfDataSave domain);
}
