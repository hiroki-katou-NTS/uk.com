package nts.uk.ctx.at.function.dom.alarm.sendemail;

import lombok.Data;
/**
 * 
 * @author thuongtv
 *
 */

@Data
public class ValueExtractAlarmDto {
	private String guid;
	private String workplaceID;
	private String hierarchyCd;
	private String workplaceName;
	private String employeeID;
	private String employeeCode;
	private String employeeName;
	private String alarmValueDate;
	private Integer category;
	private String categoryName;
	private String alarmItem;
	private String alarmValueMessage;
	private String comment;
	public ValueExtractAlarmDto(String guid, String workplaceID, String hierarchyCd, String workplaceName,
			String employeeID, String employeeCode, String employeeName, String alarmValueDate, Integer category,
			String categoryName, String alarmItem, String alarmValueMessage, String comment) {
		super();
		this.guid = guid;
		this.workplaceID = workplaceID;
		this.hierarchyCd = hierarchyCd;
		this.workplaceName = workplaceName;
		this.employeeID = employeeID;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.alarmValueDate = alarmValueDate;
		this.category = category;
		this.categoryName = categoryName;
		this.alarmItem = alarmItem;
		this.alarmValueMessage = alarmValueMessage;
		this.comment = comment;
	}
	
	
}
