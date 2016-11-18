package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import javax.enterprise.context.RequestScoped;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.TaxAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.CalculationMethod;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDataCheckService;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PayrollSystem;
import nts.uk.shr.com.primitive.PersonId;

@RequestScoped
public class PaymentDataCheckServiceImpl implements PaymentDataCheckService {

	@Override
	public boolean isExists(String companyCode, String personId, PayBonusAtr payBonusAtr,
			int processingYearMonth) {
		// TODO Auto-generated method stub
		return false;
	}


}
