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
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
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
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout = furikyuMngDataExtractionData.getPayoutSubofHDManagementLinkToPayout();
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToSub = furikyuMngDataExtractionData.getPayoutSubofHDManagementLinkToSub();
		List<CompositePayOutSubMngData> compositePayOutSubMngData = new ArrayList<CompositePayOutSubMngData>();

		for (PayoutManagementData item : payoutManagementData) {
			compositePayOutSubMngData.add(new CompositePayOutSubMngData(item, payoutSubofHDManagementLinkToPayout));
		}

		for (SubstitutionOfHDManagementData item : substitutionOfHDManagementData) {
			compositePayOutSubMngData.add(new CompositePayOutSubMngData(item, payoutSubofHDManagementLinkToSub));
		}

		compositePayOutSubMngData.sort(new Comparator<CompositePayOutSubMngData>() {
		    @Override
		    public int compare(CompositePayOutSubMngData m1, CompositePayOutSubMngData m2) {
		    	
		    	GeneralDate date1 = (m1.getPayoutId() != null) ? m1.getDayoffDatePyout() : m1.getDayoffDateSub();
		    	GeneralDate date2 = (m2.getPayoutId() != null) ? m2.getDayoffDatePyout() : m2.getDayoffDateSub();
		    	
		    	if (date1 == null) {					
		    		if(date2 == null) return 0;					
		    		return -1;				
		    	} else {					
		    		if(date2 == null) return 1;					
		    		return date1.before(date2) ? -1 : 1;				
		    	}
		    }
		});
		
		return new FurikyuMngDataExtractionDto(compositePayOutSubMngData, furikyuMngDataExtractionData.getExpirationDate(),
				furikyuMngDataExtractionData.getNumberOfDayLeft(), furikyuMngDataExtractionData.getClosureId(), furikyuMngDataExtractionData.isHaveEmploymentCode(),
				furikyuMngDataExtractionData.getSWkpHistImport(), furikyuMngDataExtractionData.getPersonEmpBasicInfoImport());
	}
}