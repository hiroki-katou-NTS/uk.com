package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AgreeOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.AppOvertimeReference;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeDto {
	
	private Long version;
	/**
	 * application
	 * 
	 */
	private ApplicationDto application;
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
	private String overtimeInstructInformation;
	/**
	 * displayOvertimeInstructInforFlg
	 */
	private boolean displayOvertimeInstructInforFlg;
	/**
	 * 
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
	 * 残業区分
	 */
	private int overtimeAtr;
	/**
	 * 残業申請時間設定
	 */
	private List<OvertimeInputDto> overTimeInputs;
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
	
	/** siftType */
	private SiftType siftType;
	
	/**
	 * siftTypes
	 */
	private List<String> siftTypes;

	/**
	 * 勤務時間From1
	 */
	private Integer workClockFrom1;
	/**
	 * 勤務時間To1
	 */
	private Integer workClockTo1;
	/**
	 * 勤務時間From2
	 */
	private Integer workClockFrom2;
	/**
	 * 勤務時間To2
	 */
	private Integer workClockTo2;
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
	 * フレックス超過時間
	 */
	private Integer flexExessTime;
	/**
	 * 就業時間外深夜時間
	 */
	private Integer overTimeShiftNight;
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
	 * appOvertimeNightFlg
	 */
	private int appOvertimeNightFlg;
	
	
	
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
	private PreAppOvertimeDto preAppOvertimeDto;
	
	/**
	 * appOvertimeReference
	 */
	private AppOvertimeReference appOvertimeReference;
	
	private boolean performanceDisplayAtr;
	
	private boolean preDisplayAtr;
	/**
	 * flexFLag
	 */
	private boolean flexFLag;
	
	/**
	 * prePostCanChangeFlg
	 */
	private boolean prePostCanChangeFlg;
	
	private List<CaculationTime> caculationTimes;
	
	private boolean resultCaculationTimeFlg;
	private boolean workTypeChangeFlg;
	
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
	
	/**
	 * 控除時間帯(丸め付き)
	 */
	private List<DeductionTimeDto> timezones ;
	
	private boolean enableOvertimeInput;
	/** 申請理由が必須 */
	private boolean requireAppReasonFlg;
	
	/** 就業時間帯 */
	private Integer worktimeStart;
	private Integer worktimeEnd;
	
	private String enteredPersonName;
	
	private PreActualColorResult preActualColorResult;
	private int performanceExcessAtr;
	private int preExcessDisplaySetting;
	/**
	 * 事前申請内容
	 */
	private ApplicationDto opAppBefore;
	
	/** 事前申請状態 */
	private boolean beforeAppStatus;
	
	/**
	 * 実績状態
	 */
	private int actualStatus;
	/**
	 * 実績内容
	 */
	private String workTypeActual; // 出勤時刻
	private String workTimeActual; // 退勤時刻
	private Integer startTimeActual; // 勤務種類
	private Integer endTimeActual; // 就業時間帯
	private List<OvertimeColorCheck> actualLst;
	
	/**
	 * 申請共通設定
	 */
	private OvertimeSettingDataDto overtimeSettingDataDto;
	
	/**
	 * List<勤怠種類, 枠NO, 計算入力差異, 事前申請超過, 実績超過>
	 */
	public List<OvertimeColorCheck> resultLst;
	
	public static OverTimeDto fromDomain(AppOverTime appOverTime){
//		return new OverTimeDto(
//				appOverTime.getVersion(),
//				ApplicationDto_New.fromDomain(appOverTime.getApplication()), 
//				appOverTime.getCompanyID(), 
//				appOverTime.getAppID(), 
//				"", 
//				false, 
//				"", 
//				"", 
//				null,
//				appOverTime.getOverTimeAtr().value, 
//				CollectionUtil.isEmpty(appOverTime.getOverTimeInput())
//					? Collections.emptyList() 
//					: appOverTime.getOverTimeInput().stream().map(x -> OvertimeInputDto.fromDomain(x)).collect(Collectors.toList()), 
//				0, 
//				appOverTime.getWorkTypeCode() == null ? null : new WorkTypeOvertime(appOverTime.getWorkTypeCode().v(), ""),
//				Collections.emptyList(),
//				appOverTime.getSiftCode() == null ? null : new SiftType(appOverTime.getSiftCode().v(),""),
//				Collections.emptyList(),
//				appOverTime.getWorkClockFrom1(), 
//				appOverTime.getWorkClockTo1(),  
//				appOverTime.getWorkClockFrom2(), 
//				appOverTime.getWorkClockTo2(), 
//				"", 
//				appOverTime.getDivergenceReason(), 
//				0, 
//				appOverTime.getFlexExessTime(), 
//				appOverTime.getOverTimeShiftNight(), 
//				false, 
//				false, 
//				false, 
//				Collections.emptyList(), 
//				false, 
//				false, 
//				Collections.emptyList(),
//				false, 
//				false, 
//				0, 
//				false,
//				false,
//				false,
//				false,
//				false, 
//				null,
//				null,
//				false,
//				false,
//				false,
//				false,
//				null,
//				false,
//				false,
//				false,
//				false,
//				null,
//				null,
//				null,
//				Collections.emptyList(),
//				false,
//				false,
//				0,
//				0,
//				"",
//				null,
//				0,
//				0,
//				null,
//				false,
//				0,
//				null,
//				null,
//				null, 
//				null,
//				Collections.emptyList(),
//				null,
//				Collections.emptyList());
		return null;
	}
	
}
