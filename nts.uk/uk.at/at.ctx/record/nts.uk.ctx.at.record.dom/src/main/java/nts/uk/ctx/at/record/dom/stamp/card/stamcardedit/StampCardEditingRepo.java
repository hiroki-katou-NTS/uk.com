package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import java.util.Optional;

/**
 * @author sonnlb
 * 
 * 打刻カード編集Repository
 */
public interface StampCardEditingRepo {

	/**
	 * 取得する(会社ID)

	 * @param 会社ID
	 *            companyId
	 * @return 打刻カード編集 StampCardEditing
	 */
	Optional<StampCardEditing> get(String companyId);
	
	/**
	 * [1] update(打刻カード編集)
	 * @param domain
	 */
	void update(StampCardEditing domain);
	
	/**
	 * [2] get
	 * @param companyId
	 * @return StampCardEditing
	 */
	StampCardEditing getStampCardEditing(String companyId);

}
