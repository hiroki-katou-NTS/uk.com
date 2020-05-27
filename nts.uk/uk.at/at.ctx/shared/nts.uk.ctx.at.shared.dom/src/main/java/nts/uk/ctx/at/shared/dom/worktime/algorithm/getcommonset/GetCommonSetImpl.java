package nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

/**
 * 就業時間帯：共通設定の取得
 * @author shuichu_ishida
 */
@Stateless
public class GetCommonSetImpl implements GetCommonSet {

	/** 就業時間帯の設定の取得 */
	@Inject
	public WorkTimeSettingRepository workTimeSet;
	/** 固定勤務設定の取得 */
	@Inject
	public FixedWorkSettingRepository fixedWorkSet;
	/** 流動勤務設定の取得 */
	@Inject
	public FlowWorkSettingRepository flowWorkSet;
	/** 時差勤務設定の取得 */
	@Inject
	public DiffTimeWorkSettingRepository diffWorkSet;
	/** フレックス勤務設定の取得 */
	@Inject
	public FlexWorkSettingRepository flexWorkSet;
	
	/** 共通設定の取得 */
	@Override
	public Optional<WorkTimezoneCommonSet> get(String companyId, String workTimeCode) {
		val require = createRequireImpl();
		return getRequire(require, companyId, workTimeCode);
	}
	
	@Override
	public Optional<WorkTimezoneCommonSet> getRequire(Require require, String companyId, String workTimeCode) {
		val workTimeSetOpt = require.findWorkTimeSettingByCode(companyId, workTimeCode);
		if (!workTimeSetOpt.isPresent()) return Optional.empty();
		
		return this.getCommonRequire(require, companyId, workTimeSetOpt.get());
	}
	
	/** 全ての共通設定の取得 */
	@Override
	public Map<String, WorkTimezoneCommonSet> getAll(String companyId) {

		Map<String, WorkTimezoneCommonSet> results = new HashMap<>();
		
		val workTimeSets = this.workTimeSet.findByCompanyId(companyId);
		for (val workTimeSet : workTimeSets){
			val commonSetOpt = this.getCommon(companyId, workTimeSet);
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
	private Optional<WorkTimezoneCommonSet> getCommon(String companyId, WorkTimeSetting workTimeSetting) {
		
		val require = createRequireImpl();
		return getCommonRequire(require, companyId, workTimeSetting);
	}
	private Optional<WorkTimezoneCommonSet> getCommonRequire(Require require, String companyId, WorkTimeSetting workTimeSet) {
		
		Optional<WorkTimezoneCommonSet> commonSet = Optional.empty();
		
		val workTimeCode = workTimeSet.getWorktimeCode().v();
		val workTimeDivision = workTimeSet.getWorkTimeDivision();
		
		switch (workTimeDivision.getWorkTimeDailyAtr()){
		case REGULAR_WORK:
			switch (workTimeDivision.getWorkTimeMethodSet()){
			case FIXED_WORK:
				val fixedWorkSetOpt = require.findFixedWorkSettingByKey(companyId, workTimeCode);
				if (fixedWorkSetOpt.isPresent()) commonSet = Optional.of(fixedWorkSetOpt.get().getCommonSetting());
				break;
			case FLOW_WORK:
				val flowWorkSetOpt = require.findFlowWorkSetting(companyId, workTimeCode);
				if (flowWorkSetOpt.isPresent()) commonSet = Optional.of(flowWorkSetOpt.get().getCommonSetting());
				break;
			case DIFFTIME_WORK:
				val diffWorkSetOpt = require.findDiffTimeWorkSetting(companyId, workTimeCode);
				if (diffWorkSetOpt.isPresent()) commonSet = Optional.of(diffWorkSetOpt.get().getCommonSet());
				break;
			}
			break;
		case FLEX_WORK:
			val flexWorkSetOpt = require.findFlexWorkSetting(companyId, workTimeCode);
			if (flexWorkSetOpt.isPresent()) commonSet = Optional.of(flexWorkSetOpt.get().getCommonSetting());
			break;
		}
		return commonSet;
	}
	
	public static interface Require{
//		workTimeSet.findByCode(companyId, workTimeCode);
		Optional<WorkTimeSetting> findWorkTimeSettingByCode(String companyId, String workTimeCode);
//		fixedWorkSet.findByKey(companyId, workTimeCode)
		Optional<FixedWorkSetting> findFixedWorkSettingByKey(String companyId, String workTimeCode);
//		flowWorkSet.find(companyId, workTimeCode)
		Optional<FlowWorkSetting> findFlowWorkSetting(String companyId, String workTimeCode);
//		diffWorkSet.find(companyId, workTimeCode)
		Optional<DiffTimeWorkSetting> findDiffTimeWorkSetting(String companyId, String workTimeCode);
//		flexWorkSet.find(companyId, workTimeCode)
		Optional<FlexWorkSetting> findFlexWorkSetting(String companyId,String workTimeCode);
	}


	private Require createRequireImpl() {
		return new GetCommonSetImpl.Require() {
			@Override
			public Optional<WorkTimeSetting> findWorkTimeSettingByCode(String companyId, String workTimeCode) {
				return workTimeSet.findByCode(companyId, workTimeCode);
			}
			@Override
			public Optional<FlowWorkSetting> findFlowWorkSetting(String companyId, String workTimeCode) {
				return flowWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public Optional<FlexWorkSetting> findFlexWorkSetting(String companyId, String workTimeCode) {
				return flexWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public Optional<FixedWorkSetting> findFixedWorkSettingByKey(String companyId, String workTimeCode) {
				return fixedWorkSet.findByKey(companyId, workTimeCode);
			}
			@Override
			public Optional<DiffTimeWorkSetting> findDiffTimeWorkSetting(String companyId, String workTimeCode) {
				return diffWorkSet.find(companyId, workTimeCode);
			}
		};
	}
}