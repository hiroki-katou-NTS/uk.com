package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.mail.CheckTransmission;
import nts.uk.ctx.at.request.dom.mail.SendMailAppInfoParam;
import nts.uk.ctx.at.request.dom.mail.SendMailApproverInfoParam;
import nts.uk.ctx.at.request.dom.mail.SendMailParam;

@Stateless
public class SendMailCommandHandler extends CommandHandlerWithResult<SendMailCommand, MailSenderResult>{
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通ダイアログ.KDL030：申請メール送信ダイアログ.アルゴリズム.メール送信.メール送信
	 */
	@Inject
	private CheckTransmission checkTranmission;
	protected MailSenderResult handle(CommandHandlerContext<SendMailCommand> context)  {
		SendMailParam sendMailParam = context.getCommand().toDomain();
		// INPUT.「メール送信情報パラメータ.申請者ごとメール先リスト」をチェックする
		if(CollectionUtil.isEmpty(sendMailParam.getAppInfoLst())) {
			throw new BusinessException("Msg_14");
		}
		// 承認者リストをチェックする
		List<SendMailApproverInfoParam> approverInfoLst = sendMailParam.getAppInfoLst().stream().map(x -> x.getApproverInfoLst())
				.flatMap(Collection::stream).collect(Collectors.toList());
		if(CollectionUtil.isEmpty(approverInfoLst)) {
			// 申請者にメールを送信するかをチェックする
			if(!sendMailParam.getOpSendMailApplicant().orElse(false)) {
				throw new BusinessException("Msg_14");
			}
			// 申請者のメールアドレスをチェックする
			List<SendMailAppInfoParam> listNotExistApplicantMail = sendMailParam.getAppInfoLst().stream().filter(x -> !x.getOpApplicantMail().isPresent()).collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(listNotExistApplicantMail)) {
				// エラーメッセージを表示する（Msg_1309）
				throw new BusinessException("Msg_1309");
			}
		}
		
		if(sendMailParam.getOpSendMailApplicant().orElse(false)) {
			// 申請者のメールアドレスをチェックする
			List<SendMailAppInfoParam> listNotExistApplicantMail = sendMailParam.getAppInfoLst().stream().filter(x -> !x.getOpApplicantMail().isPresent()).collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(listNotExistApplicantMail)) {
				// エラーメッセージを表示する（Msg_1309）
				throw new BusinessException("Msg_1309");
			}
		}
		
		List<String> successList = new ArrayList<>();
		for(SendMailAppInfoParam sendMailAppInfoParam : sendMailParam.getAppInfoLst()) {
			// 「メール送信する」の承認者全員に対してメールを送信する
			// アルゴリズム「送信・送信後チェック」を実行する
			successList.addAll(checkTranmission.doCheckTranmission(
					sendMailAppInfoParam, 
					sendMailParam.getOpSendMailApplicant().orElse(false), 
					sendMailParam.getMailTemplate(),
					sendMailParam.getAppEmailSet())
					.getSuccessList());
		}

		return new MailSenderResult(successList, new ArrayList<>());
	}
}
