package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

/**
 * 
 * @author HungTT - <<Work>> 処理年月更新
 *
 */

@Stateless
public class ProcessYearMonthUpdate {

	@Inject
	private ClosureRepository closureRepository;

	/**
	 * 処理年月更新
	 * 
	 * @param companyId
	 * @param closureId
	 */
	public void processYmUpdate(String companyId, int closureId) {
		Optional<Closure> optClosure = closureRepository.findById(companyId, closureId);
		// check exist
		if (!optClosure.isPresent()) {
			return;
		}
		// update CurrentMonth
		Closure closure = optClosure.get();
		closure.updateCurrentMonth();
		closureRepository.update(closure);
	}

}