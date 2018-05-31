package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectManager;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAfterApprovalImpl_New implements DetailAfterApproval_New {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;

	@Inject
	private MailSender mailsender;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private AppReflectManager appReflectManager;
	
	@Override
	public String doApproval(String companyID, String appID, String employeeID, String memo) {
		String strMail = "";
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		approvalRootStateAdapter.doApprove(
				companyID, 
				appID, 
				employeeID, 
				false, 
				application.getAppType().value, 
				application.getAppDate(), 
				memo);
		Boolean allApprovalFlg = approvalRootStateAdapter.isApproveAllComplete(
				companyID, 
				appID, 
				employeeID, 
				false, 
				application.getAppType().value, 
				application.getAppDate());
		applicationRepository.updateWithVersion(application);
		if(allApprovalFlg.equals(Boolean.TRUE)){
			// 実績反映状態 = 反映状態．反映待ち
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.WAITREFLECTION);
			applicationRepository.update(application);
			if((application.getPrePostAtr().equals(PrePostAtr.PREDICT)&&
					(application.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)
					|| application.getAppType().equals(ApplicationType.BREAK_TIME_APPLICATION)))
				|| application.getAppType().equals(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION)
				|| application.getAppType().equals(ApplicationType.WORK_CHANGE_APPLICATION)
				|| application.getAppType().equals(ApplicationType.ABSENCE_APPLICATION)
				|| application.getAppType().equals(ApplicationType.COMPLEMENT_LEAVE_APPLICATION)){
				appReflectManager.reflectEmployeeOfApp(application);
			}
		} else {
			// ドメインモデル「申請」と紐付き「反映情報」．実績反映状態 = 反映状態．未反映
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
			applicationRepository.update(application);
		}
		AppTypeDiscreteSetting discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value).get();
		// 承認処理時に自動でメールを送信するが trueの場合
		if (discreteSetting.getSendMailWhenApprovalFlg().equals(AppCanAtr.CAN)) {
			if(allApprovalFlg.equals(Boolean.TRUE)){
				String email = employeeAdapter.empEmail(application.getEmployeeID());
				if(Strings.isNotBlank(email)) {
					try {
						mailsender.send("nts", email, new MailContents("nts mail", "approval mail from NTS"));
					} catch (SendMailFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					strMail += email + System.lineSeparator();
				}
			}
		}
		
		if (discreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
			if(allApprovalFlg.equals(Boolean.FALSE)){
				List<String> destination = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
						companyID, 
						application.getAppID(), 
						1, 
						false, 
						employeeID, 
						application.getAppType().value, 
						application.getAppDate());
				if(!CollectionUtil.isEmpty(destination)){
					String email = employeeAdapter.empEmail(employeeID);
					if(Strings.isNotBlank(email)) {
						try {
							mailsender.send("nts", email, new MailContents("nts mail", "approval mail from NTS"));
						} catch (SendMailFailedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						strMail += email + System.lineSeparator();
					}
				}
			}
		}
		return strMail;
		
	}

}
