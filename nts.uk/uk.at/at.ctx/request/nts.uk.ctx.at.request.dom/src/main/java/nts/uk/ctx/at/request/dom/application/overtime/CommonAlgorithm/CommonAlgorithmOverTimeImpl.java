package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

public class CommonAlgorithmOverTimeImpl implements ICommonAlgorithmOverTime {
	
	@Inject
	private WorkingConditionService workingConditionService;
	
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Override
	public QuotaOuput getOvertimeQuotaSetUse(String companyId, String employeeId, GeneralDate date,
			OverTimeAtr overTimeAtr) {
		// pending by domain
		// 社員の労働条件を取得する
		Optional<WorkingConditionItem> workingConditionItem = workingConditionService.findWorkConditionByEmployee(createRequireM1(), employeeId, date);
		
		
		return null;
	}
	
	
	private WorkingConditionService.RequireM1 createRequireM1() {
		return new WorkingConditionService.RequireM1() {
			
			@Override
			public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
				return workingConditionItemRepository.getByHistoryId(historyId);
			}
			
			@Override
			public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
				return workingConditionRepository.getBySidAndStandardDate(companyId, employeeId, baseDate);
			}
		};
	}


	@Override
	public List<WorkType> getWorkType(Optional<AppEmploymentSetting> appEmploymentSettingOp) {
		List<WorkType> workTypes = new ArrayList<>();
		Boolean isC1 = false;
		Boolean isC2 = false;
		Boolean isC3 = false;
		if (appEmploymentSettingOp.isPresent()) {
			AppEmploymentSetting appEmploymentSetting = appEmploymentSettingOp.get();
			isC1 = !appEmploymentSetting.getListWTOAH().isEmpty();
			if (!isC1) {
				isC2 = appEmploymentSetting.getListWTOAH().get(0).getWorkTypeSetDisplayFlg();
				isC3 = !appEmploymentSetting.getListWTOAH().get(0).getWorkTypeList().isEmpty();
			}
			// 「申請別対象勤務種類」をチェックする
			if (isC1 && isC2 && isC3) {
				// ドメインモデル「勤務種類」を取得して返す
				List<WorkType> listWorkType = workTypeRepository.findByCidAndWorkTypeCodes(AppContexts.user().companyId(), appEmploymentSetting.getListWTOAH().get(0).getWorkTypeList());
				if (!workTypes.isEmpty()) {
					workTypes = listWorkType.stream().filter(x -> x.isDeprecated()).collect(Collectors.toList());
				}
			}
			
		} 
		
		if (!(isC1 && isC2 && isC3)) {
			// ドメインモデル「勤務種類」を取得して返す
			workTypes = workTypeRepository.findWorkType(AppContexts.user().companyId(), 0, allDayAtrs(), halfAtrs()).stream()
										  .collect(Collectors.toList());
		}
		// 取得した「勤務種類」をチェック
		if (workTypes.isEmpty()) throw new BusinessException("Msg_1567");
		return workTypes;
	}
	
	/**
	 * // １日の勤務＝以下に該当するもの
	 * 　出勤、休出、振出、連続勤務
	 * @return
	 */
	private List<Integer> allDayAtrs(){
		
		List<Integer> allDayAtrs = new ArrayList<>();
		//出勤
		allDayAtrs.add(0);
		//休出
		allDayAtrs.add(11);
		//振出
		allDayAtrs.add(7);
		// 連続勤務
		allDayAtrs.add(10);
		return allDayAtrs;
	}
	/**
	 * 午前 また 午後 in (休日, 振出, 年休, 出勤, 特別休暇, 欠勤, 代休, 時間消化休暇)
	 * @return
	 */
	private List<Integer> halfAtrs(){
		List<Integer> halfAtrs = new ArrayList<>();
		// 休日
		halfAtrs.add(1);
		// 振出
		halfAtrs.add(7);
		// 年休
		halfAtrs.add(2);
		// 出勤
		halfAtrs.add(0);
		//特別休暇
		halfAtrs.add(4);
		// 欠勤
		halfAtrs.add(5);
		// 代休
		halfAtrs.add(6);
		//時間消化休暇
		halfAtrs.add(9);
		return halfAtrs;
	}


	@Override
	public InfoBaseDateOutput getInfoBaseDate(String companyId, String employeeId, GeneralDate date,
			OverTimeAtr overTimeAtr, List<WorkTimeSetting> workTime,
			Optional<AppEmploymentSetting> appEmploymentSettingOp) {
		if (workTime.isEmpty()) throw new BusinessException("Msg_1568");
		InfoBaseDateOutput output = new InfoBaseDateOutput();
		// 指定社員の申請残業枠を取得する
		QuotaOuput quotaOuput = this.getOvertimeQuotaSetUse(companyId, employeeId, date, overTimeAtr);
		output.setQuotaOutput(quotaOuput);
		// 07_勤務種類取得
		List<WorkType> worktypes = this.getWorkType(appEmploymentSettingOp);
		output.setWorktypes(worktypes);
		// INPUT．「就業時間帯の設定<List>」をチェックする
		return output;
	}


	@Override
	public void getInfoAppDate() {
		// TODO Auto-generated method stub
		
	}

}
