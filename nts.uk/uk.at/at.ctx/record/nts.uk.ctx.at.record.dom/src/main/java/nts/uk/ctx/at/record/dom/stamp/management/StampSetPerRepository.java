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
	 * 
	 * @param layout
	 */
	public void insertPage(StampPageLayout layout);

	public void updatePage(StampPageLayout layout);

	public Optional<StampPageLayout> getStampSetPage(String companyId, int pageNo);

	List<StampPageLayout> getAllStampSetPage(String companyId);

	Optional<StampPageLayout> getStampSetPageByCid(String companyId);

	void delete(String companyId, int pageNo);

}
