package nts.uk.ctx.at.request.ac.record.application.divergence;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.application.divergence.DivergenceTimeRepositoryPub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceTimeRepositoryAdapter implements DivergenceTimeRootRepository{
	
	@Inject
	private DivergenceTimeRepositoryPub pub;
	
	@Override
	public List<DivergenceTimeRoot> getList(List<Integer> frames) {
		return this.getAllDivTime(AppContexts.user().companyId())
					.stream()
					.filter(x -> frames.contains(x.getDivergenceTimeNo()))
					.collect(Collectors.toList());
	}

	@Override
	public List<DivergenceTimeRoot> getAllDivTime(String companyId) {
		return pub.getAllDivTime(companyId);
	}

}
