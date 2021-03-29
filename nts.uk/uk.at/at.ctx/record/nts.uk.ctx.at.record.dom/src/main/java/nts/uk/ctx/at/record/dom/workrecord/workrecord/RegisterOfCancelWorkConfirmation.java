package nts.uk.ctx.at.record.dom.workrecord.workrecord;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * DS:就業確定を登録・解除を行う
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.実績の状況管理.管理職場の就業確定.就業確定を登録・解除を行う
 * 
 * @author chungnt
 *
 */

public class RegisterOfCancelWorkConfirmation {

	/**
	 * [1] 取得する
	 * 
	 * @param require
	 * @param companyId
	 *            会社ID
	 * @param workplaceId
	 *            職場ID
	 * @param closureId
	 *            締めID
	 * @param processYM
	 *            処理年月
	 * @param employeeId
	 *            確定者
	 * @param date
	 *            確定日時
	 * @param whetherToCancel
	 *            解除するか
	 * @return
	 */
	public static Optional<AtomTask> get(Require require, CompanyId companyId, WorkplaceId workplaceId, ClosureId closureId,
			YearMonth processYM, Optional<String> employeeId, Optional<GeneralDateTime> date, Boolean whetherToCancel) {

		if (!whetherToCancel) {
			EmploymentConfirmed employmentConfirmed = new EmploymentConfirmed(companyId, workplaceId, closureId,
					processYM, employeeId.get(), date.get());

			AtomTask atomTask = AtomTask.of(() -> {
				require.insert(employmentConfirmed);
			});
			
			return Optional.of(atomTask);
		}
		
		Optional<EmploymentConfirmed> optEmploymentConfirmed = require.get(companyId.v(), workplaceId.v(), closureId, processYM);
		
		if (!optEmploymentConfirmed.isPresent()) {
			
			return Optional.empty();
		}
		
		AtomTask atomTask = AtomTask.of(() -> {
			require.delete(optEmploymentConfirmed.get());
		});
		
		return Optional.of(atomTask);
	}

	public static interface Require {
		// [R-1] 就業確定を追加する
		void insert(EmploymentConfirmed domain);

		// [R-3] 就業確定を削除する
		void delete(EmploymentConfirmed domain);

		// [R-2] 就業確定を取得する
		Optional<EmploymentConfirmed> get(String companyId, String workplaceId, ClosureId closureId,
				YearMonth processYM);
	}

}
