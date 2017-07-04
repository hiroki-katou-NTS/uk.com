package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPSettingFinder {
	@Inject
	private BPSettingRepository bpSettingRepository;

	public List<BPSettingDto> getAllBonusPaySetting() {
		String companyId = AppContexts.user().companyId();
		List<BonusPaySetting> lstBonusPaySetting = this.bpSettingRepository.getAllBonusPaySetting(companyId);
		return lstBonusPaySetting.stream().map(c -> toBPSettingDto(c)).collect(Collectors.toList());
	}

	private BPSettingDto toBPSettingDto(BonusPaySetting bonusPaySetting) {
		return new BPSettingDto(bonusPaySetting.getCompanyId().toString(), bonusPaySetting.getCode().toString(),
				bonusPaySetting.getName().toString());
	}
	public BPSettingDto getBonusPaySetting(String bonusPaySettingCode){
		String companyId = AppContexts.user().companyId();
		Optional<BonusPaySetting> bonusPaySetting = this.bpSettingRepository.getBonusPaySetting(companyId, new BonusPaySettingCode(bonusPaySettingCode));
		if(bonusPaySetting.isPresent()){
			BonusPaySetting bPaySetting = bonusPaySetting.get();
			
			return new BPSettingDto(companyId, bPaySetting.getCode().v(), bPaySetting.getName().v());
			
		}
		return null;
	}
	
	

	// public List<BPSettingDto> getAllBonusPaySetting() {
	// String companyId = AppContexts.user().companyId();
	// List<BonusPaySetting> lst =
	// bonusPaySettingService.getAllBonusPaySetting(companyId);
	// return lst.stream().map(c ->
	// toBPSettingDto(c)).collect(Collectors.toList());
	// }
	//
	// private BPSettingDto toBPSettingDto(BonusPaySetting bonusPaySetting) {
	// List<BonusPayTimesheet> lstBonusPayTimesheet =
	// bonusPaySetting.getLstBonusPayTimesheet();
	// List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet =
	// bonusPaySetting.getLstSpecBonusPayTimesheet();
	// List<BPTimesheetDto> lstBPTimesheetDto = lstBonusPayTimesheet.stream()
	// .map(c -> toBPTimesheetDto(bonusPaySetting.getCompanyId().toString(),
	// bonusPaySetting.getCode().toString(), c))
	// .collect(Collectors.toList());
	// List<SpecBPTimesheetDto> lstSpecBPTimesheetDto =
	// lstSpecBonusPayTimesheet.stream()
	// .map(c -> toSpecBPTimesheetDto(bonusPaySetting.getCompanyId().toString(),
	// bonusPaySetting.getCode().toString(), c))
	// .collect(Collectors.toList());
	// return new BPSettingDto(bonusPaySetting.getCompanyId().toString(),
	// bonusPaySetting.getCode().toString(),
	// bonusPaySetting.getName().toString(), lstBPTimesheetDto,
	// lstSpecBPTimesheetDto);
	// }
	//
	// private BPTimesheetDto toBPTimesheetDto(String companyId, String
	// bonusPaySettingCode,
	// BonusPayTimesheet bonusPayTimesheet) {
	// return new BPTimesheetDto(companyId, bonusPayTimesheet.getTimeSheetId(),
	// bonusPayTimesheet.getUseAtr().value,
	// bonusPaySettingCode, bonusPayTimesheet.getTimeItemId().toString(),
	// bonusPayTimesheet.getStartTime().minute(),
	// bonusPayTimesheet.getEndTime().minute(),
	// bonusPayTimesheet.getRoundingTimeAtr().value,
	// bonusPayTimesheet.getRoundingAtr().value);
	//
	// }
	//
	// private SpecBPTimesheetDto toSpecBPTimesheetDto(String companyId, String
	// bonusPaySettingCode,
	// SpecBonusPayTimesheet specBonusPayTimesheet) {
	// return new SpecBPTimesheetDto(companyId,
	// specBonusPayTimesheet.getTimeSheetId(),
	// specBonusPayTimesheet.getUseAtr().value, bonusPaySettingCode,
	// specBonusPayTimesheet.getTimeItemId().toString(),
	// specBonusPayTimesheet.getStartTime().minute(),
	// specBonusPayTimesheet.getEndTime().minute(),
	// specBonusPayTimesheet.getRoundingTimeAtr().value,
	// specBonusPayTimesheet.getRoundingAtr().value,
	// specBonusPayTimesheet.getDateCode());
	// }
}
