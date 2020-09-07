package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

/**
 * @author sonnlb
 * 
 * 打刻カード編集Repository
 */
public interface StampCardEditingRepo {
	
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
	StampCardEditing get(String companyId);

}
