package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

public interface OvertimeRepository {
	/**
	 * @param companyID
	 * @param appID
	 * @return
	 */
	Optional<AppOverTime> getAppOvertime(String companyID, String appID);
	
	/**
	 * ドメインモデル「残業申請」の登録処理を実行する(INSERT)
	 * @param domain : 残業申請
	 */
	void Add(AppOverTime domain);
	
}
