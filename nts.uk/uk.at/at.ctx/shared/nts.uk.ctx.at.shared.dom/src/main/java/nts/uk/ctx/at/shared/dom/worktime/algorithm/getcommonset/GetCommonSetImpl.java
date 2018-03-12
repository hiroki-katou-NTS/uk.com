package nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
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
		
		Optional<WorkTimezoneCommonSet> commonSet = Optional.empty();
		
		val workTimeSetOpt = this.workTimeSet.findByCode(companyId, workTimeCode);
		if (!workTimeSetOpt.isPresent()) return commonSet;
		val workTimeDivision = workTimeSetOpt.get().getWorkTimeDivision();
		
		switch (workTimeDivision.getWorkTimeDailyAtr()){
		case REGULAR_WORK:
			switch (workTimeDivision.getWorkTimeMethodSet()){
			case FIXED_WORK:
				val fixedWorkSetOpt = this.fixedWorkSet.findByKey(companyId, workTimeCode);
				if (fixedWorkSetOpt.isPresent()) commonSet = Optional.of(fixedWorkSetOpt.get().getCommonSetting());
				break;
			case FLOW_WORK:
				val flowWorkSetOpt = this.flowWorkSet.find(companyId, workTimeCode);
				if (flowWorkSetOpt.isPresent()) commonSet = Optional.of(flowWorkSetOpt.get().getCommonSetting());
				break;
			case DIFFTIME_WORK:
				val diffWorkSetOpt = this.diffWorkSet.find(companyId, workTimeCode);
				if (diffWorkSetOpt.isPresent()) commonSet = Optional.of(diffWorkSetOpt.get().getCommonSet());
				break;
			}
			break;
		case FLEX_WORK:
			val flexWorkSetOpt = this.flexWorkSet.find(companyId, workTimeCode);
			if (flexWorkSetOpt.isPresent()) commonSet = Optional.of(flexWorkSetOpt.get().getCommonSetting());
			break;
		}
		return commonSet;
	}
}
