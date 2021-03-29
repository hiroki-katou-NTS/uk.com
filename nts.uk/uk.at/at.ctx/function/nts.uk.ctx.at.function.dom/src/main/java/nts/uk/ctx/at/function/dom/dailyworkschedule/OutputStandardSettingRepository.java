package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

public interface OutputStandardSettingRepository {

	/**
	 * ドメインモデル「日別勤務表の出力項目定型設定」を取得
	 * @param companyId: 会社ID
	 * @return ドメインモデル Domain Model「日別勤務表の出力項目定型設定」
	 */
	public Optional<OutputStandardSettingOfDailyWorkSchedule> getStandardSettingByCompanyId(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param outputStandard the output standard
	 */
	public void add(OutputStandardSettingOfDailyWorkSchedule outputStandard);
	
	/**
	 * ドメインモデル「日別勤務表の出力項目定型設定」を削除する(Delete the domain model 「日別勤務表の出力項目定型設定」)
	 *
	 * @param companyId 会社ID
	 * @param layoutId 出力項目．出力レイアウトID
	 */
	public void deleteStandardSetting(String companyId, String layoutId);
	
	/**
	 * ドメインモデル「日別勤務表の出力項目定型設定」を取得する(Get the domain model 「日別勤務表の出力項目定型設定」)
	 *
	 * @param companyId 会社ID：ログイン会社ID
	 * @param code コード：入力しているコード(D1_6)
	 * @return the optional
	 */
	public Optional<OutputItemDailyWorkSchedule> findByCompanyIdAndCode(String companyId, String code);
}
