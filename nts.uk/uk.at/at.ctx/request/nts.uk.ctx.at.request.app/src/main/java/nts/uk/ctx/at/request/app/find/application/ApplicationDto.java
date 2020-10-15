package nts.uk.ctx.at.request.app.find.application;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationDto {
	
	@Setter
	private int version;
	
	/**
	 * ID
	 */
	private String appID;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 申請者
	 */
	private String employeeID;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 申請日
	 */
	private String appDate;
	
	/**
	 * 入力者
	 */
	private String enteredPerson;
	
	/**
	 * 入力日
	 */
	private String inputDate;
	
	/**
	 * 反映状態
	 */
	private ReflectionStatusDto reflectionStatus;
	
	/**
	 * 打刻申請モード
	 */
	private Integer opStampRequestMode;
	
	/**
	 * 差戻し理由
	 */
	private String opReversionReason;
	
	/**
	 * 申請開始日
	 */
	private String opAppStartDate;
	 
	/**
	 * 申請終了日
	 */
	private String opAppEndDate;
	
	/**
	 * 申請理由
	 */
	private String opAppReason;
	
	/**
	 * 定型理由
	 */
	private Integer opAppStandardReasonCD;
	
	public static ApplicationDto fromDomain(Application application) {
		return new ApplicationDto(
				application.getVersion(), 
				application.getAppID(), 
				application.getPrePostAtr().value, 
				application.getEmployeeID(), 
				application.getAppType().value, 
				application.getAppDate().getApplicationDate().toString(), 
				application.getEnteredPersonID(), 
				application.getInputDate().toString(), 
				ReflectionStatusDto.fromDomain(application.getReflectionStatus()), 
				application.getOpStampRequestMode().map(x -> x.value).orElse(null), 
				application.getOpReversionReason().map(x -> x.v()).orElse(null), 
				application.getOpAppStartDate().map(x -> x.getApplicationDate().toString()).orElse(null), 
				application.getOpAppEndDate().map(x -> x.getApplicationDate().toString()).orElse(null), 
				application.getOpAppReason().map(x -> x.v()).orElse(null), 
				application.getOpAppStandardReasonCD().map(x -> x.v()).orElse(null));
	}
	
	public Application toDomain() {
		Application application = new Application(
				version,
				appID, 
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
				employeeID, 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				new ApplicationDate(GeneralDate.fromString(appDate, "yyyy/MM/dd")), 
				enteredPerson, 
				GeneralDateTime.fromString(inputDate, "yyyy/MM/dd HH:mm:ss"), 
				reflectionStatus.toDomain());
		if(opStampRequestMode != null) {
			application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(opStampRequestMode, StampRequestMode.class)));
		}
		if(!StringUtils.isBlank(opReversionReason)) {
			application.setOpReversionReason(Optional.of(new ReasonForReversion(opReversionReason)));
		}
		if(!StringUtils.isBlank(opAppStartDate)) {
			application.setOpAppStartDate(Optional.of(new ApplicationDate(GeneralDate.fromString(opAppStartDate, "yyyy/MM/dd"))));
		}
		if(!StringUtils.isBlank(opAppEndDate)) {
			application.setOpAppEndDate(Optional.of(new ApplicationDate(GeneralDate.fromString(opAppEndDate, "yyyy/MM/dd"))));
		}
		if(!StringUtils.isBlank(opAppReason)) {
			application.setOpAppReason(Optional.of(new AppReason(opAppReason)));
		}
		if(opAppStandardReasonCD != null) {
			application.setOpAppStandardReasonCD(Optional.of(new AppStandardReasonCode(opAppStandardReasonCD)));
		}
		
		return application;
	}
	
}
