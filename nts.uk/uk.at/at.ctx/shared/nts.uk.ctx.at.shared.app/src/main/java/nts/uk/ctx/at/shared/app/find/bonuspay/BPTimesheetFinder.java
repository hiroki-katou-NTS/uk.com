package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPTimesheetFinder {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;

	public List<BPTimesheetDto> getListTimesheet(String bonusPaySettingCode) {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimesheet> lstBonusPayTimesheet = bpTimesheetRepository.getListTimesheet(companyId,
				bonusPaySettingCode);
		return lstBonusPayTimesheet.stream().map(c -> toBPTimesheetDto(companyId, bonusPaySettingCode, c))
				.collect(Collectors.toList());
	}

	private BPTimesheetDto toBPTimesheetDto(String companyId, String bonusPaySettingCode,
			BonusPayTimesheet bonusPayTimesheet) {
		return new BPTimesheetDto(companyId, bonusPayTimesheet.getTimeSheetId(), bonusPayTimesheet.getUseAtr().value,
				bonusPaySettingCode, bonusPayTimesheet.getTimeItemId().toString(),
				bonusPayTimesheet.getStartTime().minute(), bonusPayTimesheet.getEndTime().minute(),
				bonusPayTimesheet.getRoundingTimeAtr().value, bonusPayTimesheet.getRoundingAtr().value);

	}

}
