package nts.uk.screen.com.app.find.cmm030.c;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmm030CScreenQuery {

	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository;
	
	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.C：新規の登録.メニュー別OCD.Ｃ：承認ルートの最終開始日を取得する
	 * @param sid 社員ID
	 * @return 開始日（Optional）
	 */
	public Optional<GeneralDate> getApprovalRootLastStartDate(String sid) {
		String cid = AppContexts.user().companyId();
		// 最新の履歴の開始日を取得する(ログイン会社ID、input.社員ID)
		return this.personApprovalRootRepository.getStartOfLastestHist(cid, sid);
	}
}
