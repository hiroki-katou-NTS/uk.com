package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AfterhandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ReceptionRestrictionSetDto {
	/**
	 * 残業申請事前の受付制限
	 */
	private OTAppBeforeAccepRestricDto otAppBeforeAccepRestric;
	
	/**
	 * 事後の受付制限
	 */
	private AfterhandRestriction afterhandRestriction;
	
	/**
	 * 事前の受付制限
	 */
	private BeforehandRestrictionDto beforehandRestriction;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	public static ReceptionRestrictionSetDto fromDomain(ReceptionRestrictionSetting receptionRestrictionSetting) {
		return new ReceptionRestrictionSetDto(
				OTAppBeforeAccepRestricDto.fromDomain(receptionRestrictionSetting.getOtAppBeforeAccepRestric()), 
				receptionRestrictionSetting.getAfterhandRestriction(), 
				BeforehandRestrictionDto.fromDomain(receptionRestrictionSetting.getBeforehandRestriction()), 
				receptionRestrictionSetting.getAppType().value);
	}
	
	public ReceptionRestrictionSetting toDomain() {
		return new ReceptionRestrictionSetting(
				otAppBeforeAccepRestric.toDomain(), 
				afterhandRestriction, 
				beforehandRestriction.toDomain(), 
				EnumAdaptor.valueOf(appType, ApplicationType.class));
	}
}
