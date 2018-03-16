package nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class ResvLeaGrantRemNumFinder{
	
	@Inject
	private RervLeaGrantRemDataRepository repository;

	public ResvLeaGrantRemNumDto getSingleData(PeregQuery query) {
		Optional<ReserveLeaveGrantRemainingData> dataOpt = repository.get(query.getEmployeeId());
		if (dataOpt.isPresent()) {
			return ResvLeaGrantRemNumDto.createFromDomain(dataOpt.get());
		}
		return null;
	}

}
