package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.ErAlApplicationAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErAlApplicationAdapterDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErAlApplicationPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErAlApplicationPubExport;
@Stateless
public class ErAlApplicationAcFinder implements ErAlApplicationAdapter {
	
	@Inject
	private ErAlApplicationPub erAlApplicationpub;

	@Override
	public Optional<ErAlApplicationAdapterDto> getAllErAlAppByEralCode(String companyID, String errorAlarmCode) {
		return erAlApplicationpub.getAllErAlAppByEralCode(companyID, errorAlarmCode).map(c->convertToImport(c));
	}
	
	private ErAlApplicationAdapterDto convertToImport(ErAlApplicationPubExport export) {
		return new ErAlApplicationAdapterDto(export.getCompanyID(),
				export.getErrorAlarmCode(),
				export.getAppType()
				);
	}

}
