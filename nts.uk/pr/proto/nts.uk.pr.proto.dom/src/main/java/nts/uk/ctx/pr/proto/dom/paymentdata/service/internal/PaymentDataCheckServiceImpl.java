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
	public boolean isExists(CompanyCode companyCode, PersonId personId, PayBonusAtr payBonusAtr,
			YearMonth processingYearMonth) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrorSystem(PayrollSystem payrollSystem, CalculationMethod calculationMethod, TaxAtr taxAttribute, CommuteAtr commuteAttribute) {
		if (payrollSystem == null) {
			return false;
		}
		
		if (calculationMethod == null) {
			return false;
		}
		
		if (taxAttribute == null) {
			return false;
		}
		
		if (commuteAttribute == null) {
			return false;
		}
		
		return true;
	}

}
