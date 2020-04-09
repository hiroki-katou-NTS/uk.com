package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

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
//	public List<AppEmploymentSetting> getEmploymentSetting(String companyID,String EmploymentCode, int appType);
	
	/**
	 * 雇用別申請承認設定
	 * @param companyId: ログイン会社 
	 * @return list of employments
	 */
	List<AppEmploymentSetting> getEmploymentSetting(String companyId);
//	List<AppEmploymentSetting_New> getEmploymentSettingRef(String companyId);
	/**
	 * 雇用別申請承認設定
	 * @param companyId: ログイン会社 
	 * @param employmentCode: 雇用コード
	 * @return list of employments
	 */
//	List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode);
	/**
	 * ドメインモデル「雇用別申請承認設定」を追加する (Add)
	 * @param employmentList
	 */
//	void insert(List<AppEmploymentSetting> employmentList);
	/**
	 * ドメインモデル「雇用別申請承認設定」を更新する (Update)
	 * @param employmentList
	 */
//	void update(List<AppEmploymentSetting> employmentList);
	void update(AppEmploymentSetting employmentList);
	/**
	 * 削除条件
	 * 		・ログイン会社  
	 * 		・選択している雇用の雇用
	 * @param companyId
	 * @param employmentCode
	 */
	void remove(String companyId, String employmentCode);
	public void insert(AppEmploymentSetting insertData);
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode);
	public List<AppEmploymentSetting> getEmploymentSetting(String companyId, String employmentCode, int appType);
}
