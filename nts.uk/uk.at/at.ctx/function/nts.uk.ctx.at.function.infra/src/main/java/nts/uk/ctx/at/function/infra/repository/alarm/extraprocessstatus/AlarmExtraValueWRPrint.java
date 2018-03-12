package nts.uk.ctx.at.function.infra.repository.alarm.extraprocessstatus;

import lombok.Value;


@Value
public class AlarmExtraValueWRPrint {
	/**社員ID*/
	private String employeeID;
	/**職場ID*/
	private String workplaceID;
	/**区分*/
	private String isClassification;
	/**コメント*/
	private String comment;
	/**アラーム値メッセージ*/
	private String alarmValueMessage;
	/**アラーム値日付*/
	private String alarmValueDate;
	/**アラーム項目*/
	private String alarmItem;
	
	private String employeeCode;
	
	private String employeeName;
	/**カテゴリ*/
	private String category;
}
