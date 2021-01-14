package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;

@Builder
@Data
public class ApplicationDto {
	private int version;

	/**
	 * ID
	 */
	private String appID;

	/**
	 * 事前事後区分
	 */
	private Integer prePostAtr;

	/**
	 * 申請者
	 */
	private String employeeID;

	/**
	 * 申請種類
	 */
	private Integer appType;

	/**
	 * 申請日
	 */
	private GeneralDate appDate;

	/**
	 * 入力者
	 */
	private String enteredPersonID;

	/**
	 * 入力日
	 */
	private GeneralDateTime inputDate;

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
	private GeneralDate opAppStartDate;

	/**
	 * 申請終了日
	 */
	private GeneralDate opAppEndDate;

	/**
	 * 申請理由
	 */
	private String opAppReason;

	/**
	 * 定型理由
	 */
	private Integer opAppStandardReasonCD;
	
	
	public static ApplicationDto toDto(Application domain) {
		return ApplicationDto.builder()
				.appID(domain.getAppID())
				.prePostAtr(domain.getPrePostAtr().value)
				.employeeID(domain.getEmployeeID())
				.appType(domain.getAppType().value)
				.enteredPersonID(domain.getEnteredPersonID())
				.inputDate(domain.getInputDate())
				.reflectionStatus(ReflectionStatusDto.toDto(domain.getReflectionStatus()))
				.opStampRequestMode(domain.getOpStampRequestMode().map(v -> v.value).orElse(null))
				.opReversionReason(domain.getOpReversionReason().map(v -> v.v()).orElse(null))
				.opAppStartDate(domain.getOpAppStartDate().map(v -> v.getApplicationDate()).orElse(null))
				.opAppEndDate(domain.getOpAppEndDate().map(v -> v.getApplicationDate()).orElse(null))
				.opAppReason(domain.getOpAppReason().map(v -> v.v()).orElse(null))
				.opAppStandardReasonCD(domain.getOpAppStandardReasonCD().map(v -> v.v()).orElse(null))
				.build();
	}
}
