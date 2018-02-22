package nts.uk.ctx.at.record.dom.monthly;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * 就業時間帯の共通設定の取得
 * @author shuichu_ishida
 */
public class GetWorkTimezoneCommonSet {

	/**
	 * 就業時間帯の共通設定の取得
	 * @param companyId 会社ID
	 * @param workTimeCode 就業時間帯コード
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 就業時間帯の共通設定
	 */
	public static Optional<WorkTimezoneCommonSet> get(
			String companyId, String workTimeCode, RepositoriesRequiredByMonthlyAggr repositories){

		Optional<WorkTimezoneCommonSet> returnValue = Optional.empty();
		
		val workTimeSetOpt = repositories.getWorkTimeSet().findByCode(companyId, workTimeCode);
		if (!workTimeSetOpt.isPresent()) return returnValue;
		val workTimeDivision = workTimeSetOpt.get().getWorkTimeDivision();
		
		switch (workTimeDivision.getWorkTimeDailyAtr()){
		case REGULAR_WORK:
			switch (workTimeDivision.getWorkTimeMethodSet()){
			case FIXED_WORK:
				val fixedWorkSetOpt = repositories.getFixedWorkSet().findByKey(companyId, workTimeCode);
				if (fixedWorkSetOpt.isPresent()) returnValue = Optional.of(fixedWorkSetOpt.get().getCommonSetting());
				break;
			case FLOW_WORK:
				val flowWorkSetOpt = repositories.getFlowWorkSet().find(companyId, workTimeCode);
				if (flowWorkSetOpt.isPresent()) returnValue = Optional.of(flowWorkSetOpt.get().getCommonSetting());
				break;
			case DIFFTIME_WORK:
				val diffWorkSetOpt = repositories.getDiffWorkSet().find(companyId, workTimeCode);
				if (diffWorkSetOpt.isPresent()) returnValue = Optional.of(diffWorkSetOpt.get().getCommonSet());
				break;
			}
			break;
		case FLEX_WORK:
			val flexWorkSetOpt = repositories.getFlexWorkSet().find(companyId, workTimeCode);
			if (flexWorkSetOpt.isPresent()) returnValue = Optional.of(flexWorkSetOpt.get().getCommonSetting());
			break;
		}
		return returnValue;
	}
}
