package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExtraResultMonthlyDomainEventPubDto {
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
	private AttendanceItemConAdapterPubDto checkConMonthly;
	
	private SpecHolidayCheckConAdapterPubDto specHolidayCheckCon ;
	
	private CheckRemainNumberMonAdapterPubDto checkRemainNumberMon;
	
	private AgreementCheckCon36AdapterPubDto agreementCheckCon36;

	public ExtraResultMonthlyDomainEventPubDto(String errorAlarmCheckID, int sortBy, String nameAlarmExtraCon, boolean useAtr, int typeCheckItem, boolean messageBold, String messageColor, String displayMessage,
			AttendanceItemConAdapterPubDto checkConMonthly, SpecHolidayCheckConAdapterPubDto specHolidayCheckCon, CheckRemainNumberMonAdapterPubDto checkRemainNumberMon, AgreementCheckCon36AdapterPubDto agreementCheckCon36) {
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
		this.specHolidayCheckCon = specHolidayCheckCon;
		this.checkRemainNumberMon = checkRemainNumberMon;
		this.agreementCheckCon36 = agreementCheckCon36;
	}

	
	
	
	
}
