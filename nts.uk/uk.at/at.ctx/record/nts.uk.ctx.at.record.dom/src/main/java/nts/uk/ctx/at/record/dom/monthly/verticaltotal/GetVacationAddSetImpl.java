package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;

/**
 * 実装：休暇加算設定を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetVacationAddSetImpl implements GetVacationAddSet {

	/** 年休設定 */
	@Inject
	public AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 積立年休設定 */
	@Inject
	public RetentionYearlySettingRepository retentionYearlySet;
	/** 特別休暇設定 */
	//@Inject
	//public SpecialHolidayRepository specialHolidaySet;
	
	/** 休暇加算設定を取得する */
	@Override
	public VacationAddSet get(String companyId) {
		
		boolean annualLeave = false;
		boolean retentionYearly = false;
		List<String> specialHolidays = new ArrayList<>();
		
		val annualLeaveSet = this.annualPaidLeaveSet.findByCompanyId(companyId);
		if (annualLeaveSet != null) {
			annualLeave = annualLeaveSet.getManageAnnualSetting().isWorkDayCalculate();
		}
		val retentionYearlySet = this.retentionYearlySet.findByCompanyId(companyId);
		if (retentionYearlySet.isPresent()){
			retentionYearly = retentionYearlySet.get().getLeaveAsWorkDays();
		}
		//*****（未）　特別休暇の設定クラスが未実装または誤り。判定方法の設計確認要。
		
		return VacationAddSet.of(annualLeave, retentionYearly, specialHolidays);
	}
}
