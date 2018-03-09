package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlarmExtraValueWkReDto {

	/**職場ID*/
	private String workplaceID;
	
	/**  階層コード*/
	private String hierarchyCd;
	
	/**職場名*/
	private String workplaceName;
	
	/**社員ID*/
	private String employeeID;
	
	private String employeeCode;
	
	private String employeeName;
	
	/**アラーム値日付*/
	private String alarmValueDate;

	/**カテゴリ*/
	private String category;
	
	/**アラーム項目*/
	private String alarmItem;
	
	/**アラーム値メッセージ*/
	private String alarmValueMessage;
	
	/**コメント*/
	private String comment;

}
