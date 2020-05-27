package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSetImpl;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
//import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
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
 * 実装：指定時間を取得
 * @author shuichu_ishida
 */
@Stateless
public class GetDesignatedTimeImpl implements GetDesignatedTime {

	/** 就業時間帯：共通設定の取得 */
	@Inject
	private GetCommonSet getCommonSet;
	/** 会社別代休時間設定 */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	/*require用*/
	@Inject
	public WorkTimeSettingRepository workTimeSet;
	@Inject
	public FixedWorkSettingRepository fixedWorkSet;
	@Inject
	public FlowWorkSettingRepository flowWorkSet;
	@Inject
	public DiffTimeWorkSettingRepository diffWorkSet;
	@Inject
	public FlexWorkSettingRepository flexWorkSet;
	/*require用*/
	
	
	@Override
	public Optional<SubHolTransferSet> get(String companyId, String workTimeCode) {
		val require = new GetDesignatedTimeImpl.Require() {
			
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
			@Override
			public CompensatoryLeaveComSetting find(String companyId) {
				return compensLeaveComSetRepository.find(companyId);
			}
		};
		return getRequire(require, companyId, workTimeCode);
	}
	
	@Override
	public Optional<SubHolTransferSet> getRequire(Require require, String companyId, String workTimeCode) {
		
		// 共通設定の取得
		val workTimezoneCommonSetOpt = this.getCommonSet.getRequire(require, companyId, workTimeCode);
		if (!workTimezoneCommonSetOpt.isPresent()){
			return Optional.ofNullable(this.getCompanySet(require, companyId));
		}
		
		// 代休振替設定を取得
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.WorkDayOffTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			return Optional.ofNullable(subHolTransferSet);
		}
		
		return Optional.ofNullable(this.getCompanySet(require, companyId));
	}
	
	/**
	 * 会社別代休時間設定から指定時間を取得する
	 * @param companyId 会社ID
	 * @return 指定時間
	 */
	private SubHolTransferSet getCompanySet(Require requirey, String companyId){
		
		val cmpLeaComSet = requirey.find(companyId);
		if (cmpLeaComSet == null) return null;
		for (val cmpOccSet : cmpLeaComSet.getCompensatoryOccurrenceSetting()){
			
			// 使用区分を確認
			if (!cmpOccSet.getTransferSetting().isUseDivision()) continue;
			
			// 「休出から代休発生」以外は、対象外
			if (cmpOccSet.getOccurrenceType() !=
					nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision.WorkDayOffTime){
				continue;
			}
			
			return cmpOccSet.getTransferSetting();
		}
		return null;
	}
	
	public static interface Require extends GetCommonSetImpl.Require{
//		this.compensLeaveComSetRepository.find(companyId);
		CompensatoryLeaveComSetting find(String companyId);
	}
}
