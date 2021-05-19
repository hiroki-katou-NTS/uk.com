package nts.uk.ctx.at.function.dom.annualworkschedule.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;

public interface SetOutputItemOfAnnualWorkSchRepository {

	/**
	 * ドメインモデル「年間勤務表の出力項目設定」をすべて取得する.
	 *
	 * @param companyId  ログイン社員ID
	 * @param employeeId ログイン会社ID
	 * @param printForm 項目選択種類
	 * @param settingType 印刷形式
	 * @return all setting in mode
	 */
	public List<SettingOutputItemOfAnnualWorkSchedule> findAllSeting(String companyId
																   , Optional<String> employeeId
																   , int printForm
																   , int settingType);

	/**
	 * 「年間勤務表の出力項目設定」を取得する
	 *
	 * @param layoutId 項目設定ID
	 * @return the setting output item of annual work schedule
	 */
	public Optional<SettingOutputItemOfAnnualWorkSchedule> findByLayoutId(String layoutId);
	
	/**
	 * 「年間勤務表の出力項目設定」を取得する.
	 *
	 * @param code the code
	 * @param companyId the company id
	 * @param settingType the setting type
	 * @return the setting output item of annual work schedule
	 */
	public Optional<SettingOutputItemOfAnnualWorkSchedule> findByCode(String code
																	, Optional<String> employeeId
																	, String companyId
																	, int settingType
																	, int printFormat);

	/**
	 * ドメインモデル「年間勤務表の出力項目設定を追加する
	 *
	 * @param domain the domain
	 */
	public void add(SettingOutputItemOfAnnualWorkSchedule domain);

	/**
	 * ドメインモデル「年間勤務表の出力項目設定」を更新する
	 *
	 * @param domain the domain
	 */
	public void update(SettingOutputItemOfAnnualWorkSchedule domain);

	/**
	 * メインモデル「年間勤務表の出力項目設定」を削除する
	 * 
	 * @param layoutId: 項目設定IDと一致する
	 */
	public void remove(String layoutId);
}
