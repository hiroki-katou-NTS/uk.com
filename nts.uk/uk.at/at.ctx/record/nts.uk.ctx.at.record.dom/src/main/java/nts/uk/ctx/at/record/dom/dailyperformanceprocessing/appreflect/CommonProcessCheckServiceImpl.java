package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectBreakTimeOfDailyDomainService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AdTimeAndAnyItemAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CommonCompanySettingForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.service.event.breaktime.BreakTimeOfDailyService;
import nts.uk.ctx.at.record.dom.service.event.overtime.OvertimeOfDailyService;
import nts.uk.ctx.at.record.dom.service.event.timeleave.TimeLeavingOfDailyService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CommonProcessCheckServiceImpl implements CommonProcessCheckService{
	@Inject
	private WorkTimeIsFluidWork workTimeisFluidWork;
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private PreOvertimeReflectService preOvertime;
	@Inject
	private CalculateDailyRecordServiceCenter calService;
	@Inject
	private CommonCompanySettingForCalc commonComSetting;
	@Inject
	private AdTimeAndAnyItemAdUpService timeAndAnyItemUpService;
	@Inject
	private ReflectBreakTimeOfDailyDomainService breaktimeSevice;
	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeRepo;
	@Inject
	private WorkingConditionItemRepository workingCondition;
	@Inject
	private TimeLeavingOfDailyService timeLeavingService;
	@Inject
	private BreakTimeOfDailyService breakTimeDailyService;
	@Inject
	private WorkTypeRepository worktypeRepo;
	@Inject
	private OvertimeOfDailyService overTimeService;
	@Inject
	private EditStateOfDailyPerformanceRepository editStateOfDailyRepo;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private EmployeeDailyPerErrorRepository employeeError;
	@Override
	public boolean commonProcessCheck(CommonCheckParameter para) {
		ReflectedStateRecord state = ReflectedStateRecord.CANCELED;
		if(para.getExecutiontype() == ExecutionType.RETURN) {
			return true;
		}
		//実績反映状態
		if(para.getDegressAtr() == DegreeReflectionAtr.RECORD) {
			state = para.getStateReflectionReal();
		} else {
			state = para.getStateReflection();
		}
		if(state == ReflectedStateRecord.WAITREFLECTION) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean reflectScheWorkTimeWorkType(CommonReflectParameter commonPara, boolean isPre, IntegrationOfDaily dailyInfor) {
		//予定勤種を反映できるかチェックする
		if(!this.checkReflectScheWorkTimeType(commonPara, isPre, commonPara.getWorkTimeCode())) {
			return false;
		}
		//予定勤種の反映		
		ReflectParameter para = new ReflectParameter(commonPara.getEmployeeId(), commonPara.getBaseDate(), commonPara.getWorkTimeCode(), 
				commonPara.getWorkTypeCode(), false);
		workTimeUpdate.updateWorkTimeType(para, true, dailyInfor);
		return true;
	}

	@Override
	public boolean checkReflectScheWorkTimeType(CommonReflectParameter commonPara, boolean isPre, String workTimeCode) {
		//INPUT．予定反映区分をチェックする
		if((commonPara.isScheTimeReflectAtr() == true && isPre)
				|| commonPara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAYS_CHANGE_AUTO) {
			return true;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(commonPara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.AUTO_CHANGE_ONLY_WORK) {
			//流動勤務かどうかの判断処理
			return workTimeisFluidWork.checkWorkTimeIsFluidWork(workTimeCode);
		}
		
		return false;
	}

	@Override
	public void updateDailyAfterReflect(List<IntegrationOfDaily>  integrationOfDaily) {
		integrationOfDaily.stream().forEach(x -> {
			WorkInfoOfDailyPerformance workInformation = x.getWorkInformation();
			workRepository.updateByKeyFlush(workInformation);
			editStateOfDailyRepo.updateByKeyFlush(x.getEditState());
			List<EditStateOfDailyPerformance> lstEditStateDB = editStateOfDailyRepo.findByEditState(workInformation.getEmployeeId(),
					workInformation.getYmd(), 
					EditStateSetting.REFLECT_APPLICATION);
			List<Integer> lstEditOfEdit = new ArrayList<>();
			List<Integer> lstEditOfDb = new ArrayList<>();
			x.getEditState().stream().forEach(a ->  {
				lstEditOfEdit.add(a.getAttendanceItemId());
			});
			
			lstEditStateDB.stream().forEach(y -> {
				lstEditOfDb.add(y.getAttendanceItemId());
			});
			lstEditOfEdit.stream().forEach(b -> {
				if(lstEditOfDb.contains(b)) {
					lstEditOfDb.remove(b);
				}
			});
			if(!lstEditOfDb.isEmpty()) {
				editStateOfDailyRepo.deleteByListItemId(workInformation.getEmployeeId(), workInformation.getYmd(), lstEditOfDb);
			}
			if(x.getBreakTime().isEmpty()) {
				breakTimeRepo.delete(x.getWorkInformation().getEmployeeId(), x.getWorkInformation().getYmd());
			} else {
				List<BreakTimeOfDailyPerformance> lstBreakTimeDb = breakTimeRepo.findByKey(x.getWorkInformation().getEmployeeId(), x.getWorkInformation().getYmd());
				if(lstBreakTimeDb.isEmpty()) {
					breakTimeRepo.insert(x.getBreakTime());
				} else {
					breakTimeRepo.update(x.getBreakTime());	
				}
			}
			timeAndAnyItemUpService.addAndUpdate(x);
			employeeError.removeParam(x.getWorkInformation().getEmployeeId(), x.getWorkInformation().getYmd());
			if(!x.getEmployeeError().isEmpty()) {
				employeeError.insert(x.getEmployeeError());	
			}
		});
	}
	@Override
	public void calculateOfAppReflect(IntegrationOfDaily integrationOfDaily, String sid, GeneralDate ymd) {
		
	}
	@Override
	public List<IntegrationOfDaily> lstIntegrationOfDaily(IntegrationOfDaily integrationOfDaily, String sid,
			GeneralDate ymd, boolean isOt) {
		if(integrationOfDaily == null) {
			integrationOfDaily = preOvertime.calculateForAppReflect(sid, ymd);
		}
		Optional<WorkingConditionItem> optWorkingCondition = workingCondition.getBySidAndStandardDate(sid, ymd);
		String companyId = AppContexts.user().companyId();
		//就業時間帯の休憩時間帯を日別実績に反映する
		integrationOfDaily = this.updateBreakTimeInfor(sid, ymd, integrationOfDaily, companyId);
        //2019.02.26　渡邉から
        //残業申請の場合は、自動打刻セットの処理を呼ばない（大塚リリースの時はこの条件で実装する（製品版では、実績の勤務種類、就業時間帯を変更した場合に自動打刻セットを実行するように修正する事（渡邉）
		if(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode() != null
				&& !isOt) {
			Optional<WorkType> workTypeInfor = worktypeRepo.findByPK(companyId, integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
			//出退勤時刻を補正する
			integrationOfDaily = timeLeavingService.correct(companyId, integrationOfDaily, optWorkingCondition, workTypeInfor, false).getData();
			//休憩時間帯を補正する	
			integrationOfDaily = breakTimeDailyService.correct(companyId, integrationOfDaily, workTypeInfor, false).getData();
			//事前残業、休日時間を補正する
			integrationOfDaily = overTimeService.correct(integrationOfDaily, workTypeInfor);
		}
		
		List<IntegrationOfDaily> lstCal = calService.calculateForSchedule(CalculateOption.asDefault(),
				Arrays.asList(integrationOfDaily) , 
				Optional.of(commonComSetting.getCompanySetting()));
		return lstCal;
	}

	@Override
	public IntegrationOfDaily updateBreakTimeInfor(String sid, GeneralDate ymd, IntegrationOfDaily integrationOfDaily, String companyId) {
		//日別実績の休憩時間帯
		BreakTimeOfDailyPerformance breakTimeInfor = null; 
		if(integrationOfDaily.getAttendanceLeave().isPresent()) {
			breakTimeInfor = breaktimeSevice.reflectBreakTime(companyId, 
					sid, 
					ymd, 
					null,
					integrationOfDaily.getAttendanceLeave().get(), 
					integrationOfDaily.getWorkInformation());
		}
				
		List<EditStateOfDailyPerformance> lstEditState = integrationOfDaily.getEditState();
		List<BreakTimeOfDailyPerformance> lstBeforeBreakTimeInfor = integrationOfDaily.getBreakTime();
		List<BreakTimeOfDailyPerformance> beforeBreakTime = lstBeforeBreakTimeInfor.stream().filter(x -> x.getBreakType() == BreakType.REFER_WORK_TIME)
				.collect(Collectors.toList());
		
		BreakTimeOfDailyPerformance beforBTWork = new BreakTimeOfDailyPerformance(sid, BreakType.REFER_WORK_TIME, new ArrayList<>(), ymd);
		if(!beforeBreakTime.isEmpty()) {
			beforBTWork = beforeBreakTime.get(0);
			lstBeforeBreakTimeInfor.remove(beforBTWork);
		} 
		List<BreakTimeSheet> breakTimeSheetsTmp = beforBTWork.getBreakTimeSheets();
		List<BreakTimeSheet> lstBreak = new ArrayList<>();
		if(breakTimeInfor != null) {
			lstBreak = breakTimeInfor.getBreakTimeSheets();
		}
				
		List<BreakTimeSheet> lstBreakOutput = new ArrayList<>();			
		for (int i = 0 ; i < 10 ; i ++ ) {
			int num_1 = (i * 6) + 157;
			int num_2 = num_1 + 2;
			int count = i;
			List<EditStateOfDailyPerformance> lstEditCheck = lstEditState.stream()
					.filter(x -> x.getAttendanceItemId() == num_1 || x.getAttendanceItemId() == num_2)
					.collect(Collectors.toList());
			if (lstEditCheck.isEmpty()) {
				if(!lstBreak.isEmpty()) {
					List<BreakTimeSheet> lstBreakOut = lstBreak.stream().filter(y -> y.getBreakFrameNo().v() == (count + 1))
							.collect(Collectors.toList());
					if(!lstBreakOut.isEmpty()) {
						lstBreakOutput.addAll(lstBreakOut);	
					}
				}
			} else {
				if(!breakTimeSheetsTmp.isEmpty()) {
					List<BreakTimeSheet> lstBreakOut = breakTimeSheetsTmp.stream().filter(y -> y.getBreakFrameNo().v() == count + 1)
							.collect(Collectors.toList());
					if(!lstBreakOut.isEmpty()) {
						lstBreakOutput.addAll(lstBreakOut);	
					}
				}
			}
		}			
				
		beforBTWork.setBreakTimeSheets(lstBreakOutput);
		//breakTimeRepo.update(beforBTWork);
		beforeBreakTime.add(beforBTWork);
		integrationOfDaily.setBreakTime(beforeBreakTime);
		return integrationOfDaily;
	}
}
