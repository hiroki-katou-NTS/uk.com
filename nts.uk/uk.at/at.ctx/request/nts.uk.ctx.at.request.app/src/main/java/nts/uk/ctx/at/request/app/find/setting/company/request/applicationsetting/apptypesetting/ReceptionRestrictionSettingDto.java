package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;

@AllArgsConstructor
@NoArgsConstructor
public class ReceptionRestrictionSettingDto {
	
	/**
	 * 申請種類
	 */
	public int appType;
	
	/**
	 * 事前の受付制限
	 */
	public BeforehandRestrictionDto beforehandRestriction;
	
	/**
	 * 事後の受付制限
	 */
	public AfterhandRestrictionDto afterhandRestriction;
	
	public static ReceptionRestrictionSettingDto fromDomain(ReceptionRestrictionSetting receptionRestrictionSetting) {
		ReceptionRestrictionSettingDto receptionRestrictionSettingDto = new ReceptionRestrictionSettingDto();
		receptionRestrictionSettingDto.appType = receptionRestrictionSetting.getAppType().value;
		receptionRestrictionSettingDto.beforehandRestriction = BeforehandRestrictionDto.fromDomain(receptionRestrictionSetting.getBeforehandRestriction());
		receptionRestrictionSettingDto.afterhandRestriction = AfterhandRestrictionDto.fromDomain(receptionRestrictionSetting.getAfterhandRestriction());
		return receptionRestrictionSettingDto;
	}
}
