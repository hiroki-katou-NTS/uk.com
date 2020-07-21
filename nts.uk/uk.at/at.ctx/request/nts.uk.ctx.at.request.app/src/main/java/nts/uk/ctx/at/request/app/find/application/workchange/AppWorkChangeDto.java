package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.ReflectionStatusDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@Setter
@Getter
public class AppWorkChangeDto extends ApplicationDto{
	
	/**
	 * 直行区分
	 */
	private int straightGo;
	
	/**
	 * 直帰区分
	 */
	private int straightBack;
	
	/**
	 * 勤務種類コード
	 */
	private String opWorkTypeCD;
	
	/**
	 * 就業時間帯コード
	 */
	private String opWorkTimeCD;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimeZoneWithWorkNoDto> timeZoneWithWorkNoLst;
	
	
	public static AppWorkChangeDto fromDomain(AppWorkChange appWorkChange) {
		AppWorkChangeDto appWorkChange_NewDto =  new AppWorkChangeDto();
		appWorkChange_NewDto.setStraightGo(appWorkChange.getStraightGo().value);
		appWorkChange_NewDto.setStraightBack(appWorkChange.getStraightBack().value);
		appWorkChange_NewDto.setOpWorkTypeCD(appWorkChange.getOpWorkTypeCD().isPresent() ? appWorkChange.getOpWorkTypeCD().get().v() : null );
		appWorkChange_NewDto.setOpWorkTimeCD(appWorkChange.getOpWorkTimeCD().isPresent() ? appWorkChange.getOpWorkTimeCD().get().v(): null );
		
		appWorkChange_NewDto.setTimeZoneWithWorkNoLst(appWorkChange.getTimeZoneWithWorkNoLst().stream().map(item -> TimeZoneWithWorkNoDto.fromDomain(item)).collect(Collectors.toList()));
		return appWorkChange_NewDto;
	}
	public AppWorkChange toDomain() {
		return new AppWorkChange(
				NotUseAtr.valueOf(this.straightGo),
				NotUseAtr.valueOf(this.straightBack),
				Optional.ofNullable(new WorkTypeCode(this.opWorkTypeCD)),
				Optional.ofNullable(new WorkTimeCode(this.opWorkTimeCD)),
				CollectionUtil.isEmpty(timeZoneWithWorkNoLst) ? Collections.emptyList() : timeZoneWithWorkNoLst.stream().map(item -> item.toDomain()).collect(Collectors.toList()),
				null);
	}
	public AppWorkChange toDomain(Application app) {
		return new AppWorkChange(
				NotUseAtr.valueOf(this.straightGo),
				NotUseAtr.valueOf(this.straightBack),
				Optional.ofNullable(new WorkTypeCode(this.opWorkTypeCD)),
				Optional.ofNullable(new WorkTimeCode(this.opWorkTimeCD)),
				CollectionUtil.isEmpty(timeZoneWithWorkNoLst) ? Collections.emptyList() : timeZoneWithWorkNoLst.stream().map(item -> item.toDomain()).collect(Collectors.toList()),
				app);
	}
	public AppWorkChangeDto(int version, String appID, int prePostAtr, String employeeID, int appType, String appDate,
			String enteredPerson, String inputDate, ReflectionStatusDto reflectionStatus, Integer opStampRequestMode,
			String opReversionReason, String opAppStartDate, String opAppEndDate, String opAppReason,
			Integer opAppStandardReasonCD) {
		super(version, appID, prePostAtr, employeeID, appType, appDate, enteredPerson, inputDate, reflectionStatus,
				opStampRequestMode, opReversionReason, opAppStartDate, opAppEndDate, opAppReason, opAppStandardReasonCD);
	}
	public AppWorkChangeDto() {
		super();
	}
}
