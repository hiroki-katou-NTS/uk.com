package nts.uk.ctx.at.request.pubimp.setting.applicationreason;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.request.pub.setting.applicationreason.ApplicationReasonExport;
import nts.uk.ctx.at.request.pub.setting.applicationreason.ApplicationReasonPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplicationReasonPubImpl implements ApplicationReasonPub {

//	@Inject
//	private ApplicationReasonRepository appReasonRepo;

	@Override
	public List<ApplicationReasonExport> getReasonByAppType(String companyId, List<Integer> lstAppType) {
//		return appReasonRepo.getReasonByAppType(companyId, lstAppType).stream()
//				.map(x -> new ApplicationReasonExport(companyId, x.getAppType().value, x.getReasonID(),
//						x.getDispOrder(), x.getReasonTemp().v(), x.getDefaultFlg().value))
//				.collect(Collectors.toList());
		return null;
	}

}
