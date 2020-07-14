package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApplicationRepository {
	
	public Optional<Application> findByID(String companyID, String appID);
	
	public void insert(Application application);
	
	public void update(Application application);
	
	public void remove(String appID);
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.アルゴリズム.申請IDを使用して申請一覧を取得する.申請IDを使用して申請一覧を取得する
	 * @param appID
	 * @return
	 */
	public Optional<Application> findByID(String appID);
	
}
