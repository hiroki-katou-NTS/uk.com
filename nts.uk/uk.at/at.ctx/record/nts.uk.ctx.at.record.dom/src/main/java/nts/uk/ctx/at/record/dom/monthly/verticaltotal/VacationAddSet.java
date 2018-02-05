package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;

/**
 * 休暇加算設定
 * @author shuichu_ishida
 */
@Getter
public class VacationAddSet {

	/** 年休 */
	private boolean annualLeave;
	/** 積立年休 */
	private boolean retentionYearly;
	/** 特別休暇 */
	private List<String> specialHolidays;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public VacationAddSet(String companyId, RepositoriesRequiredByMonthlyAggr repositories){
		
		this.annualLeave = false;
		this.retentionYearly = false;
		this.specialHolidays = new ArrayList<>();
		
		val annualLeaveSet = repositories.getAnnualPaidLeaveSet().findByCompanyId(companyId);
		if (annualLeaveSet != null) {
			this.annualLeave = annualLeaveSet.getManageAnnualSetting().isWorkDayCalculate();
		}
		val retentionYearlySet = repositories.getRetentionYearlySet().findByCompanyId(companyId);
		if (retentionYearlySet.isPresent()){
			this.retentionYearly = retentionYearlySet.get().getLeaveAsWorkDays();
		}
		//*****（未）　特別休暇の設定クラスが未実装または誤り。判定方法の設計確認要。
	}
}
