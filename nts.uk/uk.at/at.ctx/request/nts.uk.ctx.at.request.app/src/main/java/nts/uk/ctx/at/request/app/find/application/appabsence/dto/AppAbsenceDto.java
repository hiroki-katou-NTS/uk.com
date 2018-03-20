package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.dom.application.appabsence.AbsenceWorkType;

@Data
public class AppAbsenceDto {
	/**
	 * version
	 */
	private long version;
	/**
	 * application
	 */
	private ApplicationDto_New application;
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * employeeID
	 */
	private String employeeID;
	/**
	 * employeeName
	 */
	private String employeeName;
	/**
	 * 休暇種類
	 */
	private int holidayAppType;
	/**
	 * 勤務種類コード
	 */
	private String workTypeCode;
	
	/**
	 * workTypeName
	 */
	private String workTypeName;
	/**
	 * 就業時間帯コード
	 */
	private String workTimeCode;
	
	/**
	 * workTimeName
	 */
	private String workTimeName;
	/**
	 * 半日の組み合わせを表示する
	 */
	public boolean halfDayFlg;
	/**
	 * 就業時間帯変更する
	 */
	private boolean changeWorkHourFlg;
	/**
	 * 終日半日休暇区分
	 */
	private Integer allDayHalfDayLeaveAtr;
	/**
	 * 開始時刻
	 */
	private Integer startTime1;
	/**
	 * 終了時刻
	 */
	private Integer endTime1;
	/**
	 * 開始時刻2
	 */
	private Integer startTime2;
	/**
	 * 終了時刻2
	 */
	private Integer endTime2;
	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestionDto> subTargetDigestions;
	
	/**
	 * 消化対象振休管理
	 */
	private List<SubDigestionDto> subDigestions;
	/**
	 * 特別休暇申請
	 */
	private AppForSpecLeaveDto appForSpecLeave;
	
	/**
	 * 時間消化申請
	 */
	private AppTimeDigestDto appTimeDigest;
	
	/**
	 * manualSendMailFlg
	 */
	private boolean manualSendMailFlg;
	/**
	 * applicationReasonDtos
	 */
	private List<ApplicationReasonDto> applicationReasonDtos;
	
	/**
	 * prePostFlg
	 */
	private boolean prePostFlg;
	
	private List<Integer> holidayAppTypes;
	
	/**
	 * workTypes
	 */
	private List<AbsenceWorkType> workTypes;
	
	/**
	 * workTimeCodes
	 */
	private List<String> workTimeCodes;
}
