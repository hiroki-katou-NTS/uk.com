package nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * 就業時間帯：共通設定の取得
 * @author shuichu_ishida
 */
public class GetCommonSet {
	
	/** 共通設定の取得 */
	public static Optional<WorkTimezoneCommonSet> workTimezoneCommonSet(RequireM3 require, String companyId, String workTimeCode) {
		
		return require.workTimeSetting(companyId, workTimeCode)
						.flatMap(workTimeSet -> getCommon(require, companyId, workTimeSet));
	}
	
	/** 全ての共通設定の取得 */
	public static Map<String, WorkTimezoneCommonSet> getAll(RequireM2 require, String companyId) {

		Map<String, WorkTimezoneCommonSet> results = new HashMap<>();
		
		val workTimeSets = require.workTimeSettings(companyId);
		for (val workTimeSet : workTimeSets){
			val commonSetOpt = getCommon(require, companyId, workTimeSet);
			if (!commonSetOpt.isPresent()) continue;
			val workTimeCode = workTimeSet.getWorktimeCode().v();
			results.putIfAbsent(workTimeCode, commonSetOpt.get());
		}
		
		return results;
	}
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param workTimeSet 就業時間帯の設定
	 * @return 就業時間帯の共通設定
	 */
	private static Optional<WorkTimezoneCommonSet> getCommon(RequireM1 require, String companyId, WorkTimeSetting workTimeSet) {
		
		Optional<WorkTimezoneCommonSet> commonSet = Optional.empty();
		
		val workTimeCode = workTimeSet.getWorktimeCode().v();
		val workTimeDivision = workTimeSet.getWorkTimeDivision();
		
		switch (workTimeDivision.getWorkTimeDailyAtr()){
		case REGULAR_WORK:
			switch (workTimeDivision.getWorkTimeMethodSet()){
			case FIXED_WORK:
				val fixedWorkSetOpt = require.fixedWorkSetting(companyId, workTimeCode);
				if (fixedWorkSetOpt.isPresent()) commonSet = Optional.of(fixedWorkSetOpt.get().getCommonSetting());
				break;
			case FLOW_WORK:
				val flowWorkSetOpt = require.flowWorkSetting(companyId, workTimeCode);
				if (flowWorkSetOpt.isPresent()) commonSet = Optional.of(flowWorkSetOpt.get().getCommonSetting());
				break;
			case DIFFTIME_WORK:
				val diffWorkSetOpt = require.diffTimeWorkSetting(companyId, workTimeCode);
				if (diffWorkSetOpt.isPresent()) commonSet = Optional.of(diffWorkSetOpt.get().getCommonSet());
				break;
			}
			break;
		case FLEX_WORK:
			val flexWorkSetOpt = require.flexWorkSetting(companyId, workTimeCode);
			if (flexWorkSetOpt.isPresent()) commonSet = Optional.of(flexWorkSetOpt.get().getCommonSetting());
			break;
		}
		return commonSet;
	}
	
	public static interface RequireM1 {
		
		Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode);

		Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode);

		Optional<DiffTimeWorkSetting> diffTimeWorkSetting(String companyId, String workTimeCode);

		Optional<FlexWorkSetting> flexWorkSetting(String companyId,String workTimeCode);
	}
	
	public static interface RequireM2 extends RequireM1 {
		
		List<WorkTimeSetting> workTimeSettings(String companyId);

	}

	public static interface RequireM3 extends RequireM1 {

		Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode);

	}

}
