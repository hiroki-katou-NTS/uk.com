package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.time.GeneralDate;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 承認者を取得する
 * @author khai.dh
 */
@Stateless
public class ApproverGetDomainService {

	/**
	 * [1] 取得する
	 * 36協定特別条項の適用申請の申請対象者から承認者を取得する
	 */
	public static Optional<ApproverItem> getApprover(Require require, String empId){
		Optional<ApproverItem> optWorkplaceApproverItem = ByWorkplaceApproverGetDomainService.getApprover(require, empId);

		if (optWorkplaceApproverItem.isPresent()) {
			return optWorkplaceApproverItem;
		}

		val baseDate = GeneralDate.today();
		val optCompanyApprover = require.getApproverHistoryItem(baseDate);
		if (optCompanyApprover.isPresent()) {
			return optCompanyApprover;
		}

		return Optional.empty();
	}

	public static interface Require extends ByWorkplaceApproverGetDomainService.Require {

		/**
		 * [R-1] 承認者の履歴項目を取得する Get the approver's history item
		 */
		Optional<ApproverItem> getApproverHistoryItem(GeneralDate baseDate);
	}
}
