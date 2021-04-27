package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト.スケジュール日次・月次・年間.スケジュール月次のアラームリストのチェック条件.アルゴリズム.使用しない.特定属性の項目の予定を作成する.対比のチェック条件をチェック.所定公休日数比をチェック.比較対象所定公休日数を取得
 *
 */
@Stateless
public class GetNumberOfDayHolidayService {
	
	/**
	 * TODO 比較対象所定公休日数を取得
	 * 
	 * @param publicHolidayManagementUsageUnit 公休利用単位設定
	 * @param employmentMonthDaySettingOpt Optional＜雇用月間日数設定＞
	 * @param employeeMonthDaySettingOpt Optional＜社員月間日数設定＞
	 * @param workplaceMonthDaySettingOpt Optional＜職場月間日数設定＞
	 * @param optCompanyMonthDaySettingOpt Optional＜会社月間日数設定＞
	 * 
	 * @return number of day holiday (所定公休日数)
	 */
	public Double getNumberOfHoliday(
			PublicHolidayManagementUsageUnit publicHolidayManagementUsageUnit,
			Optional<EmploymentMonthDaySetting> employmentMonthDaySettingOpt,
			Optional<EmployeeMonthDaySetting> employeeMonthDaySettingOpt,
			Optional<WorkplaceMonthDaySetting> workplaceMonthDaySettingOpt,
			Optional<CompanyMonthDaySetting> optCompanyMonthDaySettingOpt) {
		int aru = 1;
		
		// 社員月間日数設定を使うかチェック
		// Input．公休利用単位設定．社員を利用する　＝＝　する
		// AND
		// Input．社員月間日設定　！＝　Empty
		if (publicHolidayManagementUsageUnit.getIsManageEmployeePublicHd() == aru && employeeMonthDaySettingOpt.isPresent()) {
			// 所定公休日数を計算
			EmployeeMonthDaySetting rmployeeMonthDaySetting = employeeMonthDaySettingOpt.get();
			
			// 所定公休日数　＝　合計（Input．社員月間日設定．公休日数．公休日数）
			return rmployeeMonthDaySetting.getPublicHolidayMonthSettings().stream().mapToDouble(x -> x.getInLegalHoliday().v()).sum();
		}
		
		// 雇用月間日数設定を使用するかチェック
		// Input．公休利用単位設定．雇用を利用する　＝＝　する
		// AND
		// Input．雇用月間日設定　！＝　Empty
		if (publicHolidayManagementUsageUnit.getIsManageEmpPublicHd() == aru && employmentMonthDaySettingOpt.isPresent()) {
			// 所定公休日数を計算
			EmploymentMonthDaySetting employmentMonthDaySetting = employmentMonthDaySettingOpt.get();
			
			// 所定公休日数　＝　合計（Input．雇用月間日設定．公休日数．公休日数）
			return employmentMonthDaySetting.getPublicHolidayMonthSettings().stream().mapToDouble(x -> x.getInLegalHoliday().v()).sum();
		}
		
		// 職場月間日数設定を使用するかチェック
		// Input．公休利用単位設定．職場を利用する　＝＝　する
		// AND
		// Input．職場月間日設定　！＝　Empty
		if (publicHolidayManagementUsageUnit.getIsManageWkpPublicHd() == aru && workplaceMonthDaySettingOpt.isPresent()) {
			// 所定公休日数を計算
			WorkplaceMonthDaySetting workplaceMonthDaySetting = workplaceMonthDaySettingOpt.get();
			// 所定公休日数　＝　合計（Input．職場月間日設定．公休日数．公休日数）
			return workplaceMonthDaySetting.getPublicHolidayMonthSettings().stream().mapToDouble(x -> x.getInLegalHoliday().v()).sum();
		}
		
		// 所定公休日数を計算
		// 会社月間日数設定　！＝　Emptyの場合
		// ・所定公休日数　＝　合計（Input．会社月間日設定．公休日数．公休日数）
		// ※１月～１２月
		if (optCompanyMonthDaySettingOpt.isPresent()) {
			CompanyMonthDaySetting companyMonthDaySetting = optCompanyMonthDaySettingOpt.get();
			return companyMonthDaySetting.getPublicHolidayMonthSettings().stream().mapToDouble(x -> x.getInLegalHoliday().v()).sum();
		}
		
		return 0.0;
	}
}
