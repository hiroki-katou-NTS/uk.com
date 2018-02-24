package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class AlarmExtraValueWkReDto {

	/**社員ID*/
	private String employeeID;
	/**職場ID*/
	private String workplaceID;
	/**区分*/
	private boolean isClassification;
	/**コメント*/
	private String comment;
	/**アラーム値メッセージ*/
	private String alarmValueMessage;
	/**アラーム値日付*/
	private GeneralDate alarmValueDate;
	/**アラーム項目*/
	private String alarmItem;
	
	private String employeeCode;
	
	private String employeeName;
	/**カテゴリ*/
	private int category;
	public AlarmExtraValueWkReDto(String employeeID, String workplaceID, boolean isClassification, String comment,
			String alarmValueMessage, GeneralDate alarmValueDate, String alarmItem, String employeeCode,
			String employeeName, int category) {
		super();
		this.employeeID = employeeID;
		this.workplaceID = workplaceID;
		this.isClassification = isClassification;
		this.comment = comment;
		this.alarmValueMessage = alarmValueMessage;
		this.alarmValueDate = alarmValueDate;
		this.alarmItem = alarmItem;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.category = category;
	}
	
	
}
