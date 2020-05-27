package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.AffPeriodEmpCodeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.EmploymentHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedServiceImpl;
@Stateless
public class InterimRemainOffPeriodCreateDataImpl implements InterimRemainOffPeriodCreateData {
	@Inject
	private InterimRemainOffDateCreateData createDataService;
	@Inject
	private EmpSubstVacationRepository empSubsRepos;
	@Inject
	private CompensLeaveEmSetRepository empLeaveSetRepos;
	@Inject
	private ShareEmploymentAdapter employmentService;
	@Inject
	private RemainCreateInforByScheData remainScheData;
	@Inject
	private RemainCreateInforByRecordData remainRecordData;
	@Inject
	private RemainCreateInforByApplicationData remainAppData;
	@Inject
	private ComSubstVacationRepository subRepos;
	@Inject
	private CompensLeaveComSetRepository leaveSetRepos;
	@Inject
	private WorkTypeIsClosedService workTypeIsClosuredService;
	/*require*/
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
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
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private SpecialHolidayRepository holidayRepo;
	/*require*/

	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(
			InterimRemainCreateDataInputPara inputParam, CompanyHolidayMngSetting comHolidaySetting) {
		val require = createRequireImpl();
		val cacheCarrier = new CacheCarrier();
		return createInterimRemainDataMngRequire(require, cacheCarrier, inputParam, comHolidaySetting);
	}
	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMngRequire(
			Require require, CacheCarrier cacheCarrier,
			InterimRemainCreateDataInputPara inputParam, CompanyHolidayMngSetting comHolidaySetting) {
		Map<GeneralDate, DailyInterimRemainMngData> dataOutput = new HashMap<>();
		//アルゴリズム「社員ID（List）と指定期間から社員の雇用履歴を取得」を実行する
		List<String> lstEmployee = new ArrayList<>();
		lstEmployee.add(inputParam.getSid());
		List<SharedSidPeriodDateEmploymentImport> emloymentHist = employmentService.getEmpHistBySidAndPeriodRequire(cacheCarrier, lstEmployee, inputParam.getDateData());
		//所属雇用履歴を設定する
		List<AffPeriodEmpCodeImport> lstEmployment = !emloymentHist.isEmpty() ? emloymentHist.get(0).getAffPeriodEmpCodeExports() : new ArrayList<>();
		List<EmploymentHolidayMngSetting> lstEmplSetting = this.lstEmpHolidayMngSetting(require, inputParam.getCid(), lstEmployment);
		GeneralDate sStartDate = inputParam.getDateData().start();
		GeneralDate sEndDate = inputParam.getDateData().end();
		//対象日の雇用別休暇管理設定を抽出する
		List<AffPeriodEmpCodeImport> lstDateEmployment = lstEmployment.stream()
				.filter(x -> x.getPeriod().start().beforeOrEquals(sStartDate) && x.getPeriod().end().afterOrEquals(sEndDate))
				.collect(Collectors.toList());
		EmploymentHolidayMngSetting employmentHolidaySetting = new EmploymentHolidayMngSetting();
		if(!lstDateEmployment.isEmpty() && lstDateEmployment.size() == 1) {
			AffPeriodEmpCodeImport dateEmployment = lstDateEmployment.get(0);
			List<EmploymentHolidayMngSetting> lstEmploymentSetting = lstEmplSetting.stream()
					.filter(y -> y.getEmploymentCode().equals(dateEmployment.getEmploymentCode()))
					.collect(Collectors.toList());
			if(!lstEmploymentSetting.isEmpty()) {
				employmentHolidaySetting = lstEmploymentSetting.get(0);
			}
		}
		for(int i = 0; sStartDate.daysTo(sEndDate) - i >= 0; i++){
			GeneralDate loopDate = inputParam.getDateData().start().addDays(i);
			if(!inputParam.getAppData().isEmpty()
					&& inputParam.getAppData().get(0).getLstAppDate() != null
					&& !inputParam.getAppData().get(0).getLstAppDate().isEmpty()
					&& inputParam.getAppData().get(0).getLstAppDate().contains(loopDate)) {
				continue;
			}
			if(employmentHolidaySetting == null || employmentHolidaySetting.getEmploymentCode() == null) {
				lstDateEmployment = lstEmployment.stream()
						.filter(x -> x.getPeriod().start().beforeOrEquals(loopDate) && x.getPeriod().end().afterOrEquals(loopDate))
						.collect(Collectors.toList());			
				if(!lstDateEmployment.isEmpty()) {
					AffPeriodEmpCodeImport dateEmployment = lstDateEmployment.get(0);
					List<EmploymentHolidayMngSetting> lstEmploymentSetting = lstEmplSetting.stream()
							.filter(y -> y.getEmploymentCode().equals(dateEmployment.getEmploymentCode()))
							.collect(Collectors.toList());
					if(!lstEmploymentSetting.isEmpty()) {
						employmentHolidaySetting = lstEmploymentSetting.get(0);
					}
				}
			}
			//対象日のデータを抽出する
			InterimRemainCreateInfor dataCreate = this.extractDataOfDate(require, cacheCarrier, inputParam.getCid(),loopDate, inputParam);
			
			
			//アルゴリズム「指定日の暫定残数管理データを作成する」
			DailyInterimRemainMngData outPutdata = createDataService.createData(
					require,
					inputParam.getCid(), 
					inputParam.getSid(),
					loopDate,
					inputParam.isDayOffTimeIsUse(),
					dataCreate,
					comHolidaySetting,
					employmentHolidaySetting);
			if(outPutdata != null) {
				dataOutput.put(loopDate, outPutdata);	
			}			
		}
		
		return dataOutput;
	}

	@Override
	public InterimRemainCreateInfor extractDataOfDate(Require require, CacheCarrier cacheCarrier, String cid,GeneralDate baseDate,
			InterimRemainCreateDataInputPara inputInfor) {
		InterimRemainCreateInfor detailData = new InterimRemainCreateInfor(Optional.empty(), Optional.empty(), Collections.emptyList());
		//実績を抽出する
		List<RecordRemainCreateInfor> recordData = inputInfor.getRecordData()
				.stream()
				.filter(x -> x.getSid().equals(inputInfor.getSid()) && x.getYmd().equals(baseDate))
				.collect(Collectors.toList());
		if(!recordData.isEmpty()) {
			detailData.setRecordData(Optional.of(recordData.get(0)));
		}
		
		//対象日の予定を抽出する
		List<ScheRemainCreateInfor> scheData = inputInfor.getScheData().stream()
				.filter(z -> z.getSid().equals(inputInfor.getSid()) && z.getYmd().equals(baseDate))
				.collect(Collectors.toList());
		if(!scheData.isEmpty()) {
			detailData.setScheData(Optional.of(scheData.get(0)));
		}
		//対象日の申請を抽出する
		List<AppRemainCreateInfor> appData = inputInfor.getAppData().stream()
				.filter(y -> y.getSid().equals(inputInfor.getSid()) 
						&& (y.getAppDate().equals(baseDate)	
								|| (y.getStartDate().isPresent()
										&& y.getEndDate().isPresent()
										&& y.getStartDate().get().beforeOrEquals(baseDate)
										&& y.getEndDate().get().afterOrEquals(baseDate))
								)
						)
				.collect(Collectors.toList());
		appData = appData.stream().sorted((a,b) -> b.getInputDate().compareTo(a.getInputDate())).collect(Collectors.toList());
		Integer excludeHolidayAtr = null;
		if(!appData.isEmpty() && appData.get(0).getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
			excludeHolidayAtr = remainAppData.excludeHolidayAtr(cacheCarrier, cid, appData.get(0).getAppId());
		}
		//申請：　勤務変更申請、休日を除外する		
		//又は　休暇申請
		if((excludeHolidayAtr != null && excludeHolidayAtr == 1)
				|| (!appData.isEmpty() && appData.get(0).getAppType() == ApplicationType.ABSENCE_APPLICATION)) {
			//申請日は休日かチェック、休日なら申請データをセットしない
			if((detailData.getRecordData().isPresent() && workTypeIsClosuredService.checkHolidayRequire(require, detailData.getRecordData().get().getWorkTypeCode()))
					|| (detailData.getScheData().isPresent() && workTypeIsClosuredService.checkHolidayRequire(require, detailData.getScheData().get().getWorkTypeCode()))) {
				return detailData;
			}
		}
		detailData.setAppData(appData);
		return detailData;
		
	}

	@Override
	public List<EmploymentHolidayMngSetting> lstEmpHolidayMngSetting(Require require, String cid, List<AffPeriodEmpCodeImport> lstEmployment) {
		List<EmploymentHolidayMngSetting> lstEmplSetting = new ArrayList<>();
		//雇用別休暇管理設定(List)を作成する
		lstEmployment.stream().forEach(emplData -> {
			//ドメインモデル「雇用振休管理設定」を取得する
			Optional<EmpSubstVacation> optEmpSubData = require.findEmpSubstVacationById(cid, emplData.getEmploymentCode());
			//ドメインモデル「雇用代休管理設定」を取得する
			CompensatoryLeaveEmSetting empSetting = require.findCompensatoryLeaveEmSetting(cid, emplData.getEmploymentCode());
			EmploymentHolidayMngSetting employmentSetting = new EmploymentHolidayMngSetting(emplData.getEmploymentCode(), optEmpSubData, empSetting);
			lstEmplSetting.add(employmentSetting);
		});
		return lstEmplSetting;
	}

	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainByScheRecordApp(
			InterimRemainCreateDataInputPara param) {
		//Input「予定」がNULLかどうかチェック
		if(param.getScheData().isEmpty()) {
			//(Imported)「残数作成元の勤務予定を取得する」
			param.setScheData(remainScheData.createRemainInfor(param.getCid(), param.getSid(), param.getDateData()));
		}
		//Input「実績」がNULLかどうかチェック
		if(param.getRecordData().isEmpty()) {
			param.setRecordData(remainRecordData.lstRecordRemainData(param.getCid(), param.getSid(), param.getDateData()));
		}
		//(Imported)「残数作成元の申請を取得する」
		List<AppRemainCreateInfor> lstAppData = remainAppData.lstRemainDataFromApp(param.getCid(), param.getSid(), param.getDateData());
		//Input「申請」がNULLかどうかチェック
		if(!lstAppData.isEmpty()) {
			lstAppData.addAll(param.getAppData());
			param.setAppData(lstAppData);
		}
		Optional<ComSubstVacation> comSetting = subRepos.findById(param.getCid());
		CompensatoryLeaveComSetting leaveComSetting = leaveSetRepos.find(param.getCid());
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(param.getCid(), comSetting, leaveComSetting);
		InterimRemainCreateDataInputPara createDataParam = new InterimRemainCreateDataInputPara(param.getCid(),
				param.getSid(),
				param.getDateData(),
				param.getRecordData(),
				param.getScheData(),
				param.getAppData(),
				param.isDayOffTimeIsUse());
		return this.createInterimRemainDataMng(createDataParam, comHolidaySetting);
	}

	public static interface Require extends InterimRemainOffDateCreateDataImpl.Require, WorkTypeIsClosedServiceImpl.Require{
//		empSubsRepos.findById(cid, emplData.getEmploymentCode());
		Optional<EmpSubstVacation> findEmpSubstVacationById(String companyId, String contractTypeCode);
//		empLeaveSetRepos.find(cid, emplData.getEmploymentCode());
		CompensatoryLeaveEmSetting findCompensatoryLeaveEmSetting(String companyId, String employmentCode);
		
	}
	
	private Require createRequireImpl() {
		return new InterimRemainOffPeriodCreateDataImpl.Require() {
			@Override
			public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
				return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
			}
			@Override
			public Optional<WorkTimeSetting> findWorkTimeSettingByCode(String companyId, String workTimeCode) {
				return workTimeSet.findByCode(companyId, workTimeCode);
			}
			@Override
			public Optional<FixedWorkSetting> findFixedWorkSettingByKey(String companyId, String workTimeCode) {
				return fixedWorkSet.findByKey(companyId, workTimeCode);
			}
			@Override
			public Optional<FlowWorkSetting> findFlowWorkSetting(String companyId, String workTimeCode) {
				return flowWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public Optional<DiffTimeWorkSetting> findDiffTimeWorkSetting(String companyId, String workTimeCode) {
				return diffWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public Optional<FlexWorkSetting> findFlexWorkSetting(String companyId, String workTimeCode) {
				return flexWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public CompensatoryLeaveComSetting find(String companyId) {
				return compensLeaveComSetRepository.find(companyId);
			}
			@Override
			public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {
				return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode);
			}
			@Override
			public Optional<WorkType> findByDeprecated(String companyId, String workTypeCd) {
				return workTypeRepo.findByDeprecated(companyId, workTypeCd);
			}
			@Override
			public List<Integer> findBySphdSpecLeave(String cid, int sphdSpecLeaveNo) {
				return holidayRepo.findBySphdSpecLeave(cid, sphdSpecLeaveNo);
			}
			@Override
			public Optional<EmpSubstVacation> findEmpSubstVacationById(String companyId, String contractTypeCode) {
				return empSubsRepos.findById(companyId, contractTypeCode);
			}
			@Override
			public CompensatoryLeaveEmSetting findCompensatoryLeaveEmSetting(String companyId, String employmentCode) {
				return empLeaveSetRepos.find(companyId, employmentCode);
			}
			@Override
			public List<Closure> findAllUse(String companyId) {
				return closureRepository.findAllUse(companyId);
			}
			@Override
			public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd) {
				return workTypeRepo.findByPK(companyId, workTypeCd);
			}
			@Override
			public Optional<Closure> findClosureById(String companyId, int closureId) {
				return closureRepository.findById(companyId, closureId);
			}
			@Override
			public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
				return workTypeRepo.findByPK(companyId, workTypeCd);
			}
		};
	}

}
