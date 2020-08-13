package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import nts.uk.ctx.at.request.dom.application.ApplicationType;

import java.util.Optional;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface ApplicationSettingRepository {
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.アルゴリズム.申請別の申請設定を取得する.申請別の申請設定を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @return 申請設定
	 */
	public ApplicationSetting findByAppType(String companyID, ApplicationType appType);

	Optional<ApplicationSetting> findByCompanyId(String companyId);

	Integer getNightOvertimeReflectAtr(String companyId);
}
