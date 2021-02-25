package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkingTimesheetBonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class WTBonusPaySettingFinder {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	public List<WTBonusPaySettingDto> getListSetting() {
		String companyId = AppContexts.user().companyId();
		List<WorkingTimesheetBonusPaySetting> lstWorkingTimesheetBonusPaySetting = this.wtBonusPaySettingRepository
				.getListSetting(companyId);
		return lstWorkingTimesheetBonusPaySetting.stream().map(c -> toWTBonusPaySettingDto(c))
				.collect(Collectors.toList());
	}

	public WTBonusPaySettingDto getWTBPSetting(String workingTimesheetCode) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkingTimesheetBonusPaySetting> workingTimesheetBonusPaySetting = this.wtBonusPaySettingRepository
				.getWTBPSetting(companyId, new WorkingTimesheetCode(workingTimesheetCode));
		if(workingTimesheetBonusPaySetting.isPresent()){
			return this.toWTBonusPaySettingDto(workingTimesheetBonusPaySetting.get());
		}
		return null;
	}

	private WTBonusPaySettingDto toWTBonusPaySettingDto(
			WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting) {
		return new WTBonusPaySettingDto(workingTimesheetBonusPaySetting.getCompanyId().toString(),
				workingTimesheetBonusPaySetting.getWorkingTimesheetCode().toString(),
				workingTimesheetBonusPaySetting.getBonusPaySettingCode().toString());
	}
}
