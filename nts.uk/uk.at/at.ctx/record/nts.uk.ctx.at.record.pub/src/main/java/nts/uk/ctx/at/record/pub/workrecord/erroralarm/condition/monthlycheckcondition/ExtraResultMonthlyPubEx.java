package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.AttendanceItemConditionPubExport;
@Getter
@Setter
@NoArgsConstructor
public class ExtraResultMonthlyPubEx {
	/**ID*/
	private String errorAlarmCheckID;
	/**並び順*/
	private int sortBy;
	/**名称*/
	private String nameAlarmExtraCon;
	/**使用区分*/
	private boolean useAtr;
	/**チェック項目の種類*/
	private int typeCheckItem;
	/**メッセージを太字にする*/
	private boolean messageBold;
	/**メッセージ色*/
	private String messageColor;
	/**表示メッセージ*/
	private String displayMessage;
	/**月次のチェック条件*/
	private AttendanceItemConditionPubExport checkConMonthly;
	public ExtraResultMonthlyPubEx(String errorAlarmCheckID, int sortBy, String nameAlarmExtraCon, boolean useAtr, int typeCheckItem, boolean messageBold, String messageColor, String displayMessage,
			AttendanceItemConditionPubExport checkConMonthly) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.sortBy = sortBy;
		this.nameAlarmExtraCon = nameAlarmExtraCon;
		this.useAtr = useAtr;
		this.typeCheckItem = typeCheckItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.displayMessage = displayMessage;
		this.checkConMonthly = checkConMonthly;
	}
	
	
}
