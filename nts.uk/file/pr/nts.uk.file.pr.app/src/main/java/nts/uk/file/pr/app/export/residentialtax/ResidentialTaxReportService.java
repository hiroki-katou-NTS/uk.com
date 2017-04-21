package nts.uk.file.pr.app.export.residentialtax;

import java.util.ArrayList;
import java.util.HashMap;
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

		String companyCode = "0001";
		int Y_K = 2016;
		Map<String, Double> totalSalaryPaymentAmount = new HashMap<>();
		Map<String, Double> totalDeliveryAmountRetirement = new HashMap<>();
		Map<String, Double> totalActualRecieveMnyMap = new HashMap<>();
		Map<String, Integer> deliveryNumber = new HashMap<>();

		// get query
		ResidentialTaxQuery query = context.getQuery();

		// get residential tax
		List<ResidentialTaxDto> residentTaxList = residentialTaxRepo.findResidentTax(companyCode,
				query.getResidentTaxCodeList());

		CompanyDto company = residentialTaxRepo.findCompany(companyCode);
		// if (query.isCompanyLogin()) {
		// // get company SEL_3
		// //CompanyDto company = residentialTaxRepo.findCompany(companyCode);
		// } else {
		// // get QCPMT_REGAL_DOC_COM.SEL2
		// CompanyDto regalDocCompany =
		// residentialTaxRepo.findRegalCompany(companyCode,
		// query.getRegalDocCompanyCode());
		// }

		// QTXMT_RESIDENTIAL_TAXSLIP.SEL_2
		List<ResidentialTaxSlipDto> residentialTaxSlipDto = residentialTaxRepo.findResidentialTaxSlip(companyCode,
				query.getYearMonth(), query.getResidentTaxCodeList());

		// PPRMT_PERSON_RESITAX.SEL_1
		List<PersonResitaxDto> personResidentTaxList = residentialTaxRepo.findPersonResidentTax(companyCode, Y_K,
				query.getResidentTaxCodeList());

		Map<String, List<PersonResitaxDto>> personResidentTaxListMap = personResidentTaxList.stream()
				.collect(Collectors.groupingBy(PersonResitaxDto::getResidenceCode, Collectors.toList()));

		// PAYMENTDETAI.SEL_6
		// List<String> personIdList = personResidentTaxList.stream().map(x ->
		// x.getPersonID()).collect(Collectors.toList());
		String processingYearMonth = String.valueOf(query.getProcessingYearMonth());
		int year = Integer.valueOf(processingYearMonth.substring(0, 4));
		int month = Integer.valueOf(processingYearMonth.substring(4, 6));
		GeneralDate baseRangeStartYearMonth = GeneralDate.ymd(2017, 03, 11);
		// GeneralDate baseRangeEndYearMonth = query.getEndDate();
		GeneralDate baseRangeEndYearMonth = GeneralDate.ymd(2017, 04, 01);
		for (ResidentialTaxSlipDto residentialTax : residentialTaxSlipDto) {
			
			List<PersonResitaxDto> personResitaxList = personResidentTaxListMap.get(residentialTax.getResiTaxCode());
			List<String> personIdList =  personResitaxList.stream()
					.map(x -> x.getPersonID())
					.collect(Collectors.toList());
			
			List<PaymentDetailDto> paymentDetailList = residentialTaxRepo.findPaymentDetail(companyCode, personIdList,
					PayBonusAtr.SALARY, query.getProcessingYearMonth(), CategoryAtr.DEDUCTION, "F108");
			
			double totalValue = paymentDetailList.stream()
			                 .mapToDouble(x->x.getValue().doubleValue())
			                 .sum();
			totalSalaryPaymentAmount.put(residentialTax.getResiTaxCode(),totalValue + residentialTax.getTaxPayrollMny().doubleValue());
			
			// RETIREMENT_PAYMENT(退職金明細データ).SEL-2
			List<RetirementPaymentDto> retirementPaymentList = residentialTaxRepo.findRetirementPaymentList(companyCode,
					personIdList, baseRangeStartYearMonth, baseRangeEndYearMonth);
			double totalCityTaxMny = retirementPaymentList.stream()
					               .mapToDouble(x -> x.getCityTaxMny().doubleValue())
					               .sum();
			double totalPrefectureTaxMny = retirementPaymentList.stream()
					               .mapToDouble(x -> x.getPrefectureTaxMny().doubleValue())
					               .sum();
			totalDeliveryAmountRetirement.put(residentialTax.getResiTaxCode(),totalPrefectureTaxMny + totalCityTaxMny + residentialTax.getTaxRetirementMny().doubleValue());
			double totalActualRecieveMny = retirementPaymentList.stream()
					               .mapToDouble(x -> x.getActualRecieveMny().doubleValue())
					               .sum();
			totalActualRecieveMnyMap.put(residentialTax.getResiTaxCode(),totalActualRecieveMny);
			deliveryNumber.put(residentialTax.getResiTaxCode(), residentialTax.getHeadCount().intValue() + personResitaxList.size());
				
		};
		// List<String> personIdList = personResidentTaxListMap

		// return
		List<ResidentTaxReportData> reportDataList = new ArrayList<ResidentTaxReportData>();

		Map<String, ResidentialTaxSlipDto> residentialTaxSlipMap = residentialTaxSlipDto.stream()
				.collect(Collectors.toMap(ResidentialTaxSlipDto::getResiTaxCode, y -> y));

		// Map<String, PaymentDetailDto> paymentDetailListMap =
		// paymentDetailList.stream().collect(Collectors.toMap(keyMapper,
		// valueMapper));

		for (ResidentialTaxDto residentialTax : residentTaxList) {
			ResidentialTaxSlipDto residentialTaxSlip = residentialTaxSlipMap.get(residentialTax.getResidenceTaxCode());
			String salaryPaymentAmount = totalSalaryPaymentAmount.get(residentialTax.getResidenceTaxCode()).toString();
			String deliveryAmountRetirement = totalDeliveryAmountRetirement.get(residentialTax.getResidenceTaxCode()).toString();
			Double totalAmountTobePaid = totalSalaryPaymentAmount.get(residentialTax.getResidenceTaxCode()) + totalDeliveryAmountRetirement.get(residentialTax.getResidenceTaxCode());
			String deliveryNumberString = deliveryNumber.get(residentialTax.getResidenceTaxCode()).toString();
			String actualRecieveMnyMap = totalActualRecieveMnyMap.get(residentialTax.getResidenceTaxCode()).toString();
			ResidentTaxReportData reportData = new ResidentTaxReportData();
			//DBD_001  residenceTaxCode
			reportData.setResidenceTaxCode(residentialTax.getResidenceTaxCode());
			//DBD_002  resiTaxAutonomy
            reportData.setResiTaxAutonomy(residentialTax.getResiTaxAutonomy());
			//DBD_003  companyAccountNo
            reportData.setCompanyAccountNo(residentialTax.getCompanyAccountNo());
			//DBD_004  registeredName
            reportData.setRegisteredName(residentialTax.getRegisteredName());
			//DBD_005  companySpecifiedNo
			reportData.setCompanySpecifiedNo(residentialTax.getCompanySpecifiedNo());
			//DBD_006  salaryPaymentAmount
			reportData.setSalaryPaymentAmount(salaryPaymentAmount);
			//DBD_007  deliveryAmountRetirement
			reportData.setDeliveryAmountRetirement(deliveryAmountRetirement);
			//DBD_008  postal
			reportData.setPostal(company.getPostal());
			//DBD_009  address1
			reportData.setAddress1(company.getAddress1());
			//DBD_010  address2
			reportData.setAddress2(company.getAddress2());
			//DBD_011  companyName
			reportData.setCompanyName(company.getCompanyName());
			//DBD_012  cordinatePostOffice
			reportData.setCordinatePostOffice(residentialTax.getCordinatePostOffice());
			//DBD_013  cordinatePostalCode
			reportData.setCordinatePostalCode(residentialTax.getCordinatePostalCode());
			//DBD_014  deliveryNumber
			reportData.setDeliveryNumber(deliveryNumberString);
			//DBD_015  actualRecieveMny
			reportData.setActualRecieveMny(actualRecieveMnyMap);
			//DBD_016  cityTaxMny
			reportData.setCityTaxMny(residentialTaxSlip.getCityTaxMny().toString());
			//DBD_017  prefectureTaxMny
			reportData.setPrefectureTaxMny(residentialTaxSlip.getPrefectureTaxMny().toString());
			//DBD_018  taxOverdueMny
			reportData.setTaxOverdueMny(residentialTaxSlip.getTaxOverdueMny().toString());
			//DBD_019  taxDemandChargeMny
			reportData.setTaxDemandChargeMny(residentialTaxSlip.getTaxDemandChargeMny().toString());
			//DBD_020  filingDate
			reportData.setFilingDate(residentialTaxSlip.getDueDate().toString());
			//CTR_001  designatedYM
			reportData.setDesignatedYM(Integer.toString(query.getProcessingYearMonth()));
			//CTR_002  totalAmountTobePaid
			reportData.setTotalAmountTobePaid(totalAmountTobePaid.toString());
			//CTR_003  dueDate
			reportData.setDueDate(query.getEndDate().toString());
			
			reportDataList.add(reportData);
		}

		this.generate.generate(context.getGeneratorContext(), reportDataList);
	}

}
