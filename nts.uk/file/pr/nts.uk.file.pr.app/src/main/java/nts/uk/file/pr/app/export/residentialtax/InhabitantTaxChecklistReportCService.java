package nts.uk.file.pr.app.export.residentialtax;

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
import nts.uk.file.pr.app.export.residentialtax.data.PaymentDetailDto;
import nts.uk.file.pr.app.export.residentialtax.data.PersonResitaxDto;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentialTaxDto;

@Stateless
public class InhabitantTaxChecklistReportCService extends ExportService<InhabitantTaxChecklistQuery> {
	@Inject
	private ResidentialTaxGenerator generate;
	@Inject
	private ResidentialTaxReportRepository residentialTaxRepo;

	@Override
	protected void handle(ExportServiceContext<InhabitantTaxChecklistQuery> context) {
		
		String companyCode = "0001";
		int Y_K = 2016;
		Map<String, Double> totalPaymentAmount = new HashMap<>();
		Map<String, Integer> totalNumberPeople = new HashMap<>();

		InhabitantTaxChecklistQuery query = context.getQuery();

		// get residential tax
		List<ResidentialTaxDto> residentTaxList = residentialTaxRepo.findResidentTax(companyCode,
				query.getResidentTaxCodeList());

		List<PersonResitaxDto> personResidentTaxList = residentialTaxRepo.findPersonResidentTax(companyCode, Y_K,
				query.getResidentTaxCodeList());

		Map<String, List<PersonResitaxDto>> personResidentTaxListMap = personResidentTaxList.stream()
				.collect(Collectors.groupingBy(PersonResitaxDto::getResidenceCode, Collectors.toList()));

		for (PersonResitaxDto residentialTax : personResidentTaxList) {
			List<PersonResitaxDto> personResitaxList = personResidentTaxListMap.get(residentialTax.getResidenceCode());
			List<String> personIdList = personResitaxList.stream().map(x -> x.getPersonID())
					.collect(Collectors.toList());

			List<PaymentDetailDto> paymentDetailList = residentialTaxRepo.findPaymentDetail(companyCode, personIdList,
					PayBonusAtr.SALARY, query.getProcessingYearMonth(), CategoryAtr.DEDUCTION, "F108");

			double totalValue = paymentDetailList.stream().mapToDouble(x -> x.getValue().doubleValue()).sum();

			totalPaymentAmount.put(residentialTax.getResidenceCode(), totalValue);
			totalNumberPeople.put(residentialTax.getResidenceCode(), personResitaxList.size());
		}

	}
}
