package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.ApprovalProcessParam;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessApprovalOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class ApproveAppHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ApproveProcessResult> {

	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject
	private DetailAfterApproval detailAfterApproval;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;

	@Override
	protected ApproveProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppDetailBehaviorCmd cmd = context.getCommand(); 
		String memo = cmd.getMemo();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		AppDetailScreenInfo appDetailScreenInfo = appDispInfoStartupOutput.getAppDetailScreenInfo().get();
		Application application = appDetailScreenInfo.getApplication();
		ApprovalProcessParam approvalProcessParam = new ApprovalProcessParam(
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet(),
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null));
		return approve(companyID, application.getAppID(), application, approvalProcessParam, memo, Collections.emptyList(), false);
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通ユースケース.承認する
	 * @param companyID
	 * @param appID
	 * @param application
	 * @param appDispInfoStartupOutput
	 * @param memo
	 * @return
	 */
	public ApproveProcessResult approve(String companyID, String appID, Application application, ApprovalProcessParam approvalProcessParam, String memo,
			List<ListOfAppTypes> listOfAppTypes, boolean isFromCMM045) {
		ApproveProcessResult approveProcessResult = new ApproveProcessResult();
		//アルゴリズム「排他チェック」を実行する (thực hiện xử lý 「check version」)
        beforeRegisterRepo.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		
        List<Application> appLst = new ArrayList<>();
        appLst.add(application);
        if(application.getAppType()==ApplicationType.COMPLEMENT_LEAVE_APPLICATION && !isFromCMM045) {
        	Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(appID);
			if(appHdsubRec.isPresent()) {
				if(appHdsubRec.get().getRecAppID().equals(appID)) {
					applicationRepository.findByID(appHdsubRec.get().getAbsenceLeaveAppID()).ifPresent(x -> appLst.add(x));
				} else {
					applicationRepository.findByID(appHdsubRec.get().getRecAppID()).ifPresent(x -> appLst.add(x));
				}
			}
        }
        List<String> approverLst = new ArrayList<>();
        String applicant = "";
        for(Application appLoop : appLst) {
        	//8-2.詳細画面承認後の処理
            ProcessApprovalOutput processApprovalOutput = detailAfterApproval.doApproval(companyID, appLoop.getAppID(), appLoop, approvalProcessParam, memo);
            if(Strings.isNotBlank(processApprovalOutput.getAppID())) {
            	approveProcessResult.getAppIDLst().add(processApprovalOutput.getAppID());
            }
            if(Strings.isNotBlank(processApprovalOutput.getReflectAppId())) {
            	approveProcessResult.getReflectAppIdLst().add(processApprovalOutput.getReflectAppId());
            }
            if(!CollectionUtil.isEmpty(processApprovalOutput.getApproverLst())) {
            	approverLst.addAll(processApprovalOutput.getApproverLst());
            }
            if(Strings.isNotBlank(processApprovalOutput.getApplicant())) {
            	applicant = processApprovalOutput.getApplicant();
            }
        }
        approveProcessResult.setProcessDone(true);
        if(isFromCMM045) {
        	return approveProcessResult;
        }
        // IF文を参照
 		AppTypeSetting appTypeSetting = approvalProcessParam.getAppTypeSetting();
 		boolean condition = approvalProcessParam.isMailServerSet() && appTypeSetting.isSendMailWhenApproval();
 		if(condition) {
 			approveProcessResult.setAutoSendMail(true);
 			if(!CollectionUtil.isEmpty(approverLst)) {
 				// 承認者へ送る（新規登録、更新登録、承認）
 	 			MailResult approverLstResult = otherCommonAlgorithm.sendMailApproverApprove(approverLst, application);
 	 			approveProcessResult.getAutoSuccessMail().addAll(approverLstResult.getSuccessList());
	 			approveProcessResult.getAutoFailMail().addAll(approverLstResult.getFailList());
	 			approveProcessResult.getAutoFailServer().addAll(approverLstResult.getFailServerList());
 			}
 			if(Strings.isNotBlank(applicant)) {
				// 申請者へ送る（承認）
	 			MailResult applicantResult = otherCommonAlgorithm.sendMailApplicantApprove(application);
	 			approveProcessResult.getAutoSuccessMail().addAll(applicantResult.getSuccessList());
	 			approveProcessResult.getAutoFailMail().addAll(applicantResult.getFailList());
	 			approveProcessResult.getAutoFailServer().addAll(applicantResult.getFailServerList());
			}
 		}
 		return approveProcessResult;
	}

}
