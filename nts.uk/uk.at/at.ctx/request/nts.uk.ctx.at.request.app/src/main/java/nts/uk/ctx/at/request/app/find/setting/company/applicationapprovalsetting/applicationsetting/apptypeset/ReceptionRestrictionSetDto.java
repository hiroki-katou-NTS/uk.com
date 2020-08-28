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
	 * 申請種類
	 */
	private int appType;

	/**
	 * 未来日許可しない
	 */
	private boolean allowFutureDay;

	/**
	 * 日数
	 */
	private int dateBeforehandRestrictions;

	/**
	 * 利用する
	 */
	private boolean useAtr;

	/**
	 * チェック方法
	 */
	private Integer methodCheck;

	/**
	 * 時刻（早出残業）
	 */
	private Integer earlyOvertime;

	/**
	 * 時刻（通常残業）
	 */
	private Integer normalOvertime;

	/**
	 * 時刻（早出残業・通常残業）
	 */
	private Integer earlyNormalOvertime;

	public ReceptionRestrictionSetting toDomain() {
		if (appType == ApplicationType.OVER_TIME_APPLICATION.value)
			return new ReceptionRestrictionSetting(
					OTAppBeforeAccepRestric.createNew(methodCheck, dateBeforehandRestrictions, useAtr, earlyOvertime, normalOvertime, earlyNormalOvertime),
					new AfterhandRestriction(allowFutureDay),
					null,
					EnumAdaptor.valueOf(appType, ApplicationType.class));
		else
			return new ReceptionRestrictionSetting(
					null,
					new AfterhandRestriction(allowFutureDay),
					BeforehandRestriction.createNew(dateBeforehandRestrictions, useAtr),
					EnumAdaptor.valueOf(appType, ApplicationType.class));
	}
	
	public static ReceptionRestrictionSetDto fromDomain(ReceptionRestrictionSetting domain) {
		if (domain.getAppType() == ApplicationType.OVER_TIME_APPLICATION)
			return new ReceptionRestrictionSetDto(
					domain.getAppType().value,
					domain.getAfterhandRestriction().isAllowFutureDay(),
					domain.getOtAppBeforeAccepRestric().get().getDateBeforehandRestrictions().value,
					domain.getOtAppBeforeAccepRestric().get().isToUse(),
					domain.getOtAppBeforeAccepRestric().get().getMethodCheck().value,
					domain.getOtAppBeforeAccepRestric().get().getOpEarlyOvertime().map(AttendanceClock::v).orElse(null),
					domain.getOtAppBeforeAccepRestric().get().getOpNormalOvertime().map(AttendanceClock::v).orElse(null),
					domain.getOtAppBeforeAccepRestric().get().getOpEarlyNormalOvertime().map(AttendanceClock::v).orElse(null)
					);
		else
			return new ReceptionRestrictionSetDto(
					domain.getAppType().value,
					domain.getAfterhandRestriction().isAllowFutureDay(),
					domain.getBeforehandRestriction().get().getDateBeforehandRestrictions().value,
					domain.getBeforehandRestriction().get().isToUse(),
					null,
					null,
					null,
					null
			);
	}
}
