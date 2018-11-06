package nts.uk.ctx.pr.core.pubimp.wageprovision.processdatecls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.CurrProcessYmExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.CurrProcessYmPub;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class CurrProcessYmPubImpl implements CurrProcessYmPub {

	@Inject
	private CurrProcessDateRepository currYmRepo;

	@Override
	public Optional<CurrProcessYmExport> getCurrentSalaryProcessYm(String companyId, int processCateNo) {
		Optional<CurrProcessDate> optCurrYm = currYmRepo.getCurrProcessDateByKey(companyId, processCateNo);
		if (optCurrYm.isPresent())
			return Optional.of(CurrProcessYmExport.fromDomain(optCurrYm.get()));
		else
			return Optional.empty();
	}

	@Override
	public List<CurrProcessYmExport> getCurrentSalaryProcessYm(String companyId, List<Integer> processCateNo) {
		List<CurrProcessYmExport> result = new ArrayList<>();
		for (Integer i : processCateNo) {
			Optional<CurrProcessDate> optCurrYm = currYmRepo.getCurrProcessDateByKey(companyId, i);
			if (optCurrYm.isPresent())
				result.add(CurrProcessYmExport.fromDomain(optCurrYm.get()));
		}
		return result;
	}

}
