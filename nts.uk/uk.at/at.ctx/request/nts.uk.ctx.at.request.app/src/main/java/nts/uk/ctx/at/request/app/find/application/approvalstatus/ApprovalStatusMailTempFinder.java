package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
public class ApprovalStatusMailTempFinder {

	@Inject
	private ApprovalStatusMailTempRepository finder;

	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;

	@Inject
	private MailSender mailsender;

	public ApprovalStatusMailTempDto findByType(int mailType) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// ドメインモデル「承認状況メールテンプレート」を取得する
		Optional<ApprovalStatusMailTemp> domain = finder.getApprovalStatusMailTempById(cid, mailType);
		return domain.isPresent() ? ApprovalStatusMailTempDto.fromDomain(domain.get()) : null;
	}

	/**
	 * アルゴリズム「承認状況本文起動」を実行する
	 * 
	 * @return
	 */
	public List<ApprovalStatusMailTempDto> findBySetting() {
		// 会社ID
		String cid = AppContexts.user().companyId();
		List<ApprovalStatusMailTempDto> listMail = new ArrayList<ApprovalStatusMailTempDto>();

		UseSetingDto transmissionAttr = this.getTransmissionAttr();

		listMail.add(this.getApprovalStatusMailTemp(cid, 0));
		if (transmissionAttr.isUsePersonConfirm()) {
			listMail.add(this.getApprovalStatusMailTemp(cid, 1));
		}
		if (transmissionAttr.isUseBossConfirm()) {
			listMail.add(this.getApprovalStatusMailTemp(cid, 2));
		}
		if (transmissionAttr.isMonthlyConfirm()) {
			listMail.add(this.getApprovalStatusMailTemp(cid, 3));
		}
		listMail.add(this.getApprovalStatusMailTemp(cid, 4));

		return listMail;
	}

	/**
	 * アルゴリズム「承認状況メール本文取得」を実行する
	 * 
	 * @param cid
	 *            会社ID
	 * @param mailType
	 *            メール種類
	 * @return ドメイン：承認状況メールテンプレート
	 */
	private ApprovalStatusMailTempDto getApprovalStatusMailTemp(String cid, int mailType) {
		Optional<ApprovalStatusMailTemp> domain = finder.getApprovalStatusMailTempById(cid, mailType);
		return domain.isPresent() ? ApprovalStatusMailTempDto.fromDomain(domain.get())
				: new ApprovalStatusMailTempDto(mailType, 1, 1, 1, "", "", 0);
	}

	/**
	 * アルゴリズム「承認状況社員メールアドレス取得」を実行する RequestList #126
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	public EmployeeEmailDto findEmpMailAddr() {
		String cId = AppContexts.user().employeeId();
		List<String> listCId = new ArrayList<String>();
		listCId.add(cId);
		Optional<EmployeeEmailImport> employee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(listCId).stream()
				.findFirst();
		return employee.isPresent() ? EmployeeEmailDto.fromImport(employee.get()) : null;
	}

	/**
	 * アルゴリズム「承認状況メールテスト送信実行」を実行する
	 * 
	 * @param mailType
	 *            対象メール
	 * @return 結果
	 */
	public boolean sendTestMail(int mailType) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// ドメインモデル「承認状況メールテンプレート」を取得する
		ApprovalStatusMailTemp domain = finder.getApprovalStatusMailTempById(cid, mailType).get();
		// 社員ID
		String sid = AppContexts.user().employeeId();
		// 社員名
		String sName = employeeRequestAdapter.getEmployeeName(sid);
		// メールアドレス
		String mailAddr = employeeRequestAdapter.empEmail(sid);
		// 件名
		String subject = domain.getMailSubject().v();
		// 送信本文
		String text = domain.getMailContent().v();

		// ログイン者よりメール送信内容を作成する(create nội dung send mail theo người login)
		List<MailTransmissionContentDto> listMailContent = new ArrayList<MailTransmissionContentDto>();
		listMailContent.add(new MailTransmissionContentDto(sid, sName, mailAddr, subject, text));
		UseSetingDto transmissionAttr = this.getTransmissionAttr();
		// アルゴリズム「承認状況メール送信実行」を実行する
		this.exeApprovalStatusMailTransmission(listMailContent, domain, transmissionAttr);
		return false;
	}

	/**
	 * アルゴリズム「承認状況メール送信実行」を実行する
	 * 
	 * @param listMailContent
	 *            メール送信内容＜社員ID、社員名、メールアドレス、件名、送信本文＞(リスト)
	 * @param domain
	 *            ドメイン：承認状況メールテンプレート
	 * @param transmissionAttr
	 *            送信区分（本人/日次/月次）
	 */
	private void exeApprovalStatusMailTransmission(List<MailTransmissionContentDto> listMailContent,
			ApprovalStatusMailTemp domain, UseSetingDto transmissionAttr) {
		for (MailTransmissionContentDto mailTransmission : listMailContent) {
			// アルゴリズム「承認状況メール埋込URL取得」を実行する

			EmbeddedUrlDto embeddedURL = this.getEmbeddedURL(mailTransmission.getSId(), domain, transmissionAttr);
			String s1 = embeddedURL.getURL();
			String s2 = embeddedURL.getURL2();
			try {
				mailsender.send("nts", mailTransmission.getMailAddr(),
						new MailContents(mailTransmission.getSubject(), mailTransmission.getText()));
			} catch (SendMailFailedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				String s = e.getMessage();
			}
		}
	}

	/**
	 * アルゴリズム「承認状況メール埋込URL取得」を実行する
	 * 
	 * @param eid
	 * @param domain
	 * @param transmissionAttr
	 */
	private EmbeddedUrlDto getEmbeddedURL(String eid, ApprovalStatusMailTemp domain,
			UseSetingDto transmissionAttr) {
		String url1 = "123123123";
		String url2 = "1231ád23123";
		return new EmbeddedUrlDto(url1, url2);
	}

	/**
	 * アルゴリズム「承認状況取得実績使用設定」を実行する
	 * 
	 * @return
	 */
	private UseSetingDto getTransmissionAttr() {
		// 月別確認を利用する ← 承認処理の利用設定.月の承認者確認を利用する
		boolean monthlyConfirm = false;
		// 上司確認を利用する ← 承認処理の利用設定.日の承認者確認を利用する
		boolean useBossConfirm = true;
		// 本人確認を利用する ← 本人確認処理の利用設定.日の本人確認を利用する
		boolean usePersonConfirm = true;
		// Waiting for Q&A
		return new UseSetingDto(monthlyConfirm, useBossConfirm, usePersonConfirm);
	}
}
