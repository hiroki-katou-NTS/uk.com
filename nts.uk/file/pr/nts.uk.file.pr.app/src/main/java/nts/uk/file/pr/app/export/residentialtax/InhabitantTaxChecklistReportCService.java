package nts.uk.file.pr.app.export.residentialtax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.file.pr.app.export.residentialtax.data.CompanyDto;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistBRpHeader;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistCReport;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistCRpData;
import nts.uk.file.pr.app.export.residentialtax.data.PaymentDetailDto;
import nts.uk.file.pr.app.export.residentialtax.data.PersonDetailDto;
import nts.uk.file.pr.app.export.residentialtax.data.PersonResitaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InhabitantTaxChecklistReportCService extends ExportService<InhabitantTaxChecklistQuery> {
	@Inject
	private InhabitantTaxChecklistCGenerator generate;
	@Inject
	private ResidentialTaxReportRepository residentialTaxRepo;

	@Override
	protected void handle(ExportServiceContext<InhabitantTaxChecklistQuery> context) {

		String companyCode = AppContexts.user().companyCode();
		int Y_K = 2016;
		Map<String, Double> totalPaymentAmount = new HashMap<>();
		Map<String, Integer> totalNumberPeople = new HashMap<>();
        Map<String, BigDecimal> personValue = new HashMap<>();
		InhabitantTaxChecklistQuery query = context.getQuery();

		// get residential tax
		List<ResidentialTaxDto> residentTaxList = residentialTaxRepo.findResidentTax(companyCode,
				query.getResidentTaxCodeList());

		List<PersonResitaxDto> personResidentTaxList = residentialTaxRepo.findPersonResidentTax(companyCode, Y_K,
				query.getResidentTaxCodeList());
		
		CompanyDto company = residentialTaxRepo.findCompany(companyCode);

		Map<String, List<PersonResitaxDto>> personResidentTaxListMap = personResidentTaxList.stream()
				.collect(Collectors.groupingBy(PersonResitaxDto::getResidenceCode, Collectors.toList()));

		for (PersonResitaxDto personResidentTax : personResidentTaxList) {
			List<PersonResitaxDto> personResitaxList = personResidentTaxListMap
					.get(personResidentTax.getResidenceCode());
			List<String> personIdList = personResitaxList.stream().map(x -> x.getPersonID())
					.collect(Collectors.toList());

			List<PaymentDetailDto> paymentDetailList = residentialTaxRepo.findPaymentDetail(companyCode, personIdList,
					PayBonusAtr.SALARY, query.getProcessingYearMonth(), CategoryAtr.DEDUCTION, "F108");

			double totalValue = paymentDetailList.stream().mapToDouble(x -> x.getValue().doubleValue()).sum();
            for (PaymentDetailDto paymentDetail : paymentDetailList) {
				 personValue.put(paymentDetail.getPersonId(), paymentDetail.getValue());
			}
			totalPaymentAmount.put(personResidentTax.getResidenceCode(), totalValue);
			totalNumberPeople.put(personResidentTax.getResidenceCode(), personResitaxList.size());
		}


		List<InhabitantTaxChecklistCRpData> reportDataList = new ArrayList<InhabitantTaxChecklistCRpData>();

		for (ResidentialTaxDto residentialTax : residentTaxList) {

			InhabitantTaxChecklistCRpData reportData = new InhabitantTaxChecklistCRpData();
			List<PersonResitaxDto> personResitaxList = personResidentTaxListMap
					.get(residentialTax.getResidenceTaxCode());
			for (PersonResitaxDto item : personResitaxList) {
				InhabitantTaxChecklistCRpData personData = new InhabitantTaxChecklistCRpData();
				BigDecimal value = personValue.get(item.getPersonID());
				// DBD_003
				personData.setResidenceTaxCode(residentialTax.getResidenceTaxCode());
				// DBD_004
				personData.setResiTaxAutonomy(residentialTax.getResiTaxAutonomy());
				// DBD_005
				personData.setCode("0000001");
				// DBD_006
				personData.setName("テスト名前");
				// DBD_007
				if(value == null) {
					personData.setValue(0d);
				} else {
					personData.setValue(value.doubleValue());
				}			
				reportDataList.add(personData);
			}
			// DBD_003 residenceTaxCode
			reportData.setResidenceTaxCode("");
			// DBD_004 resiTaxAutonomy
			reportData.setResiTaxAutonomy("合計");
			// DBD_005
			reportData.setCode("");
			// DBD_006
			reportData.setName(totalNumberPeople.get(residentialTax.getResidenceTaxCode()).toString());
			// DBD_007
			reportData.setValue(totalPaymentAmount.get(residentialTax.getResidenceTaxCode()));
			reportDataList.add(reportData);
		}

		InhabitantTaxChecklistCRpData sumReportData = new InhabitantTaxChecklistCRpData();

		Double sumPaymentAmount = totalPaymentAmount.values().stream().mapToDouble(x -> x.doubleValue()).sum();

		Integer sumNumberPeople = totalNumberPeople.values().stream().mapToInt(x -> x.intValue()).sum();
		
		// DBD_003 residenceTaxCode
		sumReportData.setResidenceTaxCode("");
		// DBD_004 resiTaxAutonomy
		sumReportData.setResiTaxAutonomy("総合計");
		// DBD_005
		sumReportData.setCode("");
		// DBD_006
		sumReportData.setName(sumNumberPeople.toString());
		// DBD_007
		sumReportData.setValue(sumPaymentAmount);

		reportDataList.add(sumReportData);

		InhabitantTaxChecklistBRpHeader header = new InhabitantTaxChecklistBRpHeader();
		int size = reportDataList.size() - 3;
		header.setCompanyName(company.getCompanyName());
		header.setStartResiTaxAutonomy(
				reportDataList.get(0).getResidenceTaxCode() + " " + reportDataList.get(0).getResiTaxAutonomy());
		header.setLateResiTaxAutonomy(
				reportDataList.get(size).getResidenceTaxCode() + " " + reportDataList.get(size).getResiTaxAutonomy());
		header.setDate("2017/04/24");

		InhabitantTaxChecklistCReport dataReportC = new InhabitantTaxChecklistCReport();
	    dataReportC.setData(reportDataList);
		dataReportC.setHeader(header);
		this.generate.generate(context.getGeneratorContext(), dataReportC);

	}

	private Map<String, PersonDetailDto> fixPersonDetail() {
		Map<String, PersonDetailDto> result = new LinkedHashMap<>();

		result.put("1", new PersonDetailDto("1", "1", ""));
		result.put("2", new PersonDetailDto("2", "", ""));
		result.put("", new PersonDetailDto("", "", ""));

		return result;
	}

}
