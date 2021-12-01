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
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DetailAfterReleaseImpl implements DetailAfterRelease {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Override
	public ProcessResult detailAfterRelease(String companyID, String appID, Application application) {
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
			// 4.解除する
			Boolean releaseFlg = approvalRootStateAdapter.doRelease(companyID, appLoop.getAppID(), loginID);
			isProcessDone = isProcessDone && releaseFlg;
			if(releaseFlg) {
				// 「反映情報」．実績反映状態を「未反映」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「未反映」)
				for(ReflectionStatusOfDay reflectionStatusOfDay : appLoop.getReflectionStatus().getListReflectionStatusOfDay()) {
					reflectionStatusOfDay.setActualReflectStatus(ReflectedState.NOTREFLECTED);
				}
				// アルゴリズム「反映状態の更新」を実行する ( Thực hiện thuật toán 「Update trạng thái phản ánh」
				applicationRepository.update(appLoop);
			}
		}
		if(!isProcessDone) {
			return processResult;
		}
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
		processResult.setProcessDone(true);
		return processResult;
	}
}
