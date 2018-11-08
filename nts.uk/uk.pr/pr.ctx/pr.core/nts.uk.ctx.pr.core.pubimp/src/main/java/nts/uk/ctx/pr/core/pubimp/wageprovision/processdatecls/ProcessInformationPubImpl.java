package nts.uk.ctx.pr.core.pubimp.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessInformationExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessInformationPub;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class ProcessInformationPubImpl implements ProcessInformationPub {

	@Inject
	private ProcessInformationRepository processInforRepo;

	@Override
	public List<ProcessInformationExport> getProcessInformationByDeprecatedCategory(String companyId,
			int deprecateAtr) {
		return processInforRepo.getProcessInformationByDeprecatedCategory(companyId, deprecateAtr).stream()
				.map(p -> ProcessInformationExport.fromDomain(p)).collect(Collectors.toList());
	}

}
