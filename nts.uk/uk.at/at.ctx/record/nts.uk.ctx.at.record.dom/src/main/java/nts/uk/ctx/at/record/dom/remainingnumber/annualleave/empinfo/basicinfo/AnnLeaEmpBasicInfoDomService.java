package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;

@Stateless
public class AnnLeaEmpBasicInfoDomService{
	@Inject
	private AnnualPaidLeaveSettingRepository annPaidLeaSettingRepo;
	
	private static final String granted = "付与日";
	
	private static final String not_grant = "未付与";

	public String calculateAnnualLeaveNumber(String companyId, List<AnnualLeaveGrantRemainingData> listData) {
		
		Double remainingDays = 0d;
		for (AnnualLeaveGrantRemainingData data : listData) {
			remainingDays += data.getDetails().getRemainingNumber().getDays().v();
		}

		AnnualPaidLeaveSetting setting = annPaidLeaSettingRepo.findByCompanyId(companyId);
		boolean useTimeAnnualLeave = setting.getYearManageType() == ManageDistinct.YES
				&& setting.getTimeSetting().getTimeManageType() == ManageDistinct.YES;

		int remainingMinutes = 0;
		if (useTimeAnnualLeave) {
			for (AnnualLeaveGrantRemainingData data : listData) {
				Optional<AnnualLeaveRemainingTime> minutes = data.getDetails().getRemainingNumber().getMinutes();
				remainingMinutes += minutes.isPresent() ? minutes.get().v() : 0;
			}
		}

		int remainingHours = remainingMinutes / 60;
		remainingMinutes -= remainingHours * 60;
		
		return remainingDays + "日と　" + remainingHours + " : " + convertWithMinutes(remainingMinutes);
	}
	
	public String calculateLastGrantDate(List<AnnualLeaveGrantRemainingData> listData) {
		if (listData.isEmpty()) {
			return not_grant; 
		} else {
			return granted;
		}
	}
	
	public String calculateRervLeaveNumber(List<ReserveLeaveGrantRemainingData> listData) {
		Double remainingDays = 0d;
		for (ReserveLeaveGrantRemainingData data : listData) {
			remainingDays += data.getDetails().getRemainingNumber().v();
		}
		return remainingDays + "日";
	}
	
	private String convertWithMinutes(int minutes) {
		if ( minutes < 10) {
			return "0" + minutes;
		}
		return "" + minutes;
	}
	
}
