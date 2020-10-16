package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 承認者を取得する
 *
 * @author khai.dh
 */
@Stateless
public class GettingApproverDomainService {

	/**
	 * [1] 取得する
	 * 36協定特別条項の適用申請の申請対象者から承認者を取得する
	 */
	public static Optional<ApproverItem> getApprover(Require require, String empId) {

		// $利用設定
		val usageSetting = require.getUsageSetting();

		// if $利用設定.職場を利用する = する
		if (usageSetting != null && usageSetting.getUseWorkplace() == DoWork.USE) {
			Optional<ApproverItem> optWorkplaceApproverItem
					= GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, empId);

			if (optWorkplaceApproverItem.isPresent()) {
				return optWorkplaceApproverItem;
			}
		}

		val baseDate = GeneralDate.today();
		val optCompanyApprover = require.getApproverHistoryItem(baseDate);
		if (optCompanyApprover.isPresent()) {
			return Optional.of(new ApproverItem(
					optCompanyApprover.get().getApproverList(),
					optCompanyApprover.get().getConfirmerList()
			));
		}

		return Optional.empty();
	}

	public interface Require extends GetWorkplaceApproveHistoryDomainService.Require {

		/**
		 * [R-1] 承認者の履歴項目を取得する Get the approver's history item
		 * 会社別の承認者（36協定）Repository.get(会社ID,基準日)
		 */
		Optional<Approver36AgrByCompany> getApproverHistoryItem(GeneralDate baseDate);

		/**
		 * [R-2] 利用設定を取得する - Get usage setting
		 * 承認者（36協定）の利用単位Repository.get(会社ID)
		 */
		UnitOfApprover getUsageSetting();
	}
}
