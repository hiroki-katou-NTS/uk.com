package nts.uk.ctx.at.schedule.dom.budget.performance.domainservice;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 外部予算日次を登録する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.予算管理.外部予算実績
 * 
 * @author HieuLt
 *
 */
public class RegisterExtBudgetDailyService {

	/**
	 * [1] 登録する
	 * @param require
	 * @param targetOrg
	 * @param itemCode
	 * @param ymd
	 * @param extBudgetActualValue
	 * @return AtomTask
	 */
	public static AtomTask signUp(Require require, TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode,
			GeneralDate ymd, Optional<ExtBudgetActualValues> extBudgetActualValue) {

		if (!extBudgetActualValue.isPresent()) {
			return AtomTask.of(() -> {
				require.delete(targetOrg, itemCode, ymd);
			});
		}
		// $外部予算実績 = 日次の外部予算実績(対象組織, 項目コード, 年月日, 値)
		ExtBudgetDaily data = new ExtBudgetDaily(targetOrg, itemCode, ymd, extBudgetActualValue.get());
		return AtomTask.of(() -> {
			// require.外部予算実績を削除する(対象組織, 項目コード, 年月日)
			require.delete(targetOrg, itemCode, ymd);
			// require.外部予算実績を追加する($外部予算実績)
			require.insert(data);
		});

	}

	public static interface Require {
		/**
		 * [R-1] 外部予算実績を追加する 日次の外部予算実績Repository.insert(日次の外部予算実績)
		 * ExtBudgetDailyRepository
		 * 
		 * @param extBudgetDaily
		 */
		void insert(ExtBudgetDaily extBudgetDaily);

		/**
		 * [R-2] 外部予算実績を削除する 日次の外部予算実績Repository.delete(対象組織識別情報, 外部予算実績項目コード,
		 * 年月日) ExtBudgetDailyRepository
		 * 
		 * @param extBudgetDaily
		 */
		void delete(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode, GeneralDate ymd);

	}

}
