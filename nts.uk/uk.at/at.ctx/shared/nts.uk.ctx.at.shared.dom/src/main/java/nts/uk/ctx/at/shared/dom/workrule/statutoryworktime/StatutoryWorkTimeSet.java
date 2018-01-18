package nts.uk.ctx.at.shared.dom.workrule.statutoryworktime;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employee.EmployeeWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSetting;

/**
 * 法定労働時間設定
 * @author keisuke_hoshina
 *
 */
@Value
public class StatutoryWorkTimeSet {
	private NormalSetting fixedSet;
	private FlexSetting flexSet;
	private DeformationLaborSetting fluidSet;
	
	/**
	 * 会社労働時間設定から法定労働時間設定を取得する
	 * @param companyWtSetting　会社労働時間設定
	 * @return　法定労働時間設定
	 */
	public static StatutoryWorkTimeSet createFromCompany(CompanyWtSetting companyWtSetting) {
		return new StatutoryWorkTimeSet(companyWtSetting.getNormalSetting(),
				companyWtSetting.getFlexSetting(),
				companyWtSetting.getDeformationLaborSetting());
	}
	
	/**
	 * 職場労働時間設定から法定労働時間設定を取得する
	 * @param workPlaceWtSetting　職場労働時間設定
	 * @return　法定労働時間設定
	 */
	public static StatutoryWorkTimeSet createFromWorkPlace(WorkPlaceWtSetting workPlaceWtSetting) {
		return new StatutoryWorkTimeSet(workPlaceWtSetting.getNormalSetting(),
										workPlaceWtSetting.getFlexSetting(),
										workPlaceWtSetting.getDeformationLaborSetting());
	}
	
	/**
	 * 雇用労働時間設定から法定労働時間設定を取得する
	 * @param employmentWtSetting　雇用労働時間設定
	 * @return　法定労働時間設定
	 */
	public static StatutoryWorkTimeSet createFromemployment(EmploymentWtSetting employmentWtSetting) {
		return new StatutoryWorkTimeSet(employmentWtSetting.getNormalSetting(),
				employmentWtSetting.getFlexSetting(),
				employmentWtSetting.getDeformationLaborSetting());
	}
	
	/**
	 * 社員労働時間設定から法定労働時間設定を取得する
	 * @param employeeWtSetting　社員労働観設定
	 * @return　法定労働時間設定
	 */
	public static StatutoryWorkTimeSet createFromemployee(EmployeeWtSetting employeeWtSetting) {
		return new StatutoryWorkTimeSet(employeeWtSetting.getNormalSetting(),
										employeeWtSetting.getFlexSetting(),
										employeeWtSetting.getDeformatinLaborSetting());
	}
	

	/**
	 * 固定勤務の法定内残業にできる時間を求める（一日分）
	 * @param workTime 就業時間（法定外用）
	 * @return
	 */
	public int calculateDailyLimitOfLegalOverworkTimeForFixed(int workTime) {
		return this.fixedSet.calculateDailyLimitOfLegalOverworkTime(workTime);
	}
}
