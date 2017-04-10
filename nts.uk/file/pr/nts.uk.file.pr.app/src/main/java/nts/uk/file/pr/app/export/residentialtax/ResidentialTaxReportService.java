package nts.uk.file.pr.app.export.residentialtax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.file.pr.app.export.residentialtax.data.CompanyDto;
import nts.uk.file.pr.app.export.residentialtax.data.PaymentDetailDto;
import nts.uk.file.pr.app.export.residentialtax.data.PersonResitaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentTaxReportData;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxSlipDto;
import nts.uk.file.pr.app.export.residentialtax.data.RetirementPaymentDto;

@Stateless
public class ResidentialTaxReportService extends ExportService<ResidentialTaxQuery> {
	@Inject
	private ResidentialTaxGenerator generate;
	@Inject
	private ResidentialTaxReportRepository residentialTaxRepo;

	@Override
	protected void handle(ExportServiceContext<ResidentialTaxQuery> context) {
		// company code
		String companyCode = "";
		String dbd014 = "";
		String dbd007 = "";
		String dbd006 = "";
		
		// get query
		ResidentialTaxQuery query = context.getQuery();
		
		// get residential tax
		List<ResidentialTaxDto> residentTaxList = residentialTaxRepo.findResidentTax(companyCode, query.getResidentTaxCodeList());
		
		CompanyDto company = residentialTaxRepo.findCompany(companyCode);
		if (query.isCompanyLogin()) {
			// get company SEL_3
			//CompanyDto company = residentialTaxRepo.findCompany(companyCode);
		} else {
			// get QCPMT_REGAL_DOC_COM.SEL2
			CompanyDto regalDocCompany = residentialTaxRepo.findRegalCompany(companyCode, query.getRegalDocCompanyCode());		
		}	
		
		
		// QTXMT_RESIDENTIAL_TAXSLIP.SEL_2
		List<String> residentTaxCodeList = query.getResidentTaxCodeList();
		List<ResidentialTaxSlipDto> residentialTaxSlipDto = residentialTaxRepo.findResidentialTaxSlip(companyCode, query.getYearMonth(), residentTaxCodeList);
		
		// PPRMT_PERSON_RESITAX.SEL_1
		List<PersonResitaxDto> personResidentTaxList = residentialTaxRepo.findPersonResidentTax(companyCode, query.getYearMonth(), residentTaxCodeList);
		int totalPerson = personResidentTaxList.size();
		
		// PAYMENTDETAI.SEL_6
		List<String> personIdList = personResidentTaxList.stream().map(x -> x.getPersonID()).collect(Collectors.toList());
		List<PaymentDetailDto> paymentDetailList = residentialTaxRepo.findPaymentDetail(
				companyCode,
				personIdList,
				PayBonusAtr.SALARY,
				query.getProcessingYearMonth(),
				CategoryAtr.DEDUCTION,
			    "F108");
		// RETIREMENT_PAYMENT(退職金明細データ).SEL-2
		String processingYearMonth = String.valueOf(query.getProcessingYearMonth());
		int year = Integer.valueOf(processingYearMonth.substring(0, 3));
		int month = Integer.valueOf(processingYearMonth.substring(3, 4));
		GeneralDate baseRangeStartYearMonth = GeneralDate.ymd(year, month, 11);
		GeneralDate baseRangeEndYearMonth = query.getEndDate();
		List<RetirementPaymentDto> retirementPaymentList = residentialTaxRepo.findRetirementPaymentList(
				companyCode,
				personIdList,
				baseRangeStartYearMonth,
				baseRangeEndYearMonth
				);
				
		// return
		List<ResidentTaxReportData> reportDataList = new ArrayList<ResidentTaxReportData>();
		
		//Map<String, ResidentialTaxDto> residentTaxMap = residentTaxList.stream().collect(Collectors.toMap(ResidentialTaxDto::getResidenceTaxCode, x->x));
		
		Map<String, ResidentialTaxSlipDto> residentialTaxSlipMap = residentialTaxSlipDto.stream().collect(Collectors.toMap(ResidentialTaxSlipDto::getResiTaxCode, y->y));
		
		
//		for (ResidentialTaxDto residentialTax : residentTaxList) {
//			ResidentialTaxSlipDto residentialTaxSlip = residentialTaxSlipMap.get(residentialTax.getResidenceTaxCode());
//			ResidentTaxReportData reportData = new ResidentTaxReportData();
//			reportData.setResidenceTaxCode(residentialTax.getResidenceTaxCode());
//			reportData.setResiTaxAutonomy(residentialTax.getResiTaxAutonomy());
//			reportData.setCompanyAccountNo(residentialTax.getCompanyAccountNo());
//			reportData.setRegisteredName(residentialTax.getRegisteredName());
//			reportData.setCompanySpecifiedNo(residentialTax.getCompanySpecifiedNo());
//			reportData.setCordinatePostalCode(residentialTax.getCordinatePostalCode());
//			reportData.setCordinatePostOffice(residentialTax.getCordinatePostOffice());
//			reportData.setDbd006(dbd006.toString());
//			reportData.setDbd007(dbd007.toString());
//			reportData.setPostal(company.getPostal());
//			reportData.setAddress1(company.getAddress1());
//			reportData.setAddress2(company.getAddress2());
//			reportData.setCompanyName(company.getCompanyName());
//			reportData.setCityTaxMny2(String.valueOf(residentialTaxSlip.getCityTaxMny()));
//			reportData.setPrefectureTaxMny2(String.valueOf(residentialTaxSlip.getPrefectureTaxMny()));
//			reportData.setTaxOverdueMny(String.valueOf(residentialTaxSlip.getTaxOverdueMny()));
//			reportData.setTaxDemandChargeMny(String.valueOf(residentialTaxSlip.getTaxDemandChargeMny()));
//			reportData.setDueDate(residentialTaxSlip.getDueDate().toString());
//			reportData.setDbd014(dbd014.toString());	
//		}
		ResidentTaxReportData reportData = new ResidentTaxReportData();
		reportData.setResidenceTaxCode("Son");
		reportData.setResiTaxAutonomy("Son");
		reportData.setCompanyAccountNo("Son");
		reportData.setRegisteredName("Son");
		reportData.setCompanySpecifiedNo("Son");
		reportData.setCordinatePostalCode("Son");
		reportData.setCordinatePostOffice("Son");
		reportData.setDbd006("Son");
		reportData.setDbd007("Son");
		reportData.setPostal("Son");
		reportData.setAddress1("Son");
		reportData.setAddress2("Son");
		reportData.setCompanyName("Son");
		reportData.setCityTaxMny2("Son");
		reportData.setPrefectureTaxMny2("Son");
		reportData.setTaxOverdueMny("Son");
		reportData.setTaxDemandChargeMny(String.valueOf("Son"));
		reportData.setDueDate("Son");
		reportData.setDbd014("Son");	
		
						
		this.generate.generate(context.getGeneratorContext(), reportData);
	}

}
