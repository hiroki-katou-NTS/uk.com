package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

public interface OvertimeRepository {
	/**
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppOverTime> getAppOvertime(String companyID, String appID);
	
	/**
	 * ドメインモデル「残業申請」の登録処理を実行する(INSERT)
	 * @param domain : 残業申請
	 */
	public void Add(AppOverTime domain);
	
	public Optional<AppOverTime> getFullAppOvertime(String companyID, String appID);
	
	public void update(AppOverTime appOverTime); 
	
	public void delete(String companyID, String appID);
	
}
