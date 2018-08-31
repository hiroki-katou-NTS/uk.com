package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.List;
import java.util.Optional;

public interface GoBackDirectlyRepository {
	// find Application by ID
	public Optional<GoBackDirectly> findByApplicationID(String companyID, String appID);
	/**
	 * 
	 * @param goBackDirectly
	 */
	public void update(GoBackDirectly goBackDirectly);

	/**
	 * ドメインモデル「直行直帰申請」の新規登録する
	 * 
	 * @param goBackDirectly
	 */
	public void insert(GoBackDirectly goBackDirectly);

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
	public List<GoBackDirectly> getListAppGoBack(String companyID, List<String> lstAppID);
}
