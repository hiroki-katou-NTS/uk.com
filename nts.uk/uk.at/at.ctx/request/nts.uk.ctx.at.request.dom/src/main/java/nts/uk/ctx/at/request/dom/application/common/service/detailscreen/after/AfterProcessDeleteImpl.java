package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

/**
 * 
 * @author hieult
 *
 */
@Stateless
/** 5-2.詳細画面削除後の処理*/
public class AfterProcessDeleteImpl implements AfterProcessDelete {
	
	@Inject
	private  EmployeeRequestAdapter  employeeAdapter;
	
	@Inject
	private AppTypeDiscreteSettingRepository  appTypeDiscreteSettingRepo;
	
	@Inject
	private ApplicationRepository_New applicationRepo;
	
	@Inject
	private MailSender mailSender;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationApprovalService_New applicationApprovalService;
	
	@Override
	public String screenAfterDelete(String companyID,String appID, Long version) {
		ApplicationType appType = applicationRepo.findByID(companyID, appID).get().getAppType();
		AppCanAtr sendMailWhenApprovalFlg = appTypeDiscreteSettingRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value)
				.get().getSendMailWhenRegisterFlg();
		List<String> converList =new ArrayList<String>();
		String strMail = "";
		
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする(kiểm tra
		// 「申請種類別設定」．新規登録時に自動でメールを送信する)//
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			converList = approvalRootStateAdapter.getMailNotifierList(companyID, appID);
			if (!CollectionUtil.isEmpty(converList)) {
				// TODOgui mail cho ng xac nhan
				
				// TODO lay thong tin Imported
				
				for(String employeeId: converList){
					String mail = employeeAdapter.getEmployeeInfor(employeeId).getSMail();
					if(Strings.isBlank(mail)) {
						continue;
					}
					try {
						mailSender.send("nts", mail, new MailContents("nts mail", "delete mail from NTS"));
						strMail += mail + System.lineSeparator();
					} catch (SendMailFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		//TODO delete domaim Application
		applicationApprovalService.delete(companyID, appID, version, appType);
		//TODO hien thi thong tin Msg_16 
		/*if (converList != null) {
			//TODO Hien thi thong tin 392
		}*/
		return strMail;
	}

}
