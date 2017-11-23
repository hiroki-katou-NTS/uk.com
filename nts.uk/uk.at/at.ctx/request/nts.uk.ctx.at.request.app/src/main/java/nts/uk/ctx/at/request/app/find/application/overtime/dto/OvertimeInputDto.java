package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.Data;

@Data
public class OvertimeInputDto {
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
	private int startTime;
	/**
	 * 完了時間
	 */
	private int endTime;
	/**
	 * 申請時間
	 */
	private int applicationTime;
}
