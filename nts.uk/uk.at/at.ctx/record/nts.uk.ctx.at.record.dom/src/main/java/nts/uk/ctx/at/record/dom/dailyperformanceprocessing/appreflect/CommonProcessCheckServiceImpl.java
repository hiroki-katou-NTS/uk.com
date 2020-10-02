package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectBreakTimeOfDailyDomainService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AdTimeAndAnyItemAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.service.event.breaktime.BreakTimeOfDailyService;
import nts.uk.ctx.at.record.dom.service.event.common.CorrectEventConts;
import nts.uk.ctx.at.record.dom.service.event.overtime.OvertimeOfDailyService;
import nts.uk.ctx.at.record.dom.service.event.schetimeleave.ScheTimeLeavingOfDailyService;
import nts.uk.ctx.at.record.dom.service.event.timeleave.TimeLeavingOfDailyService;
import nts.uk.ctx.at.record.dom.service.event.worktime.WorkTimeOfDailyService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CommonProcessCheckServiceImpl implements CommonProcessCheckService{
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private CalculateDailyRecordServiceCenter calService;
	@Inject
	private AdTimeAndAnyItemAdUpService timeAndAnyItemUpService;
	@Inject
	private ReflectBreakTimeOfDailyDomainService breaktimeSevice;
	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeRepo;
	@Inject
	private TimeLeavingOfDailyService timeLeavingService;
	@Inject
	private BreakTimeOfDailyService breakTimeDailyService;
	@Inject
	private WorkTypeRepository worktypeRepo;
	@Inject
	private OvertimeOfDailyService overTimeService;
	@Inject
	private EmployeeDailyPerErrorRepository employeeError;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private ErrMessageInfoRepository errMessInfo;
	@Inject
	private ScheTimeLeavingOfDailyService scheTimeService;
	@Inject
	private EditStateOfDailyPerformanceRepository dailyReposiroty;
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	@Inject
	private WorkTimeOfDailyService workTimeDailyService;
	@Inject
	private DailyRecordAdUpService dailyService;
	@Inject 
	private RecordDomRequireService requireService;
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
	public void reflectScheWorkTimeWorkType(CommonReflectParameter commonPara, boolean isPre,
			IntegrationOfDaily dailyInfor) {
		//予定勤種の反映		
		ReflectParameter para = new ReflectParameter(commonPara.getEmployeeId(), commonPara.getAppDate(), commonPara.getWorkTimeCode(), 
				commonPara.getWorkTypeCode(), false);
		workTimeUpdate.updateWorkTimeType(para, true, dailyInfor);
	}

	@Override
	public boolean checkReflectScheWorkTimeType(CommonReflectParameter commonPara, boolean isPre, String workTimeCode) {
		if(!isPre) {
			return false;
		}
		//INPUT．予定反映区分をチェックする
		if((commonPara.isScheTimeReflectAtr() == true && isPre)
				|| commonPara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAYS_CHANGE_AUTO) {
			return true;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(commonPara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.AUTO_CHANGE_ONLY_WORK) {
			//流動勤務かどうかの判断処理
			return WorkTimeIsFluidWork.checkWorkTimeIsFluidWork(requireService.createRequire(), workTimeCode);
		}
		
		return false;
	}

	@Override
	public void calculateOfAppReflect(CommonCalculateOfAppReflectParam commonPara) {
		String companyId = AppContexts.user().companyId();
		//就業時間帯の休憩時間帯を日別実績に反映する
		if(commonPara.getAppType() != ApplicationType.OVER_TIME_APPLICATION) { //2019/10/09 DUDT　今まで事前残業申請しか対応しない実績の項目に反映してないから休憩時間を補正することがいらない
			this.updateBreakTimeInfor(commonPara.getSid(),
					commonPara.getYmd(),
					commonPara.getIntegrationOfDaily(), companyId, commonPara.getAppType());	
		}
		
		Optional<WorkType> workTypeInfor = Optional.empty();
		if(commonPara.getIntegrationOfDaily().getWorkInformation().getRecordInfo().getWorkTypeCode() != null) {
			workTypeInfor = worktypeRepo.findByPK(companyId, 
					commonPara.getIntegrationOfDaily().getWorkInformation().getRecordInfo().getWorkTypeCode().v());
			//就業時間帯の補正処理
			workTimeDailyService.correct(companyId, commonPara.getIntegrationOfDaily());
			//2019.02.26　渡邉から
			//残業申請の場合は、自動打刻セットの処理を呼ばない（大塚リリースの時はこの条件で実装する（製品版では、実績の勤務種類、就業時間帯を変更した場合に自動打刻セットを実行するように修正する事（渡邉）
			if(commonPara.getAppType() != ApplicationType.OVER_TIME_APPLICATION) {
				List<EditStateOfDailyAttd> lstTime = commonPara.getIntegrationOfDaily().getEditState().stream()
						.filter(x -> CorrectEventConts.LEAVE_ITEMS.contains(x.getAttendanceItemId()) 
								|| CorrectEventConts.ATTENDANCE_ITEMS.contains(x.getAttendanceItemId()))
						.collect(Collectors.toList());
				if(!(commonPara.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION && !commonPara.isPreRequest())) {
					//予定出退勤時刻を反映する
					scheTimeService.correct(companyId,
							commonPara.getWorkTypeCode(),
							commonPara.getWorkTimeCode(),
							commonPara.getStartTime(),
							commonPara.getEndTime(),
							commonPara.getIntegrationOfDaily());	
				}				
				if(commonPara.getAppType() != ApplicationType.BREAK_TIME_APPLICATION ||
						(commonPara.getAppType() == ApplicationType.BREAK_TIME_APPLICATION && lstTime.isEmpty())) {
					//出退勤時刻を補正する
					timeLeavingService.correct(companyId, commonPara.getIntegrationOfDaily(), Optional.empty(), workTypeInfor, false).getData();	
				}				
				// 申請された時間を補正する
				overTimeService.correct(commonPara.getIntegrationOfDaily(), workTypeInfor, true);
				//Neu khong phai don xin di lam vao ngay nghi va don xin di lam vao ngay nghi ko tich chon phan anh gio nghi
				if(workTypeInfor.isPresent() && (!workTypeInfor.get().getDailyWork().isHolidayWork()
						|| (workTypeInfor.get().getDailyWork().isHolidayWork() && commonPara.getAppType() != ApplicationType.BREAK_TIME_APPLICATION))) {
					//休憩時間帯を補正する	
					breakTimeDailyService.correct(companyId, commonPara.getIntegrationOfDaily(), workTypeInfor, false).getData();
				}
			}
		}
		
		List<IntegrationOfDaily> lstCal = calService.calculateForSchedule(CalculateOption.asDefault(),
				Arrays.asList(commonPara.getIntegrationOfDaily()) , 
				Optional.empty());
		IntegrationOfDaily x = lstCal.get(0);
		if(commonPara.getIntegrationOfDaily().getBreakTime().isEmpty()) {
			breakTimeRepo.delete(commonPara.getSid(), commonPara.getYmd());
		} else {
			breakTimeRepo.update(commonPara.getIntegrationOfDaily().getBreakTime()
					.stream().map(mapper-> new BreakTimeOfDailyPerformance(commonPara.getSid(), commonPara.getYmd(), mapper)).collect(Collectors.toList()));	
		}
		commonPara.getIntegrationOfDaily().getAttendanceLeave().ifPresent(a -> timeLeavingOfDaily.updateFlush(new TimeLeavingOfDailyPerformance(commonPara.getSid(), commonPara.getYmd(), a)));
		workRepository.updateByKeyFlush(new WorkInfoOfDailyPerformance(commonPara.getSid(), commonPara.getYmd(), x.getWorkInformation()));
		timeAndAnyItemUpService.addAndUpdate(lstCal);
		dailyReposiroty.addAndUpdate(commonPara.getIntegrationOfDaily().getEditState().stream().map(mapper-> new EditStateOfDailyPerformance(commonPara.getSid(), commonPara.getYmd(), mapper)).collect(Collectors.toList()));
		Map<String, List<GeneralDate>> param = new HashMap<>();
		param.put(commonPara.getSid(), Arrays.asList(commonPara.getYmd()));		
		employeeError.removeNotOTK(param);
		if(!x.getEmployeeError().isEmpty()) {
			employeeError.update(x.getEmployeeError());	
		}
		//特定のエラーが発生している社員の確認、承認をクリアする
		dailyService.removeConfirmApproval(lstCal,
				commonPara.iPUSOpt,
				commonPara.approvalSet);
	}

	@Override
	public IntegrationOfDaily updateBreakTimeInfor(String sid, GeneralDate ymd, IntegrationOfDaily integrationOfDaily, String companyId,
			ApplicationType appType) {
		//日別実績の休憩時間帯
		BreakTimeOfDailyPerformance breakTimeInfor = null; 
		TimeLeavingOfDailyPerformance leavingOfDailyPerformance = new TimeLeavingOfDailyPerformance(sid, ymd, integrationOfDaily.getAttendanceLeave().get());
		WorkInfoOfDailyPerformance infoOfDailyPerformance = new WorkInfoOfDailyPerformance(sid, ymd, integrationOfDaily.getWorkInformation());
		if(integrationOfDaily.getAttendanceLeave().isPresent()) {
			breakTimeInfor = breaktimeSevice.reflectBreakTime(companyId, 
					sid, 
					ymd, 
					null,
					leavingOfDailyPerformance, 
					infoOfDailyPerformance);
		}
				
		List<EditStateOfDailyAttd> lstEditState = integrationOfDaily.getEditState();
		List<BreakTimeOfDailyAttd> lstBeforeBreakTimeInfor = integrationOfDaily.getBreakTime();
		List<BreakTimeOfDailyAttd> beforeBreakTime = lstBeforeBreakTimeInfor.stream().filter(x -> x.getBreakType() == BreakType.REFER_WORK_TIME)
				.collect(Collectors.toList());
		if(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode() != null) {
			Optional<WorkType> workTypeInfor = worktypeRepo.findByPK(companyId, integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
			//休日出勤申請しか反映してない
			if(appType == ApplicationType.BREAK_TIME_APPLICATION
					&& workTypeInfor.isPresent() && workTypeInfor.get().getDailyWork().isHolidayWork()) {
				if(beforeBreakTime.isEmpty()) {
					integrationOfDaily.getBreakTime().add(breakTimeInfor.getTimeZone());
				} else {
					integrationOfDaily.getBreakTime().removeAll(beforeBreakTime);
					integrationOfDaily.getBreakTime().add(breakTimeInfor.getTimeZone());
				}
				return integrationOfDaily;
			}
		}
		
		BreakTimeOfDailyPerformance beforBTWork = new BreakTimeOfDailyPerformance(sid, BreakType.REFER_WORK_TIME, new ArrayList<>(), ymd);
		if(!beforeBreakTime.isEmpty()) {
			beforBTWork = new BreakTimeOfDailyPerformance(sid, BreakType.REFER_WORK_TIME, beforeBreakTime.get(0).getBreakTimeSheets(), ymd);
			lstBeforeBreakTimeInfor.remove(beforBTWork);
		} 
		List<BreakTimeSheet> breakTimeSheetsTmp = beforBTWork.getTimeZone().getBreakTimeSheets();
		List<BreakTimeSheet> lstBreak = new ArrayList<>();
		if(breakTimeInfor != null) {
			lstBreak = breakTimeInfor.getTimeZone().getBreakTimeSheets();
		}
				
		List<BreakTimeSheet> lstBreakOutput = new ArrayList<>();			
		for (int i = 0 ; i < 10 ; i ++ ) {
			int num_1 = (i * 6) + 157;
			int num_2 = num_1 + 2;
			int count = i + 1;
			
			List<EditStateOfDailyAttd> lstEditCheck = lstEditState.stream()
					.filter(x -> x.getAttendanceItemId() == num_1 || x.getAttendanceItemId() == num_2)
					.collect(Collectors.toList());
			if(!lstEditCheck.isEmpty() && lstEditCheck.size() == 2
					&& lstEditCheck.get(0).getEditStateSetting() == EditStateSetting.REFLECT_APPLICATION) {
				lstEditCheck.clear();
				integrationOfDaily.getBreakTime().stream().forEach(x -> {
					List<BreakTimeSheet> b = new ArrayList<>(x.getBreakTimeSheets());
					b.stream().forEach(a -> {
						if(a.getBreakFrameNo().v() == count) {
							x.getBreakTimeSheets().remove(a);
						}
					});
				});
			}
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
		lstBeforeBreakTimeInfor.add(beforBTWork.getTimeZone());
		if(!beforeBreakTime.isEmpty()) {
			integrationOfDaily.setBreakTime(lstBeforeBreakTimeInfor);
		} else {
			integrationOfDaily.setBreakTime(integrationOfDaily.getBreakTime().stream().map(x -> {
				if(x.getBreakType() == BreakType.REFER_WORK_TIME) {
					return new BreakTimeOfDailyAttd(x.getBreakType(), x.getBreakTimeSheets());
				}
				return x;
			}).collect(Collectors.toList()));
		}
		return integrationOfDaily;
	}

	private boolean isReflectBreakTime(List<EditStateOfDailyPerformance> lstEditState) {
		List<EditStateOfDailyPerformance> lstEditReflect = lstEditState.stream()
				.filter(x -> (workTimeUpdate.lstBreakStartTime().contains(x.getEditState().getAttendanceItemId()) 
							|| workTimeUpdate.lstBreakEndTime().contains(x.getEditState().getAttendanceItemId())                            
							|| workTimeUpdate.lstScheBreakStartTime().contains(x.getEditState().getAttendanceItemId())
                            || workTimeUpdate.lstScheBreakEndTime().contains(x.getEditState().getAttendanceItemId()))
						&& x.getEditState().getEditStateSetting() == EditStateSetting.REFLECT_APPLICATION)
				.collect(Collectors.toList());
		return lstEditReflect.isEmpty() ? false : true;
	}
	@Override
	public void createLogError(String sid, GeneralDate ymd, String excLogId) {
		ErrMessageInfo errMes = new ErrMessageInfo(sid, 
				excLogId,
				new ErrMessageResource("024"),
				EnumAdaptor.valueOf(2, ExecutionContent.class),
				ymd,
				new ErrMessageContent(TextResource.localize("Msg_1541")));
		this.errMessInfo.add(errMes);
	}
}
