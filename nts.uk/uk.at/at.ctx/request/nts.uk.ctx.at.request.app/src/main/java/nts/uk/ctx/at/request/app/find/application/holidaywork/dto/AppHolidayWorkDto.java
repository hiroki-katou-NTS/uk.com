package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOvertimeDetailDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHolidayWorkPreAndReferDto;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppHolidayWorkDto {

	private Long version;
	/**
	 * application
	 */
	private ApplicationDto_New application;
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * overtimeInstructInformation
	 */
	private String holidayInstructInformation;
	/**
	 * displayOvertimeInstructInforFlg
	 */
	private boolean displayHolidayInstructInforFlg;
	/**
	 * employeeID
	 */
	private String employeeID;
	
	/**
	 * 申請者
	 */
	private String employeeName;
	/**
	 * employees
	 */
	private List<EmployeeOvertimeDto> employees;
	/**
	 * 残業申請時間設定
	 */
	private List<HolidayWorkInputDto> holidayWorkInputDtos;
	/**
	 *  事前事後区分表示 
	 */
	private int displayPrePostFlg;
	/**
	 * workType
	 */
	private WorkTypeOvertime workType;
	
	/**
	 * workTypes
	 */
	private List<String> workTypes;
	
	/** workTime */
	private SiftType workTime;
	
	/**
	 * siftTypes
	 */
	private List<String> workTimes;

	/**
	 * 勤務時間Start1
	 */
	private Integer workClockStart1;
	/**
	 * 勤務時間End1
	 */
	private Integer workClockEnd1;
	/**
	 * 勤務時間Start2
	 */
	private Integer workClockStart2;
	/**
	 * 勤務時間End2
	 */
	private Integer workClockEnd2;
	/**
	 * goAtr1
	 */
	private int goAtr1;
	/**
	 * backAtr1
	 */
	private int backAtr1;
	/**
	 * goAtr2
	 */
	private int goAtr2;
	/**
	 * backAtr2
	 */
	private int backAtr2;
	/**
	 * 乖離定型理由
	 */
	private String divergenceReasonID;
	/**
	 * 乖離理由
	 */
	private String divergenceReasonContent;
	
	/**
	 * 計算残業時間
	 */
	private int calculationOverTime;
	/**
	 * 就業時間外深夜時間
	 */
	private Integer holidayShiftNight;
	/**
	 * 時刻計算利用
	 */
	private boolean displayCaculationTime;
	
	/**
	 * 休憩時間取得表示する
	 */
	private boolean displayRestTime;
	
	/**
	 * 加給時間を取得
	 */
	private boolean displayBonusTime;
	
	/**
	 * applicationReasonDtos
	 */
	private List<ApplicationReasonDto> applicationReasonDtos;
	/**
	 * typicalReasonDisplayFlg
	 */
	private boolean typicalReasonDisplayFlg;
	
	/**
	 * displayAppReasonContentFlg
	 */
	private boolean displayAppReasonContentFlg;
	
	/**
	 * divergenceReasonDtos
	 */
	private List<DivergenceReasonDto> divergenceReasonDtos;
	/**
	 * displayDivergenceReason
	 */
	private boolean displayDivergenceReasonForm;
	
	/**
	 * displayDivergenceReasonInput
	 */
	private boolean displayDivergenceReasonInput;
	
	/**
	 * holidayWorkNightFlg
	 */
	private int holidayWorkNightFlg;
	
	/**
	 * 参照ラベル
	 */
	private boolean referencePanelFlg;
	/**
	 * 事前申請ラベル
	 */
	private boolean preAppPanelFlg;
	/**
	 * allPreAppPanelFlg
	 */
	private boolean allPreAppPanelFlg;
	/**
	 * 時間外表示区分
	 */
	private boolean extratimeDisplayFlag;
	
	/**
	 * manualSendMailAtr
	 */
	private boolean manualSendMailAtr;
	
	/**
	 * preAppOvertimeDto
	 */
	private AppHolidayWorkPreAndReferDto preAppHolidayWorkDto;
	
	/**
	 * appOvertimeReference
	 */
	private AppHolidayWorkPreAndReferDto holidayWorkReferenceDto;
	
	/**
	 * prePostCanChangeFlg
	 */
	private boolean prePostCanChangeFlg;
	
	/**
	 * 承認処理時に自動でメールを送信する
	 */
	private boolean sendMailWhenApprovalFlg;
	/**
	 * 新規登録時に自動でメールを送信する
	 */
	private boolean sendMailWhenRegisterFlg;
	
	private AgreeOverTimeDto agreementTimeDto;
	
	private AppOvertimeDetailDto appOvertimeDetailDto;
	
	private Integer appOvertimeDetailStatus;
	
	public static AppHolidayWorkDto fromDomain(AppHolidayWork appHolidayWork){
		return new AppHolidayWorkDto(
				appHolidayWork.getVersion(),
				ApplicationDto_New.fromDomain(appHolidayWork.getApplication()), 
				appHolidayWork.getCompanyID(), 
				appHolidayWork.getAppID(), 
				"", 
				false, 
				"", 
				"",
				null,
				CollectionUtil.isEmpty(appHolidayWork.getHolidayWorkInputs())
					? Collections.emptyList() 
					: appHolidayWork.getHolidayWorkInputs().stream().map(x -> HolidayWorkInputDto.fromDomain(x)).collect(Collectors.toList()), 
				0, 
				new WorkTypeOvertime(appHolidayWork.getWorkTypeCode() == null ? null : appHolidayWork.getWorkTypeCode().v(), ""),
				Collections.emptyList(),
				new SiftType(appHolidayWork.getWorkTimeCode() == null ? null : appHolidayWork.getWorkTimeCode().v(),""),
				Collections.emptyList(),
				appHolidayWork.getWorkClock1().getStartTime() == null ? null : appHolidayWork.getWorkClock1().getStartTime().v(), 
				appHolidayWork.getWorkClock1().getEndTime() == null ? null : appHolidayWork.getWorkClock1().getEndTime().v(),  
				appHolidayWork.getWorkClock2().getStartTime()== null ? null : appHolidayWork.getWorkClock2().getStartTime().v(), 
				appHolidayWork.getWorkClock2().getEndTime() == null ? null : appHolidayWork.getWorkClock2().getEndTime().v(),
				appHolidayWork.getWorkClock1().getGoAtr().value,
				appHolidayWork.getWorkClock1().getBackAtr().value,
				appHolidayWork.getWorkClock2().getGoAtr().value,
				appHolidayWork.getWorkClock2().getBackAtr().value,
				"", 
				appHolidayWork.getDivergenceReason(), 
				0,
				appHolidayWork.getHolidayShiftNight(), 
				false, 
				false, 
				false, 
				Collections.emptyList(), 
				false, 
				false, 
				Collections.emptyList(),
				false, 
				false, 
				0, 
				false,
				false,
				false,
				false,
				false, 
				null,
				null,false, false, false,
				null,
				null, 
				null);
	}
	
}
