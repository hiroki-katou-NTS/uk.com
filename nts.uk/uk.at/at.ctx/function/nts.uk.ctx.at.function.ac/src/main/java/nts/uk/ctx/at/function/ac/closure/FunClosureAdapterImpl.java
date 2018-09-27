package nts.uk.ctx.at.function.ac.closure;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.closure.FunClosureAdapter;
import nts.uk.ctx.at.function.dom.adapter.closure.PresentClosingPeriodFunImport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
@Stateless
public class FunClosureAdapterImpl implements FunClosureAdapter {

	@Inject
	private ShClosurePub shClosurePub;
	
	@Override
	public Optional<PresentClosingPeriodFunImport> getClosureById(String cId, int closureId) {
		Optional<PresentClosingPeriodFunImport> closure = shClosurePub.find(cId, closureId)
				.map(c-> new PresentClosingPeriodFunImport(c.getProcessingYm(),
						c.getClosureStartDate(),
						c.getClosureEndDate()));
		return closure;
	}

}
