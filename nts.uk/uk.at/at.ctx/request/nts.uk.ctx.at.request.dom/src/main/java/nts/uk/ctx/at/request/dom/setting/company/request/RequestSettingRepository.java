package nts.uk.ctx.at.request.dom.setting.company.request;

import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RequestSettingRepository {
	/**
	 * ドメインモデル「承認一覧表示設定」を取得する
	 * @param companyID
	 * @return
	 */
	public Optional<RequestSetting> findByCompany(String companyID);
	/**
	 * update before and after hand restriction
	 * @param req
	 */
	public void update(RequestSetting req);
}
