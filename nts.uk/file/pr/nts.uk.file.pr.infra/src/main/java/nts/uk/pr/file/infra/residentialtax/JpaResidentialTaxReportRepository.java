package nts.uk.pr.file.infra.residentialtax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.file.pr.app.export.residentialtax.ResidentialTaxReportRepository;
import nts.uk.file.pr.app.export.residentialtax.data.CompanyDto;
import nts.uk.file.pr.app.export.residentialtax.data.PaymentDetailDto;
import nts.uk.file.pr.app.export.residentialtax.data.PersonResitaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxSlipDto;
import nts.uk.file.pr.app.export.residentialtax.data.RetirementPaymentDto;

@Stateless
public class JpaResidentialTaxReportRepository extends JpaRepository implements ResidentialTaxReportRepository {
	private static String PERSON_RESITAX_SEL_1;

	static {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NEW ");
		query.append(ResidentialTaxDto.class.getName() + "()");
		query.append("FROM ");

		PERSON_RESITAX_SEL_1 = query.toString();
	}

	@Override
	public List<ResidentialTaxDto> findResidentTax(String companyCode, List<String> residentTaxCodeList) {
		List<ResidentialTaxDto> result = new ArrayList<>();
		result.add(new ResidentialTaxDto("residenceTaxCode", "resiTaxAutonomy", "registeredName", "companyAccountNo",
				"companySpecifiedNo", "cordinatePostalCode", "cordinatePostOffice"));

		return result;
	}

	@Override
	public CompanyDto findCompany(String companyCode) {
		CompanyDto result = new CompanyDto("companyCode1", "companyName1", "postal1", "address1_1", "address2_1");
		return result;
	}

	@Override
	public CompanyDto findRegalCompany(String companyCode, String regalDocCompanyCode) {
		CompanyDto result = new CompanyDto("companyCode2", "companyName2", "postal2", "address1_2", "address2_2");
		return result;
	}

	@Override
	public List<ResidentialTaxSlipDto> findResidentialTaxSlip(String companyCode, int yearMonth,
			List<String> residentTaxCodeList) {
		List<ResidentialTaxSlipDto> result = new ArrayList<>();
		result.add(new ResidentialTaxSlipDto(
				"resiTaxCode", 
				 199004, 
				99999, 
				99999, 
				99999, 
				99999, 
				"address", 
				GeneralDate.today(), 
				100, 
				9999, 
				9999, 
				9999));
		return result;
	}

	@Override
	public List<PaymentDetailDto> findPaymentDetail(String companyCode, List<String> personIdList, PayBonusAtr salary,
			int processingYearMonth, CategoryAtr deduction, String string) {
		List<PaymentDetailDto> result = new ArrayList<>();
		result.add(new PaymentDetailDto(
				"personId", 
				9999, 
				"itemCode", 
			    BigDecimal.valueOf(999999), 
				1111, 
				2222));
		return result;
	}

	@Override
	public List<RetirementPaymentDto> findRetirementPaymentList(String companyCode, List<String> personIdList,
			GeneralDate baseRangeStartYearMonth, GeneralDate baseRangeEndYearMonth) {
		List<RetirementPaymentDto> result = new ArrayList<>();
		result.add(new RetirementPaymentDto(
				"personId", 
				GeneralDate.today(), 
				9999, 
				9999, 
				9999,
				9999, 
				BigDecimal.valueOf(999999), 
				BigDecimal.valueOf(999999), 
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				9999, 
				9999, 
				BigDecimal.valueOf(999999),
                BigDecimal.valueOf(999999),
                BigDecimal.valueOf(999999),
                BigDecimal.valueOf(999999),
                BigDecimal.valueOf(999999),
                "withholdingMeno"));
		return result;
	}

	@Override
	public List<PersonResitaxDto> findPersonResidentTax(String companyCode, int yearMonth,
			List<String> residentTaxCodeList) {
		List<PersonResitaxDto> result = new ArrayList<>();
		result.add(new PersonResitaxDto(
				"personID", 
				"residenceCode", 
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999), 
				BigDecimal.valueOf(999999),
				BigDecimal.valueOf(999999)));
		return result;
	}
}
