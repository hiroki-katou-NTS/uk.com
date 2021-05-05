package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAfterDenyImpl implements DetailAfterDeny {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;

	@Override
	public ProcessResult doDeny(String companyID, String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput, String memo) {
		String loginID = AppContexts.user().employeeId();
		ProcessResult processResult = new ProcessResult();
		List<Application> appLst = new ArrayList<>();
        appLst.add(application);
        if(application.getAppType()==ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
        	Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(appID);
			if(appHdsubRec.isPresent()) {
				if(appHdsubRec.get().getRecAppID().equals(appID)) {
					applicationRepository.findByID(appHdsubRec.get().getAbsenceLeaveAppID()).ifPresent(x -> appLst.add(x));
				} else {
					applicationRepository.findByID(appHdsubRec.get().getRecAppID()).ifPresent(x -> appLst.add(x));
				}
			}
        }
		processResult.setAppIDLst(appLst.stream().map(x -> x.getAppID()).collect(Collectors.toList()));
		boolean isProcessDone = true;
		for(Application appLoop : appLst) {
			// 3.否認する(DenyService)
			Boolean releaseFlg = approvalRootStateAdapter.doDeny(appLoop.getAppID(), loginID, memo);
			isProcessDone = isProcessDone && releaseFlg;
			if(releaseFlg) {
				// 「反映情報」．実績反映状態を「否認」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「否認」)
				for(ReflectionStatusOfDay reflectionStatusOfDay : appLoop.getReflectionStatus().getListReflectionStatusOfDay()) {
					reflectionStatusOfDay.setActualReflectStatus(ReflectedState.DENIAL);
				}
				// アルゴリズム「反映状態の更新」を実行する
				applicationRepository.update(appLoop);
			}
		}
		if(!isProcessDone) {
			return processResult;
		}
		processResult.setProcessDone(true);
		// 暫定データの登録
		List<GeneralDate> dateLst = new ArrayList<>();
		if(application.getAppType()==ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
			dateLst = appLst.stream().map(x -> x.getAppDate().getApplicationDate())
					.sorted(Comparator.comparing(GeneralDate::date))
					.collect(Collectors.toList());
		} else {
			GeneralDate startDate = application.getOpAppStartDate().map(x -> x.getApplicationDate()).orElse(application.getAppDate().getApplicationDate());
			GeneralDate endDate = application.getOpAppEndDate().map(x -> x.getApplicationDate()).orElse(application.getAppDate().getApplicationDate());
			dateLst = new DatePeriod(startDate, endDate).datesBetween();
		}
		interimRemainDataMngRegisterDateChange.registerDateChange(companyID, application.getEmployeeID(), dateLst);
		return processResult;
	}

}
