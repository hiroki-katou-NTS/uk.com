package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class AlarmExtraValueWkReDto {

	/**職場ID*/
	private String workplaceName;
	
	/**社員ID*/
	private String employeeID;
	
	private String employeeCode;
	
	private String employeeName;
	
	/**アラーム値日付*/
	private GeneralDate alarmValueDate;

	/**カテゴリ*/
	private String category;
	
	/**アラーム項目*/
	private String alarmItem;
	
	/**アラーム値メッセージ*/
	private String alarmValueMessage;
	
	/**コメント*/
	private String comment;



	



	
	
}
