package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
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

		// 追加する履歴
		val histToAddClone = Approver36AgrByCompany.create(
				histToAdd.getCompanyId(),
				histToAdd.getPeriod(),
				new ArrayList<>(histToAdd.getApproverList()),
				new ArrayList<>(histToAdd.getConfirmerList())
		);

		// $最新の履歴
		val optLatestHist = require.getLatestHistory(GeneralDate.max());
		if (optLatestHist.isPresent()){
			optLatestHist.get().setPeriod(new DatePeriod(
					optLatestHist.get().getPeriod().start(),
					histToAddClone.getPeriod().start().addDays(-1)
			));
		}

		return AtomTask.of(() -> {
			require.addHistory(histToAddClone);

			if (optLatestHist.isPresent()){
				require.changeLatestHistory(optLatestHist.get(), optLatestHist.get().getPeriod().start());
			}
		});
	}

	public interface Require {

		/**
		 * [R-1] 最新の履歴を取得する Get latest history
 		 */
		Optional<Approver36AgrByCompany> getLatestHistory(GeneralDate baseDate);

		/**
		 * [R-2] 履歴を追加する Add history
		 */
		void addHistory(Approver36AgrByCompany hist);

		/**
		 * [R-3] 最新の履歴を変更する Change the latest history
		 */
		void changeLatestHistory(Approver36AgrByCompany hist, GeneralDate date);
	}
}
