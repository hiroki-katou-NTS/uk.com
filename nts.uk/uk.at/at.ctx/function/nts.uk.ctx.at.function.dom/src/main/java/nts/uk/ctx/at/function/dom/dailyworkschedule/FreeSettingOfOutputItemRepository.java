package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

public interface FreeSettingOfOutputItemRepository {

	/**
	 * ドメインモデル「日別勤務表の出力項目自由設定」を取得
	 * 
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return ドメインモデル「日別勤務表の出力項目自由設定」
	 */
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> getFreeSettingByCompanyAndEmployee(String companyId, String employeeId);
	
	public void add(FreeSettingOfOutputItemForDailyWorkSchedule freeSettingOfOutputItemForDailyWorkSchedule);
	
	/**
	 * ドメインモデル「日別勤務表の出力項目自由設定」を削除する (Delete the domain model 「日別勤務表の出力項目自由設定」)
	 *
	 * @param companyId : 会社ID　(ログイン会社ID)
	 * @param employeeId : 社員ID (ログイン社員ID)
	 * @param layoutId: 出力項目．出力レイアウトID
	 */
	public void deleteFreeSetting(String companyId, String employeeId, String layoutId);

	/**
	 * ドメインモデル「日別勤務表の出力項目自由設定」を取得する (Get the domain model 「日別勤務表の出力項目自由設定」).
	 *
	 * @param companyId 会社ID：ログイン会社ID
	 * @param employeeId 社員ID　：　ログイン社員ID
	 * @param code コード：入力しているコード(D1_6)
	 * @return the optional
	 */
	public Optional<OutputItemDailyWorkSchedule> findByCompanyIdAndEmployeeIdAndCode(String companyId, String employeeId, String code);
}
