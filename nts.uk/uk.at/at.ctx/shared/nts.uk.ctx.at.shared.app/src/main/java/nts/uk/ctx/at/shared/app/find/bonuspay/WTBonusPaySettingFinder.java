package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;

@Stateless
@Transactional
public class WTBonusPaySettingFinder {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	public List<WTBonusPaySettingDto> getListSetting(String companyId) {
		List<WorkingTimesheetBonusPaySetting> lstWorkingTimesheetBonusPaySetting = this.wtBonusPaySettingRepository
				.getListSetting(companyId);
		return lstWorkingTimesheetBonusPaySetting.stream().map(c -> toWTBonusPaySettingDto(c))
				.collect(Collectors.toList());
	}

	private WTBonusPaySettingDto toWTBonusPaySettingDto(
			WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting) {
		return new WTBonusPaySettingDto(workingTimesheetBonusPaySetting.getCompanyId().toString(),
				workingTimesheetBonusPaySetting.getWorkingTimesheetCode().toString(),
				workingTimesheetBonusPaySetting.getBonusPaySettingCode().toString());
	}
}
