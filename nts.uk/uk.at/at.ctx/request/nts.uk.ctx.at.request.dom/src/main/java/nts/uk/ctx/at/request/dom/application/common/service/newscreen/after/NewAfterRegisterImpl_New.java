package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
public class NewAfterRegisterImpl_New implements NewAfterRegister_New {
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private MailSender mailSender;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	public String processAfterRegister(Application_New application){
		
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(application.getCompanyID(), application.getAppType().value);
		if(!appTypeDiscreteSettingOp.isPresent()) {
			throw new RuntimeException("Not found AppTypeDiscreteSetting in table KRQST_APP_TYPE_DISCRETE, appType =" + application.getAppType().value);
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)) {
			return null;
		}
		// アルゴリズム「送信先リストの取得」を実行する
		List<String> destinationList = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
				application.getCompanyID(), 
				application.getAppID(), 
				1, 
				true, 
				application.getEmployeeID(), 
				application.getAppType().value, 
				application.getAppDate());
		
		// 送信先リストに項目がいるかチェックする 
		if(destinationList.size() < 1) return null;
		String mails = "";
		for(String destination : destinationList) {
			//Imported(就業)「社員」
			String email = employeeAdapter.empEmail(destination);
			try {
				if(!Strings.isBlank(email)) {
					mailSender.send("nts", email, new MailContents("nts mail", "new mail from NTS"));
					mails += email + System.lineSeparator();
				}
				
			} catch (SendMailFailedException e) {
				e.printStackTrace();
			}
		}
		return mails;
	}
}
