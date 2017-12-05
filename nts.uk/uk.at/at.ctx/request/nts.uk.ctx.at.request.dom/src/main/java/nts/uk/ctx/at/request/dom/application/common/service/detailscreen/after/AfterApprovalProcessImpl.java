package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.DestinationMailListOuput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcess;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
public class AfterApprovalProcessImpl implements AfterApprovalProcess {
	@Inject
	private ApplicationRepository appRepo;
	@Inject
	private ApprovalFrameRepository frameRepo;
	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;
	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;
	@Inject
	private RegisterAtApproveReflectionInfoService reflectionInfoService;
	@Inject
	private AgentAdapter approvalAgencyInformationService;
	@Inject
	private DestinationJudgmentProcess destinationJudgmentProcessService;
	
	@Inject
	private ApproveAcceptedRepository approveAcceptedRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private MailSender mailsender;
	
	@Override
	public String detailScreenAfterApprovalProcess(Application application, String approverMemo) {
		String companyID = AppContexts.user().companyId();
		List<String> listMailReceived = new ArrayList<>();
		String strMail = "";
		//アルゴリズム「承認情報の整理」を実行する(thực hiện xứ lý 「承認情報の整理」)		
		application = reflectionInfoService.organizationOfApprovalInfo(application, approverMemo);
		//共通アルゴリズム「実績反映状態の判断」を実行する
		this.judgmentActualReflection(application);
		//ドメインモデル「申請」と紐付き「承認情報」「反映情報」をUpdateする
		appRepo.updateApplication(application);
		// get domain 申請種類別設定
		Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID, application.getApplicationType().value);
		// 承認処理時に自動でメールを送信するが trueの場合
		if(discreteSetting.isPresent()) {
			if (discreteSetting.get().getSendMailWhenApprovalFlg() == AppCanAtr.CAN) {
				//反映情報」．実績反映状態をチェックする
				if (application.getReflectPerState() == ReflectPlanPerState.WAITREFLECTION) {
					// 申請者本人にメール送信する =>>> SEND MAIL
				}
			}
			// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
			if (discreteSetting.get().getSendMailWhenRegisterFlg() == AppCanAtr.CAN) {
				// 「反映情報」．実績反映状態が「反映待ち」じゃない場合
				if (application.getReflectPerState() != ReflectPlanPerState.WAITREFLECTION) {
					// 申請者本人にメール送信する 
					listMailReceived = this.MailDestination(application).getDestinationMail();
					if(!listMailReceived.isEmpty()) {
						for(String mail: listMailReceived) {
							try {
								if(!Strings.isBlank(mail)) {
									mailsender.send("nts", mail, new MailContents("nts mail", "approval mail from NTS"));
									strMail += mail + System.lineSeparator();
								}
							} catch (SendMailFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}						
					}
				}
			}
		}
		
		//情報メッセージ（)
		//TODO: TRA VE MOT LIST GUI MAIL
		return strMail;
	}
	/**
	 * 1.申請個別のエラーチェック
	 */
	@Override
	public void invidialApplicationErrorCheck(String appID) {
		// TODO Auto-generated method stub
		// EA chưa viết xong
	}
	/**
	 * 2.申請個別の更新
	 */
	@Override
	public void invidialApplicationUpdate(Application application) {
		appRepo.updateApplication(application);
	}
	/**
	 * 
	 */
	@Override
	public List<String> actualReflectionStateDecision(String appID, String phaseID, ApprovalAtr approvalAtr) {
		// 承認者一覧
		List<String> lstApproved = new ArrayList<>();
		List<String> lstNotApproved = new ArrayList<>();
		List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(), phaseID);
		for(ApprovalFrame approvalFrame : listFrame ) {
			List<ApproveAccepted> listApproveAccepted = approveAcceptedRepository.getAllApproverAccepted(approvalFrame.getCompanyID(), approvalFrame.getFrameID());
			approvalFrame.setListApproveAccepted(listApproveAccepted);
		}
		for (ApprovalFrame frame : listFrame) {
			if(frame.getListApproveAccepted() !=null) {
				for(ApproveAccepted x : frame.getListApproveAccepted()){
					if (x.getApprovalATR() == ApprovalAtr.APPROVED) {
						lstApproved.add(x.getApproverSID());
					};
					if (x.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
						lstNotApproved.add(x.getApproverSID());
					};	
				}
			}
		}
		// Get distinct List Approver
		lstApproved.stream().distinct().collect(Collectors.toList());
		lstNotApproved.stream().distinct().collect(Collectors.toList());
		if (approvalAtr == ApprovalAtr.APPROVED) {
			return lstApproved;
		} else {
			return lstNotApproved;
		}
	}
	/**
	 * 
	 */
	@Override
	public DestinationMailListOuput MailDestination(Application application) {
		DestinationMailListOuput output = new DestinationMailListOuput();
		String appID = application.getApplicationID();
		List<String> listMailReceived = new ArrayList<String>();
		List<Integer> listPhaseFrame = new ArrayList<Integer>();
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(AppContexts.user().companyId(), appID);
		for (AppApprovalPhase phase : listPhase) {
			List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(), phase.getPhaseID());
			for (ApprovalFrame frame : listFrame) {
				if (frame.getDispOrder() >= 1 && frame.getDispOrder() <= 5) {
					//アルゴリズム「承認者一覧を取得する」を実行する
					List<String> listApprover = this.actualReflectionStateDecision(appID, phase.getPhaseID(), ApprovalAtr.APPROVED);
					if(!listApprover.isEmpty()) {
						//未承認の承認者一覧(output)に承認者がいるかチェックする
						List<String> listNotApprover = this.actualReflectionStateDecision(appID, phase.getPhaseID(), ApprovalAtr.UNAPPROVED);
						if(!listNotApprover.isEmpty()) {
							//アルゴリズム「承認代行情報の取得処理」を実行する
							AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(AppContexts.user().companyId(), listNotApprover);
							//アルゴリズム「送信先の判断処理」を実行する
							List<String> listDestination  = destinationJudgmentProcessService.getDestinationJudgmentProcessService(agency.getListApproverAndRepresenterSID());
							if(!listDestination.isEmpty()) {
								//送信先リスト(output)を送信者リストに追加する
								listMailReceived.addAll(listDestination);
							}
						}
					}
				}
			}
		}
		output.setDestinationMail(listMailReceived);
		//output.phaseFrameNumber = 1;
		return output;
	}
	/**
	 * 3.実績反映状態の判断 
	 * @param application
	 * @return
	 */
	@Override
	public void  judgmentActualReflection(Application application) {
		String companyID = AppContexts.user().companyId();
		String appID = application.getApplicationID();
		if(application.getListPhase() != null) {
			for (AppApprovalPhase phase : application.getListPhase()) {
				// 承認フェーズ」．承認区分が承認済以外の場合(「承認フェーズ」．承認区分 ≠ 承認済
				if (phase.getApprovalATR() != ApprovalAtr.APPROVED) {
					List<String> lstApprover = this.actualReflectionStateDecision(appID, phase.getPhaseID(),ApprovalAtr.APPROVED);
					if(!lstApprover.isEmpty()) {
						// 承認者の代行情報リスト
						AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, lstApprover);
						//返す結果の全承認者パス設定フラグがtrue(全承認者パス設定フラグ = true)
						if(!agency.isFlag()) {
							return;
						}
					}
				//「承認フェーズ」．承認区分が承認済の場合(「承認フェーズ」．承認区分 = 承認済)
				} else {
					// 「反映情報」．実績反映状態を「反映待ち」にする
					application.changeReflectState(ReflectPlanPerState.WAITREFLECTION.value);
					return;
				}
				
			}
		}
		
		
	}
	@Override
	public List<String> getListApprover(String companyID, String appID) {
		List<String> listApprover = new ArrayList<String>();
		Optional<Application> opApplication = applicationRepository.getAppById(companyID, appID);
		if(!opApplication.isPresent()){
			return listApprover;
		}
		Application application = opApplication.get();
		application.getListPhase().stream().forEach(phase ->{
			phase.getListFrame().stream().forEach(frame -> {
				frame.getListApproveAccepted().stream().forEach(accepted -> {
					if(Strings.isNotBlank(accepted.getRepresenterSID())){
						listApprover.add(accepted.getRepresenterSID());
					} else {
						listApprover.add(accepted.getApproverSID());
					}
				});
			});
		});
		return listApprover;
	}

}
