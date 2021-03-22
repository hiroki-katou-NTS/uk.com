package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.List;
import java.util.Optional;
/**
 * 「古いクラス → 削除予定 → 使わないでください (Old Class → Delete plan → Please don't use it)」
 * @author phuongdv
 *
 */
public interface GoBackDirectlyRepository_Old {
	// find Application by ID
	public Optional<GoBackDirectly_Old> findByApplicationID(String companyID, String appID);
	/**
	 * 
	 * @param goBackDirectly
	 */
	public void update(GoBackDirectly_Old goBackDirectly);

	/**
	 * ドメインモデル「直行直帰申請」の新規登録する
	 * 
	 * @param goBackDirectly
	 */
	public void insert(GoBackDirectly_Old goBackDirectly);

	/**
	 * 
	 * @param goBackDirectly
	 */
	public void delete(String companyID, String appID);
	/**
	 * @author hoatt
	 * get List Application Go Back
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public List<GoBackDirectly_Old> getListAppGoBack(String companyID, List<String> lstAppID);
}
