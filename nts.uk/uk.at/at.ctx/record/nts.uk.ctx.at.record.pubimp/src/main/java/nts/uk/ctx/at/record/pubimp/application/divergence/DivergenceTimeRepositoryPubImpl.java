package nts.uk.ctx.at.record.pubimp.application.divergence;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.pub.application.divergence.DivergenceTimeRepositoryPub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
/*
 * Refactor5
 * hoangnd
 * 
 */
@Stateless
public class DivergenceTimeRepositoryPubImpl implements DivergenceTimeRepositoryPub {
	
	@Inject
	private DivergenceTimeRepository repo;
	
	@Override
	public List<DivergenceTimeRoot> getAllDivTime(String companyId) {
		
		return repo.getAllDivTime(companyId);
	}

}
