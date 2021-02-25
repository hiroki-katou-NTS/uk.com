package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;
import java.util.Optional;

/**
* データ削除の手動設定
*/
/**
 * @author hiep.th
 *
 */
public interface ManualSetDeletionRepository {

	List<ManualSetDeletion> getAllManualSetDeletion();
	
	List<ManualSetDeletion> getManualSetDeletionsSystemTypeAndId(List<String> delId);

	Optional<ManualSetDeletion> getManualSetDeletionById(String cid, String delId);

	/**
	 * @param domain
	 * @author hiep.th
	 */

	Optional<ManualSetDeletion> getManualSetDeletionById(String delId);

	void addManualSetting(ManualSetDeletion domain);
}
