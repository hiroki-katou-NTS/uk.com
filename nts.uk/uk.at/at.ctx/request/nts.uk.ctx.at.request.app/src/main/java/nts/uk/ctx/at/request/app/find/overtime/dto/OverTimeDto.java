package nts.uk.ctx.at.request.app.find.overtime.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;

@Data
public class OverTimeDto {
	/**
	 * application
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
	 * 申請者
	 */
	private String employeeName;
	/**
	 * 残業区分
	 */
	private int overtimeAtr;
	/**
	 * 残業申請時間設定
	 */
	private List<OvertimeInputDto> overTimeInput;
	/**
	 *  事前事後区分表示 
	 */
	private int displayPrePostFlg;
	/**
	 * workType
	 */
	private WorkTypeOvertime workType;
	
	/**
	 * siftType
	 */
	private SiftType siftType;

	/**
	 * 勤務時間From1
	 */
	private int workClockFrom1;
	/**
	 * 勤務時間To1
	 */
	private int workClockTo1;
	/**
	 * 勤務時間From2
	 */
	private int workClockFrom2;
	/**
	 * 勤務時間To2
	 */
	private int workClockTo2;
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
	private int flexExessTime;
	/**
	 * 就業時間外深夜時間
	 */
	private int overTimeShiftNight;
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
	
	private int appOvertimeNightFlg;
	
}
