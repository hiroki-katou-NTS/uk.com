package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 職場別の承認者を取得する
 * 36協定特別条項の適用申請の申請対象者から職場別の承認者を取得する
 *
 * @author khai.dh
 */
@Stateless
public class ByWorkplaceApproverGetDomainService {

	/**
	 * [1] 取得する
	 * システム日付時点の所属職場の職場別の承認者（36協定）を取得する。
	 * ない場合、上位職場の職場別の承認者（36協定）を取得する。
	 */
	public Optional<ApproverItem> getApprover(Require require, String empId) {

		val baseDate = GeneralDate.today();
		Optional<SWkpHistRcImported> wkpHist = require.getWkpHist(empId, baseDate);

		if (!wkpHist.isPresent()) {
			return Optional.empty();
		}
		val wkpApproverHist = require.getApproverHist(wkpHist.get().getWorkplaceId(), baseDate);

		if (wkpApproverHist.isPresent()) {
			return wkpApproverHist;
		}

		val topWkpList = require.getTopWorkplace(AppContexts.user().companyId(), wkpHist.get().getWorkplaceId(), baseDate);
		// TODO chưa xong

		return Optional.empty();
	}

	public static interface Require {
		/**
		 * [R-1] 所属職場を取得する Get workplace
		 * アルゴリズム.[RQ30]社員所属職場履歴を取得(社員ID,基準日)
		 */
		Optional<SWkpHistRcImported> getWkpHist(String empId, GeneralDate baseDate);

		/**
		 * [R-2] 承認者の履歴項目を取得する Get approver's history item
		 * 職場別の承認者（36協定）Repository.get(職場ID,基準日)
		 */
		Optional<ApproverItem> getApproverHist(String workplaceId, GeneralDate baseDate);

		/**
		 * [R-3] 上位職場を取得する Get Top Workplace
		 * アルゴリズム.[No.569]職場の上位職場を取得する(会社ID,職場ID,基準日)
		 */
		Optional<ApproverItem> getTopWorkplace(String cid, String workplaceId, GeneralDate baseDate);
	}
}
