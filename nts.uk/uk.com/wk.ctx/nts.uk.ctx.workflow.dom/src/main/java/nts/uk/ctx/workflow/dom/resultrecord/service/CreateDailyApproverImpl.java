package nts.uk.ctx.workflow.dom.resultrecord.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.resultrecord.AppFrameDynamic;
import nts.uk.ctx.workflow.dom.resultrecord.AppPhaseDynamic;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootDynamic;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootDynamicRepository;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirmRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CreateDailyApproverImpl implements CreateDailyApprover {
	
	@Inject
	private AppRootDynamicRepository appRootDynamicRepository;
	
	@Inject
	private AppRootConfirmRepository appRootConfirmRepository;
	
	@Inject
	private CollectApprovalRootService collectApprovalRootService;

	@Override
	public AppRootDynamicContent createDailyApprover(String employeeID, RecordRootType rootType, GeneralDate recordDate) {
		String companyID = AppContexts.user().companyId();
		// 承認ルートを取得する（確認）
		ApprovalRootContentOutput approvalRootContentOutput = collectApprovalRootService.getApprovalRootConfirm(
																companyID, 
																employeeID, 
																EnumAdaptor.valueOf(rootType.value-1, ConfirmationRootType.class), 
																recordDate);
		AppRootDynamic appRootDynamic = new AppRootDynamic(
				approvalRootContentOutput.getApprovalRootState().getRootStateID(), 
				companyID, 
				employeeID, 
				new DatePeriod(recordDate, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd")), 
				rootType, 
				approvalRootContentOutput.getApprovalRootState().getListApprovalPhaseState().stream()
					.map(x -> new AppPhaseDynamic(
							x.getPhaseOrder(), 
							x.getApprovalForm(), 
							x.getListApprovalFrame().stream()
								.map(y -> new AppFrameDynamic(
										y.getFrameOrder(), 
										y.getConfirmAtr().value==1?true:false, 
										y.getListApproverState().stream().map(z -> z.getApproverID())
								.collect(Collectors.toList())))
					.collect(Collectors.toList())))
				.collect(Collectors.toList()));
		ErrorFlag errorFlag = approvalRootContentOutput.getErrorFlag();
		String errorMsgID = "";
		switch (errorFlag) {
		case NO_APPROVER:
			errorMsgID = "Msg_324";
			break;
		case NO_CONFIRM_PERSON:
			errorMsgID = "Msg_326";
			break;
		case APPROVER_UP_10:
			errorMsgID = "Msg_325";
			break;
		default:
			break;
		}
		if(errorFlag!=ErrorFlag.NO_ERROR){
			// create log
		}
		// ドメインモデル「承認ルート中間データ」を取得する
		Optional<AppRootDynamic> opAppRootDynamicConflict = appRootDynamicRepository.findByEmpDate(companyID, employeeID, recordDate, rootType);
		if(opAppRootDynamicConflict.isPresent()){
			// 履歴期間．開始日が一番新しいドメインモデル「承認ルート中間データ」を取得する
			AppRootDynamic appRootDynamicNewest = appRootDynamicRepository.findByEmpDateNewest(companyID, employeeID, rootType).get();
			// output．承認ルートの内容は取得したドメインモデル「承認ルート中間データ」を比較する
			boolean isSame = compareAppRootContent(appRootDynamicNewest, appRootDynamic);
			if(isSame){
				return new AppRootDynamicContent(appRootDynamicNewest, errorFlag, errorMsgID);
			}
		}
		// 取得した承認ルートをドメインモデル「承認ルート中間データ」にINSERTする
		appRootDynamicRepository.insert(appRootDynamic);
		// 履歴期間．開始日が一番新しいドメインモデル「承認ルート中間データ」をUPDATEする
		appRootDynamicRepository.findByEmpDateNewest(companyID, employeeID, rootType).ifPresent(x -> {
			GeneralDate start = x.getDatePeriod().start();
			GeneralDate end = x.getDatePeriod().end().addDays(-1);
			x.setDatePeriod(new DatePeriod(start, end));
			appRootDynamicRepository.update(x);
		});
		//承認状態をクリアする
		appRootConfirmRepository.clearStatus(companyID, employeeID, recordDate, rootType);
		return new AppRootDynamicContent(appRootDynamic, errorFlag, errorMsgID);
	}
	
	private boolean compareAppRootContent(AppRootDynamic oldAppRoot, AppRootDynamic newAppRoot){
		for(AppPhaseDynamic oldAppPhase : oldAppRoot.getListAppPhase()){
			Optional<AppPhaseDynamic> opNewAppPhaseLoop = 
					newAppRoot.getListAppPhase().stream().filter(x -> x.getPhaseOrder()==oldAppPhase.getPhaseOrder()).findAny();
			if(!opNewAppPhaseLoop.isPresent()){
				return false;
			}
			boolean isSame = compareAppPhaseContent(oldAppPhase, opNewAppPhaseLoop.get());
			if(!isSame){
				return false;
			}
		}
		return true;
	}
	
	private boolean compareAppPhaseContent(AppPhaseDynamic oldAppPhase, AppPhaseDynamic newAppPhase){
		for(AppFrameDynamic oldAppFrame : oldAppPhase.getListAppFrame()){
			Optional<AppFrameDynamic> opNewAppFrameLoop = 
					newAppPhase.getListAppFrame().stream().filter(x -> x.getFrameOrder()==oldAppFrame.getFrameOrder()).findAny();
			if(!opNewAppFrameLoop.isPresent()){
				return false;
			}
			boolean isSame = compareAppFrameContent(oldAppFrame, opNewAppFrameLoop.get());
			if(!isSame){
				return false;
			}
		}
		return true;
	}
	
	private boolean compareAppFrameContent(AppFrameDynamic oldAppFrame, AppFrameDynamic newAppFrame){
		List<String> oldList = oldAppFrame.getListApprover();
		List<String> newList = newAppFrame.getListApprover();
		return oldList.containsAll(newList) && newList.containsAll(oldList);
	}

}
