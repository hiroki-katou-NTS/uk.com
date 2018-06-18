package nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.AttendanceItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunImport;

@Getter
@Setter
@NoArgsConstructor
public class ExtraResultMonthlyDomainEventDto {
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
	private AttendanceItemConAdapterDto checkConMonthly;
	
	private SpecHolidayCheckConFunImport specHolidayCheckCon ;
	
	private CheckRemainNumberMonFunImport checkRemainNumberMon;
	
	private AgreementCheckCon36FunImport agreementCheckCon36;
	
	public ExtraResultMonthlyDomainEventDto(String errorAlarmCheckID, int sortBy, String nameAlarmExtraCon, boolean useAtr, int typeCheckItem, boolean messageBold, String messageColor, String displayMessage,
			AttendanceItemConAdapterDto checkConMonthly) {
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

	public ExtraResultMonthlyDomainEventDto(String errorAlarmCheckID, int sortBy, String nameAlarmExtraCon, boolean useAtr, int typeCheckItem, boolean messageBold, String messageColor, String displayMessage,
			AttendanceItemConAdapterDto checkConMonthly, SpecHolidayCheckConFunImport specHolidayCheckCon, CheckRemainNumberMonFunImport checkRemainNumberMon, AgreementCheckCon36FunImport agreementCheckCon36) {
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
