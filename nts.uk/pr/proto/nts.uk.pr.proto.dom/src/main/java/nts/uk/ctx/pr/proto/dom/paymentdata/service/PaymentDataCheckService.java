package nts.uk.ctx.pr.proto.dom.paymentdata.service;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.TaxAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.CalculationMethod;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.personalinformation.employmentcontract.PayrollSystem;
import nts.uk.shr.com.primitive.PersonId;

public interface PaymentDataCheckService {
	
	/**
	 * Check exists payment data of person.
	 * @param companyCode company code
	 * @param personId person id
	 * @param payBonusAtr payroll bonus attribute
	 * @param processingYearMonth processing year month
	 * @return true if exists payment data of person else false
	 */
	boolean isExists(CompanyCode companyCode, PersonId personId, PayBonusAtr payBonusAtr, YearMonth processingYearMonth);
	
	boolean isErrorSystem(PayrollSystem payrollSystem, CalculationMethod calculationMethod, TaxAtr taxAttribute, CommuteAtr commuteAttribute);
}
