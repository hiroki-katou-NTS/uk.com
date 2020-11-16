package nts.uk.ctx.at.record.ac.application.setting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.adapter.application.setting.ApplicationReasonRc;
import nts.uk.ctx.at.record.dom.adapter.application.setting.ApplicationReasonRcAdapter;
import nts.uk.ctx.at.request.pub.setting.applicationreason.ApplicationReasonPub;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplicationReasonRcAdapterImpl implements ApplicationReasonRcAdapter {

	@Inject
	private ApplicationReasonPub appReasonPub;

	@Override
	public List<ApplicationReasonRc> getReasonByAppType(String companyId, List<Integer> lstAppType) {
		return appReasonPub.getReasonByAppType(companyId, lstAppType).stream().map(x -> {
			return new ApplicationReasonRc(companyId, EnumAdaptor.valueOf(x.appType, ApplicationType.class),
					x.getReasonTemp());
		}).collect(Collectors.toList());
	}

}
