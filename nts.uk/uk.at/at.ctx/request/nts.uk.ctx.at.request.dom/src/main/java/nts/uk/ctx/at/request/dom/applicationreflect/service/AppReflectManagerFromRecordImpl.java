package nts.uk.ctx.at.request.dom.applicationreflect.service;

/*import nts.arc.task.data.TaskDataSetter;*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.closurestatus.ClosureStatusManagementRequestImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExeStateOfCalAndSumImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionLogRequestImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.SetInforReflAprResultImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.TargetPersonImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.TargetPersonRequestImport;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppReflectManagerFromRecordImpl implements AppReflectManagerFromRecord {
	@Inject
	private TargetPersonRequestImport targetPerson;
	@Inject
	private ExecutionLogRequestImport execuLog;
	@Inject
	private RequestSettingRepository requestSettingRepo;
	@Inject
	private ClosureStatusManagementRequestImport closureStatusImport;
	@Inject
	private ApplicationRepository applicationRepo;
	@Inject
	private AppReflectManager appRefMng;
	@Inject
	private InformationSettingOfAppForReflect appSetting;
	@Inject
	private ManagedParallelWithContext managedParallelWithContext;

	@Inject
	private ApplicationRepository repoApp;

	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ProcessStateReflect applicationRellect(String workId, DatePeriod workDate, AsyncCommandHandlerContext asyncContext) {
		val dataSetter = asyncContext.getDataSetter();
		dataSetter.setData("reflectApprovalCount", 0);
		dataSetter.setData("reflectApprovalHasError", ErrorPresent.NO_ERROR.nameId );
		String cid = AppContexts.user().companyId();
		//ドメインモデル「申請承認設定」を取得する
		Optional<RequestSetting> optRequesSetting = requestSettingRepo.findByCompany(cid);
		if(!optRequesSetting.isPresent()) {
			return ProcessStateReflect.SUCCESS;
		}
		//再実行かどうか判断する 
		Optional<SetInforReflAprResultImport> optRefAppResult = execuLog.optReflectResult(workId, 2);//2: 承認結果反映 
		//対象社員を取得
		List<TargetPersonImport> lstPerson = targetPerson.getTargetPerson(workId)
				.stream()
				.sorted(Comparator.comparing(TargetPersonImport::getEmployeeId))
				.collect(Collectors.toList());
		ExecutionTypeExImport aprResult = optRefAppResult.isPresent() ? optRefAppResult.get().getExecutionType() 
				: ExecutionTypeExImport.NORMAL_EXECUTION;
		InformationSettingOfEachApp reflectSetting = appSetting.getSettingOfEachApp();
		AtomicInteger count = new AtomicInteger(0);
		List<ProcessStateReflect> status = Collections.synchronizedList(new ArrayList<>());

		this.managedParallelWithContext.forEach(lstPerson, x -> {
			if(status.stream().anyMatch(c -> c == ProcessStateReflect.INTERRUPTION)) {
				return;
			}
			count.incrementAndGet();
			//データ更新
			//状態確認
			Optional<ExeStateOfCalAndSumImport> optState = execuLog.executionStatus(workId);
			if(optState.isPresent() && optState.get() == ExeStateOfCalAndSumImport.START_INTERRUPTION) {
				asyncContext.finishedAsCancelled();	
				dataSetter.updateData("reflectApprovalStatus", ExecutionStatusReflect.STOPPING.nameId);
				status.add(ProcessStateReflect.INTERRUPTION);
			}
			//処理した社員の実行状況を「完了」にする
			execuLog.updateLogInfo(x.getEmployeeId(), workId, 2, 0);
			execuLog.updateLogInfo(workId, 2, 0);
			if(dataSetter != null) {
				dataSetter.updateData("reflectApprovalStatus", ExecutionStatusReflect.DONE.nameId);	
			}	
			
			dataSetter.updateData("reflectApprovalStatus", ExecutionStatusReflect.PROCESSING.nameId);
			dataSetter.updateData("reflectApprovalCount", count);
			//社員の申請を反映 (Phản ánh nhân viên)
			this.reflectAppOfEmployee(workId, x.getEmployeeId(),
					workDate, 
					optRequesSetting.get(),
					aprResult,
					reflectSetting);
		});
		if(status.stream().anyMatch(c -> c == ProcessStateReflect.INTERRUPTION)) {
			return ProcessStateReflect.INTERRUPTION;
		}
		dataSetter.updateData("reflectApprovalCount", lstPerson.size());
		//処理した社員の実行状況を「完了」にする
		return ProcessStateReflect.SUCCESS;
	}
	@Override
	public void reflectAppOfEmployee(String workId, String sid, DatePeriod datePeriod,
			RequestSetting optRequesSetting, ExecutionTypeExImport refAppResult, InformationSettingOfEachApp reflectSetting) {
		//ドメインモデル「締め状態管理」を取得する
		Optional<DatePeriod> optClosureStatus = closureStatusImport.closureDatePeriod(sid);
		//「申請期間」を作成する
		//申請期間　←　パラメータ.期間のうちドメインモデル「締め状態管理.期間」に含まれている期間を削除した期間
		DatePeriod appDatePeriod = datePeriod;
		if(optClosureStatus.isPresent()) {
			DatePeriod closureDatePeriod = optClosureStatus.get();
			if(datePeriod.start().beforeOrEquals(closureDatePeriod.end())
					&& closureDatePeriod.end().before(datePeriod.end())) {
				appDatePeriod = new DatePeriod(closureDatePeriod.end().addDays(1), datePeriod.end());
			} else if (closureDatePeriod.end().beforeOrEquals(datePeriod.start())
					&& datePeriod.end().after(closureDatePeriod.end())) {
				GeneralDate sDate = datePeriod.start();
				if(closureDatePeriod.end().equals(datePeriod.start())) {
					sDate = datePeriod.start().addDays(1);
				}
				appDatePeriod = new DatePeriod(sDate, datePeriod.end());
			}	
		}		
		this.reflectAppOfAppDate(workId, sid, refAppResult, reflectSetting, appDatePeriod);		
	}
	@Override
	public void reflectAppOfAppDate(String workId, String sid, ExecutionTypeExImport refAppResult,
			InformationSettingOfEachApp reflectSetting, DatePeriod appDatePeriod) {
		List<Application> lstApp = this.getApps(sid, appDatePeriod, refAppResult);
		List<Application> lstAppTmp = new ArrayList<>(lstApp);
		if(!lstAppTmp.isEmpty()) {
			for (Application x : lstAppTmp) {
				if(!x.getOpAppStartDate().get().equals(x.getOpAppEndDate().get())) {
					this.getAppByManyDay(lstApp, sid, new DatePeriod(x.getOpAppStartDate().get().getApplicationDate(), x.getOpAppEndDate().get().getApplicationDate()), refAppResult);
				}
			}
		}				
		lstApp = lstApp.stream().sorted((a,b) -> a.getInputDate().compareTo(b.getInputDate())).collect(Collectors.toList());
		lstApp.stream().forEach(x -> {
			appRefMng.reflectEmployeeOfApp(x, reflectSetting, refAppResult, workId, 0);
		});
	}
	
	private void getAppByManyDay(List<Application> lstApp, String sid, DatePeriod appDate, ExecutionTypeExImport refAppResult) {
		List<Application> lstTmp = this.getApps(sid, appDate, refAppResult);
		boolean isAdd = false;
		if(!lstTmp.isEmpty()) {
			for (Application a : lstTmp) {
				List<Application> tmp = lstApp.stream().filter(b -> b.getAppID().equals(a.getAppID())).collect(Collectors.toList());
				if(tmp.isEmpty()) {
					lstApp.add(a);
					isAdd = true;
				}
			}	
		}
		if(isAdd) {
			lstTmp.stream().forEach(x -> {
				if(!x.getOpAppStartDate().get().equals(x.getOpAppEndDate().get())) {
					this.getAppByManyDay(lstApp, sid, new DatePeriod(x.getOpAppStartDate().get().getApplicationDate(), x.getOpAppEndDate().get().getApplicationDate()), refAppResult);
				}
			});	
		}
	}
	
	@Override
	public List<Application> getApps(String sid, DatePeriod datePeriod, ExecutionTypeExImport exeType) {
		List<Integer> lstApptype = new ArrayList<>();
		lstApptype.add(ApplicationType.ABSENCE_APPLICATION.value);
		lstApptype.add(ApplicationType.OVER_TIME_APPLICATION.value);
		lstApptype.add(ApplicationType.STAMP_APPLICATION.value);
		lstApptype.add(ApplicationType.HOLIDAY_WORK_APPLICATION.value);
		lstApptype.add(ApplicationType.WORK_CHANGE_APPLICATION.value);
		lstApptype.add(ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		lstApptype.add(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.value);
		List<Integer> lstRecordStatus = new ArrayList<>();
		List<Integer> lstScheStatus = new ArrayList<>();
		List<Application> lstApp = new ArrayList<>();
		// 実行種別を確認
		if(exeType == ExecutionTypeExImport.NORMAL_EXECUTION) {
			//反映待ちの申請を取得
			lstRecordStatus.add(ReflectedState.WAITREFLECTION.value);
			lstScheStatus.add(ReflectedState.WAITREFLECTION.value);			 
		} else {
			//反映済みも含めて申請を取得
			lstRecordStatus.add(ReflectedState.WAITREFLECTION.value);
			lstRecordStatus.add(ReflectedState.REFLECTED.value);
			lstScheStatus.add(ReflectedState.WAITREFLECTION.value);
			lstScheStatus.add(ReflectedState.REFLECTED.value);
		}
		lstApp = applicationRepo.getAppForReflect(sid, datePeriod, lstRecordStatus, lstScheStatus, lstApptype);
		//申請日でソートする		
		return lstApp;
	}

	@Override
	public ProcessStateReflect reflectAppOfEmployeeTotal(String workId, String sid, DatePeriod datePeriod) {
		Optional<ExeStateOfCalAndSumImport> optState = execuLog.executionStatus(workId);
		if(optState.isPresent() && optState.get() == ExeStateOfCalAndSumImport.START_INTERRUPTION) {
			return ProcessStateReflect.INTERRUPTION;
		}
		InformationSettingOfEachApp reflectSetting = appSetting.getSettingOfEachApp();
		//ドメインモデル「申請承認設定」を取得する
		Optional<RequestSetting> optRequesSetting = requestSettingRepo.findByCompany(AppContexts.user().companyId());
		if(!optRequesSetting.isPresent()) {
			return ProcessStateReflect.SUCCESS;
		}
		//再実行かどうか判断する 
		Optional<SetInforReflAprResultImport> optRefAppResult = execuLog.optReflectResult(workId, 2);//2: 承認結果反映 
		ExecutionTypeExImport aprResult = ExecutionTypeExImport.NORMAL_EXECUTION;
		if(optRefAppResult.isPresent()) {
			aprResult = optRefAppResult.get().getExecutionType();
		}
		this.reflectAppOfEmployee(workId, sid, datePeriod, 
				optRequesSetting.get(), aprResult, reflectSetting);
		return ProcessStateReflect.SUCCESS;
	}
	@Override
	public void reflectApplication(List<String> lstID) {
		List<Application> lstApplication = repoApp.findByListID(AppContexts.user().companyId(), lstID);	
		InformationSettingOfEachApp reflectSetting = appSetting.getSettingOfEachApp();
		Map<String, List<Application>> appForSid = new HashMap<>();		
		List<String> lstSid = new ArrayList<>();
		lstApplication.stream().forEach(x -> {
			if(appForSid.containsKey(x.getEmployeeID())) {
				appForSid.get(x.getEmployeeID()).add(x);
			} else {
				lstSid.add(x.getEmployeeID());
				List<Application> tmp = new ArrayList<>();
				tmp.add(x);
				appForSid.put(x.getEmployeeID(), tmp);
			}
		});
		this.managedParallelWithContext.forEach(lstSid, sid -> {
			List<Application> lstApp = appForSid.get(sid);
			lstApp = lstApp.stream().sorted((a,b) -> a.getInputDate().compareTo(b.getInputDate())).collect(Collectors.toList());
			lstApp.stream().forEach(app -> {
				if((app.getPrePostAtr().equals(PrePostAtr.PREDICT)&&
						app.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)
					|| app.getAppType().equals(ApplicationType.HOLIDAY_WORK_APPLICATION))
					|| app.getAppType().equals(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION)
					|| app.getAppType().equals(ApplicationType.WORK_CHANGE_APPLICATION)
					|| app.getAppType().equals(ApplicationType.ABSENCE_APPLICATION)
					|| app.getAppType().equals(ApplicationType.COMPLEMENT_LEAVE_APPLICATION)){
					
					GeneralDate startDate = app.getOpAppStartDate().isPresent() ? app.getOpAppStartDate().get().getApplicationDate() : app.getAppDate().getApplicationDate();
					GeneralDate endDate = app.getOpAppEndDate().isPresent() ? app.getOpAppEndDate().get().getApplicationDate() : app.getAppDate().getApplicationDate();
					this.reflectAppOfAppDate("",
							app.getEmployeeID(),
							ExecutionTypeExImport.RERUN,
							reflectSetting,
							new DatePeriod(startDate, endDate));
				}
			});
		});
	}

}
