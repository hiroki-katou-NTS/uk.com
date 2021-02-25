package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SpecBPTimesheetFinder {
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;

	public List<SpecBPTimesheetDto> getListTimesheet(String bonusPaySettingCode) {
		String companyId = AppContexts.user().companyId();
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = specBPTimesheetRepository.getListTimesheet(companyId,
				new BonusPaySettingCode(bonusPaySettingCode));
		return lstSpecBonusPayTimesheet.stream().map(c -> toSpecBPTimesheetDto(companyId, bonusPaySettingCode, c))
				.collect(Collectors.toList());
	}

	private SpecBPTimesheetDto toSpecBPTimesheetDto(String companyId, String bonusPaySettingCode,
			SpecBonusPayTimesheet specBonusPayTimesheet) {
		return new SpecBPTimesheetDto(companyId, specBonusPayTimesheet.getTimeSheetId(),
				specBonusPayTimesheet.getUseAtr().value, bonusPaySettingCode, specBonusPayTimesheet.getTimeItemId(),
				specBonusPayTimesheet.getStartTime().v(), specBonusPayTimesheet.getEndTime().v(),
				specBonusPayTimesheet.getRoundingTimeAtr().value, specBonusPayTimesheet.getRoundingAtr().value,
				specBonusPayTimesheet.getDateCode());
	}
}
