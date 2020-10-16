package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPTimesheetFinder {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;

	public List<BPTimesheetDto> getListTimesheet(String bonusPaySettingCode) {
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimesheet> lstBonusPayTimesheet = bpTimesheetRepository.getListTimesheet(companyId,
				new BonusPaySettingCode(bonusPaySettingCode) );
		return lstBonusPayTimesheet.stream().map(c -> toBPTimesheetDto(companyId, bonusPaySettingCode, c))
				.collect(Collectors.toList());
	}
	
	public void checkUseArt(List<Boolean> lstuseArt){
		boolean checkUseExist = false;
		
		for (Boolean useArt : lstuseArt) {
			if(useArt){
				checkUseExist=useArt;
			}
		}
		if(!checkUseExist){
			throw new BusinessException("Msg_34");
		}
		
	}

	private BPTimesheetDto toBPTimesheetDto(String companyId, String bonusPaySettingCode,
			BonusPayTimesheet bonusPayTimesheet) {
		return new BPTimesheetDto(companyId, bonusPayTimesheet.getTimeSheetId(), bonusPayTimesheet.getUseAtr().value,
				bonusPaySettingCode, bonusPayTimesheet.getTimeItemId(),
				bonusPayTimesheet.getStartTime().v().intValue(), bonusPayTimesheet.getEndTime().v().intValue(),
				bonusPayTimesheet.getRoundingTimeAtr().value, bonusPayTimesheet.getRoundingAtr().value);

	}

}
