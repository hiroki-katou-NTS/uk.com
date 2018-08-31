package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Check36AgreementValue {
	
	private boolean check36AgreementCon;
	
	private int errorValue;
	
	private int alarmValue;

	public Check36AgreementValue(boolean check36AgreementCon, int errorValue, int alarmValue) {
		super();
		this.check36AgreementCon = check36AgreementCon;
		this.errorValue = errorValue;
		this.alarmValue = alarmValue;
	}
	
	

}
