package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.List;
import java.util.Optional;

public interface StampSetPerRepository {
	
	/**
	 * insert(個人利用の打刻設定)
	 * @param stampSettingPerson
	 */
	public void insert(StampSettingPerson stampSettingPerson);
	
	/**
	 * update(個人利用の打刻設定)
	 * @param stampSettingPerson
	 */
	public void update(StampSettingPerson stampSettingPerson);

	/**
	 * 取得する
	 * @param companyId
	 * @return
	 */
	public Optional<StampSettingPerson> getStampSet (String companyId);

	/**
	 * insert 打刻ページレイアウト
	 * @param layout
	 */
	public void insertPage(StampPageLayout layout);

	/**
	 * update 打刻ページレイアウト
	 * @paramStampPageLayout layout
	 */
	public void updatePage(StampPageLayout layout);

	/**
	 * find 打刻ページレイアウト
	 * @param companyId
	 * @param pageNo
	 * @param buttonLayoutType
	 * @return
	 */
	public Optional<StampPageLayout> getStampSetPage(String companyId, int pageNo);

	/**
	 * find all 打刻ページレイアウト
	 * @param companyId
	 * @return
	 */
	List<StampPageLayout> getAllStampSetPage(String companyId);

	/**
	 * find by CID
	 * @param companyId
	 * @return
	 */
	Optional<StampPageLayout> getStampSetPageByCid(String companyId);

	/**
	 * delete 打刻ページレイアウト
	 * @param companyId
	 * @param pageNo
	 */
	void delete(String companyId, int pageNo);

}
