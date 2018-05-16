package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.Optional;
import java.util.List;

/**
* データ削除の手動設定
*/
/**
 * @author hiep.th
 *
 */
public interface ManualSetDeletionRepository {

	List<ManualSetDeletion> getAllManualSetDeletion();

	Optional<ManualSetDeletion> getManualSetDeletionById(String cid, String delId);

	/**
	 * @param domain
	 * @author hiep.th
	 */

	Optional<ManualSetDeletion> getManualSetDeletionById(String delId);

	void addManualSetting(ManualSetDeletion domain);
}
