package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;

public interface AppEmploymentSettingRepository {
	/**
	 * 勤務種類リスト
	 * @param companyID
	 * @param EmploymentCode
	 * @param appType
	 * @return
	 */
	public List<AppEmployWorkType> getEmploymentWorkType(String companyID,String EmploymentCode, int appType);
	/**
	 * 雇用別申請承認設定
	 * @param companyID
	 * @param EmploymentCode
	 * @param appType
	 * @return
	 */
	public Optional<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode, int appType);
	
	/**
	 * 雇用別申請承認設定
	 * @param companyId: ログイン会社 
	 * @return list of employments
	 */
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId);
	/**
	 * 雇用別申請承認設定
	 * @param companyId: ログイン会社 
	 * @param employmentCode: 雇用コード
	 * @return list of employments
	 */
	public Optional<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode);
	/**
	 * ドメインモデル「雇用別申請承認設定」を追加する (Add)
	 * @param employmentList
	 */
	public void insert(AppEmploymentSetting insertData);
	/**
	 * ドメインモデル「雇用別申請承認設定」を更新する (Update)
	 * @param employmentList
	 */
	void update(AppEmploymentSetting employmentList);
	/**
	 * 削除条件
	 * 		・ログイン会社  
	 * 		・選択している雇用の雇用
	 * @param companyId
	 * @param employmentCode
	 */
	void remove(String companyId, String employmentCode);
	


}
