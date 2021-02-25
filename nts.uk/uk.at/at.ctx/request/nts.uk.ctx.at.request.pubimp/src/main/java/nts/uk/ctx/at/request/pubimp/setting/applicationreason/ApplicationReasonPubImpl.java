package nts.uk.ctx.at.request.pubimp.setting.applicationreason;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.pub.setting.applicationreason.ApplicationReasonExport;
import nts.uk.ctx.at.request.pub.setting.applicationreason.ApplicationReasonPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplicationReasonPubImpl implements ApplicationReasonPub {

	@Inject
	private AppReasonStandardRepository appReasonRepo;

	@Override
	public List<ApplicationReasonExport> getReasonByAppType(String companyId, List<Integer> lstAppType) {
		List<AppReasonStandard> lstAppReason = appReasonRepo.findByListAppType(companyId, lstAppType);
		return lstAppReason.stream()
				.map(x -> new ApplicationReasonExport(companyId, x.getApplicationType().value,
						x.getReasonTypeItemLst().stream()
								.map(y -> Pair.of(y.getAppStandardReasonCD().v(), y.getReasonForFixedForm().v()))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
	}

}
