package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.FurikyuMngDataExtractionData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.FurikyuMngDataExtractionService;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

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

		compositePayOutSubMngData.sort(new Comparator<CompositePayOutSubMngData>() {
		    @Override
		    public int compare(CompositePayOutSubMngData m1, CompositePayOutSubMngData m2) {
		    	GeneralDate date1 = (m1.getPayoutId() != null) ? m1.getDayoffDatePyout() : m1.getDayoffDateSub();
		    	GeneralDate date2 = (m2.getPayoutId() != null) ? m2.getDayoffDatePyout() : m2.getDayoffDateSub();
		    	
		    	return date1.before(date2) ? -1 : 1;
		    }
		});
		
		return new FurikyuMngDataExtractionDto(compositePayOutSubMngData, furikyuMngDataExtractionData.getExpirationDate(),
				furikyuMngDataExtractionData.getNumberOfDayLeft(), furikyuMngDataExtractionData.getClosureId());
	}
}