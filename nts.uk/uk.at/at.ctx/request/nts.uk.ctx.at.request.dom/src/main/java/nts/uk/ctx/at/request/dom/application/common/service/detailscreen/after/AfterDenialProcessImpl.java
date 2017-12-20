package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

/**
 * 
 * @author ducpm
 *
 */
@Stateless
public class AfterDenialProcessImpl implements AfterDenialProcess {

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;

	@Inject
	private AgentAdapter approvalAgencyInformationService;
	
	@Inject 
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private ApproveAcceptedRepository approveAcceptedRepo;
	@Inject
	private MailSender mailSender;
	
	@Override
	public String detailedScreenAfterDenialProcess(Application application, String memo) {
		// 否認できるフラグ
		boolean canDeniedFlg = false;
		String email = "";
		String loginEmp = AppContexts.user().employeeId();
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「申請」．「承認フェーズ」5～1の順でループする
		application.setListPhase(application.getListPhase().stream().sorted(Comparator.comparingInt(AppApprovalPhase::getDispOrder).reversed()).collect(Collectors.toList()));
		List<AppApprovalPhase> listPhase = application.getListPhase();
		for (AppApprovalPhase phase : listPhase) {
			//アルゴリズム「承認者一覧を取得する」を実行する
			List<String> listApprover = new ArrayList<>();
			List<ApproveAccepted> listAllApproverPhase = new ArrayList<>();
			phase.getListFrame().stream().forEach(y -> {
				y.getListApproveAccepted().stream().forEach(z -> {
					listApprover.add(z.getApproverSID());
					listAllApproverPhase.add(z);
				});
			});
			if(CollectionUtil.isEmpty(listApprover)) {
				canDeniedFlg = false;
			}
			// ループ中の承認フェーズには承認を行ったか(Approval phase đang xử lý được xác nhận chưa)
			boolean isNotApproved = false;
			isNotApproved = listAllApproverPhase.stream().anyMatch(x -> x.getApprovalATR() == ApprovalAtr.UNAPPROVED);
			if(isNotApproved) {
				if(phase.getDispOrder() - 1 < 0) {
					continue;
				}
				//アルゴリズム「否認できるかチェックする」を実行する(thực hiện xử lý 「否認できるかチェックする」)
				canDeniedFlg = this.canDeniedCheck(application, phase.getDispOrder() - 1);
				if (!canDeniedFlg) {
					continue;
				}
			}
			
			//ドメインモデル「承認フェーズ」．「承認枠」1～5ループする(loop domain 「承認フェーズ」．「承認枠」1～5)
			List<ApprovalFrame> listFrame = phase.getListFrame();
			for (ApprovalFrame frame : listFrame) {
				List<String> approverIds = frame.getListApproveAccepted().stream().map(x -> x.getApproverSID())
						.collect(Collectors.toList());
				//ログイン者が承認者かチェックする(kiểm tra xem người xác nhận có phải người login hay không)
				if (approverIds.contains(loginEmp)) {
					//(ループ中の「承認枠」)承認区分=「否認」、承認者=ログイン者の社員ID、代行者=空
					for (ApproveAccepted appAccepted : frame.getListApproveAccepted()) {
						if(appAccepted.getApproverSID().equals(loginEmp)) {
							appAccepted.setApprovalATR(ApprovalAtr.DENIAL);
							appAccepted.setRepresenterSID("");
							continue;
						}
					}
				} else {
					//アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)
					AgentPubImport agency = this.approvalAgencyInformationService
							.getApprovalAgencyInformation(companyID, approverIds);
					//ログイン者社員が返す結果の承認代行者リストに存在するかチェックする(kiểm tra xem người login có trong danh sách người đại diện xác nhận hay không)
					if (agency.getListRepresenterSID().contains(loginEmp)) {
						// (ドメインモデル「承認枠」)承認区分=「否認」、承認者=空、代行者=ログイン者の社員ID
						//insert them 1 ban ghi vao bang KRQDT_APPROVE_ACCEPTED (ko co trong EAP)
						ApproveAccepted representer = ApproveAccepted.createFromJavaType(companyID,
								UUID.randomUUID().toString(),
								"", 
								ApprovalAtr.DENIAL.value, 
								ConfirmAtr.USEATR_USE.value, //can xem lai
								GeneralDate.today(),
								memo, 
								loginEmp);
						//frame.getListApproveAccepted().add(approveAccepted);
						approveAcceptedRepo.createApproverAccepted(representer, frame.getFrameID());
						//chuyen trang thai nhung nguoi trong frame thanh DENIAL
						frame.getListApproveAccepted().stream().forEach(x -> {
							x.setApprovalATR(ApprovalAtr.DENIAL);
							x.setRepresenterSID(loginEmp);
						});
					}
				}
			}
			
		}
		//「反映情報」．実績反映状態を「否認」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「否認」)
		application.setReflectPerState(ReflectPlanPerState.DENIAL);
		//ドメインモデル「申請」と紐付き「承認情報」「反映情報」をUpdateする(update domain 「申請」 và 「承認情報」「反映情報」 tương ứng)
		appRepo.fullUpdateApplication(application);
		//SEND mail
		// lấy domain 申請種類別設定
		ApplicationType appType = application.getApplicationType();
		// get DiscreteSetting
		Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID,
				appType.value);
		// get flag check auto send mail
		// 承認処理時に自動でメールを送信するが trueの場合
		AppCanAtr sendMailWhenApprovalFlg = discreteSetting.get().getSendMailWhenApprovalFlg();
		// check Continue
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			// 申請者本人にメール送信する ===>>>>Thuc hien gui mail cho nguoi viet don
			email = employeeAdapter.empEmail(loginEmp);
			try {
				if(!Strings.isBlank(email)) {
					mailSender.send("nts", email, new MailContents("nts mail", "deny mail from NTS"));
				}
			} catch (SendMailFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Hien thi Message
		// 送信先リストに項目がいる
		return email;
	}
	/**
	 * 否認できるかチェックする true：否認できる false：否認できない
	 */
	@Override
	public boolean canDeniedCheck(Application application, int startOrderNum) {
		List<AppApprovalPhase> listPhase = application.getListPhase();
		String loginEmp = AppContexts.user().employeeId();
		if (startOrderNum > 0) {
			// アルゴリズム「承認者一覧を取得する」を実行する
			for (AppApprovalPhase phase : listPhase) {
				//ループ開始順序(input)～１の順でループ
				if(phase.getDispOrder() > startOrderNum) {
					continue;
				}
				//アルゴリズム「承認者一覧を取得する」を実行する(thực hiện xử lý 「承認者一覧を取得する」)
				List<String> listRepresenter = new ArrayList<>();
				List<ApproveAccepted> listAllApproverPhase = new ArrayList<>();
				List<String> listApprover = new ArrayList<>();
				phase.getListFrame().stream().forEach(y -> {
					y.getListApproveAccepted().stream().forEach(z -> {
						listApprover.add(z.getApproverSID());
						listRepresenter.add(z.getRepresenterSID());
						listAllApproverPhase.add(z);
					});
				});
				if(CollectionUtil.isEmpty(listAllApproverPhase)) {
					continue;
				}
				// 「承認フェーズ」．承認区分をチェックする(check dữ liệu 「承認フェーズ」．承認区分)
				//「承認フェーズ」．承認区分が承認済(「承認フェーズ」．承認区分 = 承認済)
				if (phase.getApprovalATR() == ApprovalAtr.APPROVED) {
					//該当承認フェーズに確定者が設定したかチェックする(Approval phase đang xử lý có người 確定者 hay không)
					for(ApproveAccepted approverPhase: listAllApproverPhase) {
						if(approverPhase.getConfirmATR() == ConfirmAtr.USEATR_USE
								&& approverPhase.getApproverSID().equals(loginEmp)) {
							return false;
						}
					}
					//承認を行ったのはログイン者かチェックする(check xem người đã xác nhận có phải người login hay không)
					if(!CollectionUtil.isEmpty(listRepresenter)
							&& listRepresenter.contains(loginEmp)
							|| listApprover.contains(loginEmp)) {
						return false;
					}else {
						return true;
					}
					// 「承認フェーズ」．承認区分が承認済じゃない(「承認フェーズ」．承認区分 ≠ 承認済)
				} else {
					return false;
				}
			}
		}
		return true;
	}	

}
