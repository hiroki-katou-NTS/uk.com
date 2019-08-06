package nts.uk.ctx.at.request.dom.application.overtime.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaculationTime {
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
	 * 勤怠種類
	 */
	private int attendanceID;
	/**
	 * 勤怠項目NO
	 */
	private int frameNo;
	
	/**
	 * timeItemTypeAtr
	 */
	private int timeItemTypeAtr;
	
	/**
	 * 勤怠項目Name
	 */
	private String frameName;
	
	/**
	 * 開始時間
	 */
	private Integer applicationTime;
	/**
	 * 完了時間
	 */
	private String preAppTime;
	/**
	 * 申請時間
	 */
	private String caculationTime;
	
	/**
	 * errorCode
	 */
	private int errorCode;
	/**
	 * displayOvertimeHour
	 */
	private boolean displayOvertimeHour;
	
	/** 事前申請超過 */
	private boolean preAppExceedState;
	
	/** 実績超過 */
	private boolean actualExceedState;
}
