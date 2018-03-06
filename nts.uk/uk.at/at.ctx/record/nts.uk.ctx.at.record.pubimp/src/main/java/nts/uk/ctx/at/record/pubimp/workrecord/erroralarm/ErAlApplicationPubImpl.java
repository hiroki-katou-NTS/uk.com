package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplication;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplicationRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErAlApplicationPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErAlApplicationPubExport;

@Stateless
public class ErAlApplicationPubImpl implements ErAlApplicationPub {

	@Inject
	private ErAlApplicationRepository repo;
	
	@Override
	public Optional<ErAlApplicationPubExport> getAllErAlAppByEralCode(String companyID, String errorAlarmCode) {
		Optional<ErAlApplicationPubExport> data = repo.getAllErAlAppByEralCode(companyID, errorAlarmCode).map(c->toDto(c));
		return data;
	}
	
	private ErAlApplicationPubExport toDto(ErAlApplication domain) {
		return new ErAlApplicationPubExport(domain.getCompanyID(),
				domain.getErrorAlarmCode(),
				domain.getAppType());
	}

}
