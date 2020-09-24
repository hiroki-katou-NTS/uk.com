package nts.uk.ctx.at.request.dom.application.algorithm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.SendMailAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationAlgorithmImpl implements ApplicationAlgorithm {
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Override
	public SendMailAtr checkAutoSendMailRegister(String appID, AppTypeSetting appTypeSetting, boolean mailServerSet) {
		// INPUT．メールサーバ設定済区分をチェックする(check INPUT．メールサーバ設定済区分 ) 
		if(!mailServerSet) {
			// 「メール送信区分」　＝＝「送信しない」
			return SendMailAtr.NOT_SEND;
		}
		// アルゴリズム「申請IDを使用して申請一覧を取得する」を実行する
		Optional<Application> opApplication = applicationRepository.findByID(appID);
		if(!opApplication.isPresent()) {
			// 「メール送信区分」　＝＝「送信しない」
			return SendMailAtr.NOT_SEND;
		}
		// Input：ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
		if(!appTypeSetting.isSendMailWhenRegister()) {
			// 「メール送信区分」　＝＝「送信しない」
			return SendMailAtr.NOT_SEND;
		}
		// アルゴリズム「次の承認の番の承認者を取得する(メール通知用)」を実行する
		List<String> destinationLst = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(appID, 5);
		// 送信先リストに項目がいるかチェックする ( Check if there is an item in the destination list )
		if(CollectionUtil.isEmpty(destinationLst)) {
			// 「メール送信区分」　＝＝「送信しない」
			return SendMailAtr.NOT_SEND;
		}
		// 「メール送信区分」　＝＝　送信する
		return SendMailAtr.SEND;
	}

}
