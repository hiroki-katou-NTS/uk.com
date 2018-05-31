package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private Integer startTime;
	/**
	 * 完了時間
	 */
	private Integer endTime;
	/**
	 * 申請時間
	 */
	private Integer applicationTime;
	
	private int errorCode;
	
	public static OvertimeInputDto fromDomain(OverTimeInput overTimeInput){
		return new OvertimeInputDto(
				overTimeInput.getCompanyID(), 
				overTimeInput.getAppID(), 
				overTimeInput.getAttendanceType().value, 
				overTimeInput.getFrameNo(), 
				overTimeInput.getTimeItemTypeAtr().value, 
				"", 
				overTimeInput.getStartTime() == null ? null : overTimeInput.getStartTime().v(), 
				overTimeInput.getEndTime() == null? null: overTimeInput.getEndTime().v(), 
				overTimeInput.getApplicationTime()== null ? null: overTimeInput.getApplicationTime().v(),0);
	}
}
