package nts.uk.ctx.at.request.dom.applicationreflect.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.closurestatus.ClosureStatusManagementRequestImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionLogRequestImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.SetInforReflAprResultImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.TargetPersonImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.TargetPersonRequestImport;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AppReflectManagerFromRecordImpl implements AppReflectManagerFromRecord{
	@Inject
	private TargetPersonRequestImport targetPerson;
	@Inject
	private ExecutionLogRequestImport execuLog;
	@Inject
	private RequestSettingRepository requestSettingRepo;
	@Inject
	private ClosureStatusManagementRequestImport closureStatusImport;
	@Inject
	private ApplicationRepository_New applicationRepo;
	@Inject
	private AppReflectManager appRefMng;
	@Override
	public boolean applicationRellect(String workId, WorkReflectAtr workAtr, DatePeriod workDate, AsyncCommandHandlerContext asyncContext) {
		val dataSetter = asyncContext.getDataSetter();
		dataSetter.setData("dailyCalculateCount", 0);
		dataSetter.setData("dailyCalculateHasError", ErrorPresent.NO_ERROR.nameId );
		//実行状況を「処理中」にする (set trạng thái thực hiện) - TODO xem lai
		//ドメインモデル「申請承認設定」を取得する
		Optional<RequestSetting> optRequesSetting = requestSettingRepo.findByCompany(AppContexts.user().companyId());
		//再実行かどうか判断する 
		Optional<SetInforReflAprResultImport> optRefAppResult = execuLog.optReflectResult(workId, 2);//2: 承認結果反映 
		//対象社員を取得
		List<TargetPersonImport> lstPerson = targetPerson.getTargetPerson(workId)
				.stream()
				.sorted(Comparator.comparing(TargetPersonImport::getEmployeeId))
				.collect(Collectors.toList());
		ExecutionTypeExImport aprResult = ExecutionTypeExImport.NORMAL_EXECUTION;
		if(optRefAppResult.isPresent()) {
			aprResult = optRefAppResult.get().getExecutionType();
		}
		for (TargetPersonImport targetPersonImport : lstPerson) {
			//社員の申請を反映 (Phản ánh nhân viên)
			if(!this.reflectAppOfEmployee(workId, targetPersonImport.getEmployeeId(), workDate, optRequesSetting.get(), aprResult)) {
				
			}
			//TODO
		}
		return false;
	}
	@Override
	public boolean reflectAppOfEmployee(String workId, String sid, DatePeriod datePeriod,
			RequestSetting optRequesSetting, ExecutionTypeExImport refAppResult) {
		
		//ドメインモデル「締め状態管理」を取得する
		Optional<DatePeriod> optClosureStatus = closureStatusImport.closureDatePeriod(sid);
		//「申請期間」を作成する
		//申請期間　←　パラメータ.期間のうちドメインモデル「締め状態管理.期間」に含まれている期間を削除した期間
		if(!optClosureStatus.isPresent()) {
			return false;
		}
		DatePeriod closureDatePeriod = optClosureStatus.get();
		DatePeriod appDatePeriod = null;
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
		if(appDatePeriod == null) {
			return false;
		}
		
		List<Application_New> lstApp = this.getApps(sid, datePeriod, refAppResult);
		if(lstApp.isEmpty()) {
			return true;
		}
		for (Application_New appData : lstApp) {
			if(appRefMng.reflectEmployeeOfApp(appData)) {
				//TODO
			} else {
				//TODO 
			}
		}
		return false;
	}
	@Override
	public List<Application_New> getApps(String sid, DatePeriod datePeriod, ExecutionTypeExImport exeType) {
		List<Integer> lstApptype = new ArrayList<>();
		lstApptype.add(ApplicationType.ABSENCE_APPLICATION.value);
		lstApptype.add(ApplicationType.OVER_TIME_APPLICATION.value);
		lstApptype.add(ApplicationType.STAMP_APPLICATION.value);
		lstApptype.add(ApplicationType.BREAK_TIME_APPLICATION.value);
		lstApptype.add(ApplicationType.WORK_CHANGE_APPLICATION.value);
		lstApptype.add(ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		lstApptype.add(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.value);
		List<Integer> lstRecordStatus = new ArrayList<>();
		List<Integer> lstScheStatus = new ArrayList<>();
		List<Application_New> lstApp = new ArrayList<>();
		// 実行種別を確認
		if(exeType == ExecutionTypeExImport.NORMAL_EXECUTION) {
			//反映待ちの申請を取得
			lstRecordStatus.add(ReflectedState_New.WAITREFLECTION.value);
			lstScheStatus.add(ReflectedState_New.WAITREFLECTION.value);			 
		} else {
			//反映済みも含めて申請を取得
			lstRecordStatus.add(ReflectedState_New.WAITREFLECTION.value);
			lstRecordStatus.add(ReflectedState_New.REFLECTED.value);
			lstScheStatus.add(ReflectedState_New.WAITREFLECTION.value);
			lstScheStatus.add(ReflectedState_New.REFLECTED.value);
		}
		lstApp = applicationRepo.getAppForReflect(sid, datePeriod, lstRecordStatus, lstScheStatus, lstApptype);
		lstApp = lstApp.stream().sorted(Comparator.comparing(Application_New :: getAppDate))
				.collect(Collectors.toList());
		//TODO 申請日でソートする
		
		return lstApp;
	}

}
