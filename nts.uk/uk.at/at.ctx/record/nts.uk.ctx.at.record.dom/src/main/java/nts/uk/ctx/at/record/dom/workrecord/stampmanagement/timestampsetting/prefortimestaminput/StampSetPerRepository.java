package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;

/**
 * 個人利用の打刻設定Repository
 *
 */
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

	Optional<StampSettingPerson> getStampSetting(String companyId);

}
