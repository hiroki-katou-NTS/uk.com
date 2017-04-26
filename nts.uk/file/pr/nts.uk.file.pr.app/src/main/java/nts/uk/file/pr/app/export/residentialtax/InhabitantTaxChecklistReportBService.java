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
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.file.pr.app.export.residentialtax.data.CompanyDto;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistBReport;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistBRpData;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistBRpHeader;
import nts.uk.file.pr.app.export.residentialtax.data.PaymentDetailDto;
import nts.uk.file.pr.app.export.residentialtax.data.PersonResitaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InhabitantTaxChecklistReportBService extends ExportService<InhabitantTaxChecklistQuery> {
	@Inject
	private InhabitantTaxChecklistBGenerator generate;
	@Inject
	private ResidentialTaxReportRepository residentialTaxRepo;

	@Override
	protected void handle(ExportServiceContext<InhabitantTaxChecklistQuery> context) {

		String companyCode = AppContexts.user().companyCode();
		int Y_K = 2016;
		Map<String, Double> totalPaymentAmount = new HashMap<>();
		Map<String, Integer> totalNumberPeople = new HashMap<>();

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
			List<PersonResitaxDto> personResitaxList = personResidentTaxListMap.get(personResidentTax.getResidenceCode());
			List<String> personIdList = personResitaxList.stream().map(x -> x.getPersonID())
					.collect(Collectors.toList());
			
			List<PaymentDetailDto> paymentDetailList = residentialTaxRepo.findPaymentDetail(companyCode, personIdList,
					PayBonusAtr.SALARY, query.getProcessingYearMonth(), CategoryAtr.DEDUCTION, "F108");
			
			double totalValue = paymentDetailList.stream()
	                 .mapToDouble(x->x.getValue().doubleValue())
	                 .sum();
			
			totalPaymentAmount.put(personResidentTax.getResidenceCode(), totalValue);
			totalNumberPeople.put(personResidentTax.getResidenceCode(), personResitaxList.size());
		}
		
		List<InhabitantTaxChecklistBRpData> reportDataList = new ArrayList<InhabitantTaxChecklistBRpData>();
		
		for (ResidentialTaxDto residentialTax : residentTaxList) {
			
			InhabitantTaxChecklistBRpData reportData = new InhabitantTaxChecklistBRpData();
			//DBD_001 residenceTaxCode
			 reportData.setResidenceTaxCode(residentialTax.getResidenceTaxCode());
			 
			//DBD_002 resiTaxAutonomy
			 reportData.setResiTaxAutonomy(residentialTax.getResiTaxAutonomy());
			 
			//DBD_003  numberPeople
			 reportData.setNumberPeople(totalNumberPeople.get(residentialTax.getResidenceTaxCode()).toString());
			//DBD_004 value
			 reportData.setValue(totalPaymentAmount.get(residentialTax.getResidenceTaxCode()));
			 reportDataList.add(reportData);
		}
		
		InhabitantTaxChecklistBRpData sumReportData = new InhabitantTaxChecklistBRpData();
			
		Double sumPaymentAmount = totalPaymentAmount.values().stream()
				.mapToDouble(x -> x.doubleValue())
				.sum();
				
		Integer sumNumberPeople = totalNumberPeople.values().stream()
				.mapToInt(x -> x.intValue())
				.sum();
		sumReportData.setResidenceTaxCode("");
		sumReportData.setResiTaxAutonomy("総合計");
		sumReportData.setNumberPeople(sumNumberPeople.toString());
		sumReportData.setValue(sumPaymentAmount);
		
		reportDataList.add(sumReportData);
		
		InhabitantTaxChecklistBRpHeader header = new InhabitantTaxChecklistBRpHeader();		
		header.setCompanyName("日通システム株式会社");
		header.setStartResiTaxAutonomy(reportDataList.get(0).getResidenceTaxCode()+" "+reportDataList.get(0).getResiTaxAutonomy());
		header.setLateResiTaxAutonomy(reportDataList.get(0).getResidenceTaxCode()+" "+reportDataList.get(0).getResiTaxAutonomy());
		header.setDate("2017/04/24");
		
		InhabitantTaxChecklistBReport dataReportB = new InhabitantTaxChecklistBReport();
		dataReportB.setData(reportDataList);
		dataReportB.setHeader(header);
		this.generate.generate(context.getGeneratorContext(), dataReportB);

	}

}
