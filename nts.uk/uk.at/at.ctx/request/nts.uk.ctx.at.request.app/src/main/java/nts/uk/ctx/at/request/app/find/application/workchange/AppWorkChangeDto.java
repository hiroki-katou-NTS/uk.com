package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
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
	
	public static AppWorkChangeDto fromDomain(AppWorkChange appWorkChange) {
		AppWorkChangeDto appWorkChange_NewDto =  new AppWorkChangeDto();
		
//		appWorkChange_NewDto.setVersion(appWorkChange.getVersion()); 
//		appWorkChange_NewDto.setAppID(appWorkChange.getAppID()); 
//		appWorkChange_NewDto.setPrePostAtr(appWorkChange.getPrePostAtr().value);
//		appWorkChange_NewDto.setEmployeeID(appWorkChange.getEmployeeID()); 
//		appWorkChange_NewDto.setAppType(2); 
//		appWorkChange_NewDto.setAppDate(appWorkChange.getAppDate().getApplicationDate().toString()); 
//		appWorkChange_NewDto.setEnteredPerson(appWorkChange.getEnteredPerson()); 
//		appWorkChange_NewDto.setInputDate(appWorkChange.getInputDate().toString()); 
////		ReflectionStatusDto.fromDomain(application.getReflectionStatus()), 
//		appWorkChange_NewDto.setOpStampRequestMode(appWorkChange.getOpStampRequestMode().map(x -> x.value).orElse(null));
//		appWorkChange_NewDto.setOpReversionReason(appWorkChange.getOpReversionReason().map(x -> x.v()).orElse(null)); 
//		appWorkChange_NewDto.setOpAppStartDate(appWorkChange.getOpAppStartDate().map(x -> x.getApplicationDate().toString()).orElse(null)); 
//		appWorkChange_NewDto.setOpAppEndDate(appWorkChange.getOpAppEndDate().map(x -> x.getApplicationDate().toString()).orElse(null));
//		appWorkChange_NewDto.setOpAppReason(appWorkChange.getOpAppReason().map(x -> x.v()).orElse(null));
//		appWorkChange_NewDto.setOpAppStandardReasonCD(appWorkChange.getOpAppStandardReasonCD().map(x -> x.v()).orElse(null));
//		
		//chua lam xu ly lay du lieu appReflect in repository
//				(AppWorkChangeDto)ApplicationDto.fromDomain(appWorkChange);
		appWorkChange_NewDto.setStraightGo(appWorkChange.getStraightGo().value);
		appWorkChange_NewDto.setStraightBack(appWorkChange.getStraightBack().value);
		appWorkChange_NewDto.setOpWorkTypeCD(appWorkChange.getOpWorkTypeCD().isPresent() ? appWorkChange.getOpWorkTypeCD().get().v() : null );
		appWorkChange_NewDto.setOpWorkTimeCD(appWorkChange.getOpWorkTimeCD().isPresent() ? appWorkChange.getOpWorkTimeCD().get().v(): null );
		
		appWorkChange_NewDto.setTimeZoneWithWorkNoLst(!CollectionUtil.isEmpty(appWorkChange.getTimeZoneWithWorkNoLst()) ? appWorkChange.getTimeZoneWithWorkNoLst().stream().map(item -> TimeZoneWithWorkNoDto.fromDomain(item)).collect(Collectors.toList()) : Collections.emptyList());
		appWorkChange_NewDto.setAppID(appWorkChange.getAppID());
		return appWorkChange_NewDto;
	}

	public AppWorkChange toDomain(Application app) {
		return new AppWorkChange(
				NotUseAtr.valueOf(this.straightGo),
				NotUseAtr.valueOf(this.straightBack),
				StringUtils.isBlank(this.opWorkTypeCD) ? Optional.empty() : Optional.ofNullable(new WorkTypeCode(this.opWorkTypeCD)),
				StringUtils.isBlank(this.opWorkTimeCD) ? Optional.empty() : Optional.ofNullable(new WorkTimeCode(this.opWorkTimeCD)) ,
				CollectionUtil.isEmpty(timeZoneWithWorkNoLst) ? Collections.emptyList() : timeZoneWithWorkNoLst.stream().map(item -> item.toDomain()).collect(Collectors.toList()),
				app);
	}
	
	
	
}
