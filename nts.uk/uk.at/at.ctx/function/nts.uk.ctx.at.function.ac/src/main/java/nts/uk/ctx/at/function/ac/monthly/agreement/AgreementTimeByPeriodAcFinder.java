package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodImport;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.PeriodAtrOfAgreement;

/**
 * @author dat.lh
 *
 */
@Stateless
public class AgreementTimeByPeriodAcFinder implements AgreementTimeByPeriodAdapter {
	@Inject
	private AgreementTimeByPeriodPub agreementTimeByPeriodPub;

	@Override
	public List<AgreementTimeByPeriodImport> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr) {
		return agreementTimeByPeriodPub.algorithm(companyId, employeeId, criteria, startMonth, year, periodAtr).stream()
				.map(x -> new AgreementTimeByPeriodImport(x.getStartMonth(), x.getEndMonth(), x.getAgreementTime(),
						x.getLimitAlarmTime().toString(), x.getLimitAlarmTime().toString(),
						x.getExceptionLimitErrorTime().toString(), x.getExceptionLimitAlarmTime().toString(),
						x.getStatus()))
				.collect(Collectors.toList());
	}

}
