package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * 
 * @author HungTT - <<Work>> 処理年月更新
 *
 */
public class ProcessYearMonthUpdate {

	/**
	 * 処理年月更新
	 * 
	 * @param companyId
	 * @param closureId
	 */
	public static AtomTask processYmUpdate(RequireM1 require, String companyId, int closureId) {
		Optional<Closure> optClosure = require.closure(companyId, closureId);
		// check exist
		if (!optClosure.isPresent()) {
			return AtomTask.of(() -> {});
		}
		// update CurrentMonth
		Closure closure = optClosure.get();
		closure.updateCurrentMonth();
		
		return AtomTask.of(() -> require.updateClosure(closure));
	}
	
	public static interface RequireM1 {
		
		Optional<Closure> closure(String companyId, int closureId);
		
		void updateClosure(Closure closure);
	}

}