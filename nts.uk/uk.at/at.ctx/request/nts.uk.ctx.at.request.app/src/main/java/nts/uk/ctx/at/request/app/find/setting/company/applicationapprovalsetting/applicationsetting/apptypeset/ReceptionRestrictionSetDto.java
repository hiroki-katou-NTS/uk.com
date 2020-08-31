package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AfterhandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.BeforehandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.OTAppBeforeAccepRestric;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
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
		if (receptionRestrictionSetting == null) return null;
		return new ReceptionRestrictionSetDto(
				receptionRestrictionSetting.getOtAppBeforeAccepRestric().map(OTAppBeforeAccepRestricDto::fromDomain).orElse(null),
				receptionRestrictionSetting.getAfterhandRestriction(),
				receptionRestrictionSetting.getBeforehandRestriction().map(BeforehandRestrictionDto::fromDomain).orElse(null),
				receptionRestrictionSetting.getAppType().value);
	}

	public ReceptionRestrictionSetting toDomain() {
		return new ReceptionRestrictionSetting(
				appType == ApplicationType.OVER_TIME_APPLICATION.value ? otAppBeforeAccepRestric.toDomain() : null,
				afterhandRestriction,
				appType == ApplicationType.OVER_TIME_APPLICATION.value ? null : beforehandRestriction.toDomain(),
				EnumAdaptor.valueOf(appType, ApplicationType.class));
	}
}
