package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 会社の承認者履歴を追加する
 * @author khai.dh
 */
@Stateless
public class CompanyApproverHistoryAddDomainService {

	/**
	 * [1] 追加する
	 * 会社別の承認者（36協定）の履歴を追加して、直前の履歴の終了日を変更する
	 */
	public static AtomTask addApproverHistory(Require require, Approver36AgrByCompany histToAdd){
		val histToAddClone = Approver36AgrByCompany.create(
				histToAdd.getCompanyId(),
				histToAdd.getPeriod(),
				histToAdd.getApproverList(),
				histToAdd.getConfirmerList()
		);

		return AtomTask.of(() -> {
			require.addHistory(histToAddClone);

			val optLatestHist = require.getLatestHistory(GeneralDate.max());
			if (optLatestHist.isPresent()){
				val latestHist = optLatestHist.get();
				val newEndDate = histToAddClone.getPeriod().start().addDays(-1);
				val periodWithNewEndDate = new DatePeriod(latestHist.getPeriod().start(), newEndDate);
				latestHist.setPeriod(periodWithNewEndDate);
				require.changeLatestHistory(latestHist,latestHist.getPeriod().start());
			}
		});
	}

	public static interface Require {
		/**
		 * [R-1] 最新の履歴を取得する Get latest history
		 *
 		 */
		Optional<Approver36AgrByCompany> getLatestHistory(GeneralDate baseDate);

		/**
		 * [R-2] 履歴を追加する Add history
		 */
		void addHistory(Approver36AgrByCompany hist);

		/**
		 * [R-3] 最新の履歴を変更する Change the latest history
		 */
		void changeLatestHistory(Approver36AgrByCompany hist,GeneralDate date);
	}
}
