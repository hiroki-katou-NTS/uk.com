package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FurikyuMngDataExtractionService {
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;
	
	public List<FurikyuMngDataExtractionData> getFurikyuMngDataExtraction(String sid, GeneralDate startDate, GeneralDate endDate, boolean isPeriod) {
		String cid = AppContexts.user().companyId();
		List<PayoutManagementData> payoutManagementData;
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData;
		List<FurikyuMngDataExtractionData> furikyuMngDataExtractionData = new ArrayList<FurikyuMngDataExtractionData>();
		
		// select 過去の結果
		if(isPeriod) {
			payoutManagementData = payoutManagementDataRepository.getBySidDatePeriodNoDigestion(sid, startDate, endDate);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBySidDatePeriodNoRemainDay(sid, startDate, endDate);
		// select 現在の残数状況
		} else {
			payoutManagementData = payoutManagementDataRepository.getSidWithCod(cid, sid, 0);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBySidRemainDayAndInPayout(sid);
		}
		
		for (PayoutManagementData item : payoutManagementData) {
			furikyuMngDataExtractionData.add(new FurikyuMngDataExtractionData(item, null));
		}
		
		for (SubstitutionOfHDManagementData item : substitutionOfHDManagementData) {
			furikyuMngDataExtractionData.add(new FurikyuMngDataExtractionData(null, item));
		}
		
		return furikyuMngDataExtractionData;
	}
}
