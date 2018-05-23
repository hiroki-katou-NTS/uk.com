package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.FurikyuMngDataExtractionData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.FurikyuMngDataExtractionService;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@Stateless
public class FurikyuMngDataExtractionFinder {

	@Inject
	private FurikyuMngDataExtractionService furikyuMngDataExtractionService;

	public FurikyuMngDataExtractionDto getFurikyuMngDataExtraction(String empId, GeneralDate startDate,
			GeneralDate endDate, boolean isPeriod) {

		FurikyuMngDataExtractionData furikyuMngDataExtractionData = furikyuMngDataExtractionService
				.getFurikyuMngDataExtraction(empId, startDate, endDate, isPeriod);
		List<PayoutManagementData> payoutManagementData = furikyuMngDataExtractionData.getPayoutManagementData();
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData = furikyuMngDataExtractionData
				.getSubstitutionOfHDManagementData();
		List<CompositePayOutSubMngData> compositePayOutSubMngData = new ArrayList<CompositePayOutSubMngData>();

		for (PayoutManagementData item : payoutManagementData) {
			compositePayOutSubMngData.add(new CompositePayOutSubMngData(item));
		}

		for (SubstitutionOfHDManagementData item : substitutionOfHDManagementData) {
			compositePayOutSubMngData.add(new CompositePayOutSubMngData(item));
		}

		return new FurikyuMngDataExtractionDto(compositePayOutSubMngData, furikyuMngDataExtractionData.getExpirationDate(),
				furikyuMngDataExtractionData.getNumberOfDayLeft(), furikyuMngDataExtractionData.getClosureId());
	}
}