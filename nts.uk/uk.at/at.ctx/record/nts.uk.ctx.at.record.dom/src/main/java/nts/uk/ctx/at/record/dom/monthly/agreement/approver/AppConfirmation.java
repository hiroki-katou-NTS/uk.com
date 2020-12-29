package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ConfirmationStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 申請を確認する
 * 36協定特別条項の適用申請を確認または否認をする
 *
 * @author khai.dh
 */
@Stateless
public class AppConfirmation {

	/**
	 * [1] 変更する
	 * 対象申請を確認または否認をする
	 * 
	 * @param require @Require
	 * @param applicantId 申請ID
	 * @param confirmerId 確認者 (社員ID)
	 * @param confirmStatus 確認状態
	 * @return AtomTask
	 */
	public static AtomTask change(Require require,
						   String applicantId,
						   String confirmerId,
						   ConfirmationStatus confirmStatus) {

		val optApp = require.getApp(applicantId); // R1
		if (!optApp.isPresent()){
			throw new BusinessException("Msg_1262");
		}

		val app = optApp.get();
		app.confirmApplication(confirmerId, confirmStatus);

		return AtomTask.of(() -> {
			require.updateApp(app); // R2
		});
	}

	public interface Require {
		/**
		 * [R-1] 申請を取得する
		 * 36協定特別条項の適用申請Repository.get(申請ID)
		 */
		Optional<SpecialProvisionsOfAgreement> getApp(String applicantId);

		/**
		 * [R-2] 申請を更新する
		 * 36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)
		 */
		void updateApp(SpecialProvisionsOfAgreement app);
	}
}
