package nts.uk.ctx.at.request.ac.record.agreement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDExport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDImport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementPeriodByYMDPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreePeriodYMDExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementPeriodByYMDAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AgreementPeriodYMDImpl implements AgreementPeriodByYMDAdapter {

	@Inject
	private AgreementPeriodByYMDPub agreementPeriodByYMDPub;
	
	@Override
	public AgreePeriodYMDExport getAgreementPeriod(String companyID, GeneralDate ymd, ClosureId closureID) {
		AgreementPeriodByYMDExport agreementPeriodByYMDExport = agreementPeriodByYMDPub.getAgreementPeriod(new AgreementPeriodByYMDImport(companyID, ymd, closureID));
		return new AgreePeriodYMDExport(agreementPeriodByYMDExport.getDateperiod(), agreementPeriodByYMDExport.getDateTime());
	}

}
