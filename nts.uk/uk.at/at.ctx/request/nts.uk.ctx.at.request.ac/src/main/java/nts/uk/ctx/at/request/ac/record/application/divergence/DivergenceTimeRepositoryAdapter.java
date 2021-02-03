package nts.uk.ctx.at.request.ac.record.application.divergence;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.application.divergence.DivergenceTimeRepositoryPub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;

@Stateless
public class DivergenceTimeRepositoryAdapter implements DivergenceTimeRootRepository{
	
	@Inject
	private DivergenceTimeRepositoryPub pub;
	
	@Override
	public List<DivergenceTimeRoot> getList(String companyId, List<Integer> frames) {
		return pub.getAllDivTime(companyId, frames);
	}
}
