package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementUnitSettingDto;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 単位設定 screen
 *
 */
@Stateless
public class AgreementUnitSettingFinder {
	
	@Inject
	private AgreementUnitSettingRepository agreementUnitSettingRepository;

	public AgreementUnitSettingDto find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementUnitSettingDto agreementUnitSettingDto = new AgreementUnitSettingDto();
		
		Optional<AgreementUnitSetting> agreementUnitSetting = this.agreementUnitSettingRepository.find(companyId);
		if(agreementUnitSetting.isPresent()){
			agreementUnitSettingDto.setClassificationUseAtr(agreementUnitSetting.get().getClassificationUseAtr().value);
			agreementUnitSettingDto.setEmploymentUseAtr(agreementUnitSetting.get().getEmploymentUseAtr().value);
			agreementUnitSettingDto.setWorkPlaceUseAtr(agreementUnitSetting.get().getWorkPlaceUseAtr().value);
		} else {
			return null;
		}
		
		return agreementUnitSettingDto;
	}
}
