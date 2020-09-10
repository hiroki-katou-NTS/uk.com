package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 承認者を取得する
 * @author khai.dh
 */
@Stateless
public class ApproverGetDomainService {

	@Inject
	private ByWorkplaceApproverGetDomainService wkpApprService;

	@Inject
	private WkpApprRequire wkpApprRequire; // TODO

	/**
	 * [1] 取得する
	 * 36協定特別条項の適用申請の申請対象者から承認者を取得する
	 */
	public Optional<ApproverItem> getApprover(Require require, String empId){

		Optional<ApproverItem> optWorkplaceApproverItem = require.getWorkplaceApprover(empId);
		//Optional<ApproverItem> optWorkplaceApproverItem = wkpApprService.getApprover(wkpApprRequire, empId); // TODO ?

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

	public static interface Require {
		/**
		 * R-0
		 */
		Optional<ApproverItem> getWorkplaceApprover(String empId);


		/**
		 * [R-1] 承認者の履歴項目を取得する Get the approver's history item
		 */
		Optional<ApproverItem> getApproverHistoryItem(GeneralDate baseDate);
	}

	// TODO
	public static class WkpApprRequire implements ByWorkplaceApproverGetDomainService.Require {

		@Override
		public Optional<SWkpHistRcImported> getWkpHist(String empId, GeneralDate baseDate) {
			return Optional.empty();
		}

		@Override
		public Optional<ApproverItem> getApproverHist(String workplaceId, GeneralDate baseDate) {
			return Optional.empty();
		}

		@Override
		public Optional<ApproverItem> getTopWorkplace(String cid, String workplaceId, GeneralDate baseDate) {
			return Optional.empty();
		}
	}
}
