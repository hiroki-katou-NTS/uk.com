package nts.uk.ctx.at.function.dom.adapter.checkresultmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Check36AgreementValueImport {
	private boolean check36AgreementCon;
	
	private int errorValue;
	
	private int alarmValue;

	public Check36AgreementValueImport(boolean check36AgreementCon, int errorValue, int alarmValue) {
		super();
		this.check36AgreementCon = check36AgreementCon;
		this.errorValue = errorValue;
		this.alarmValue = alarmValue;
	}
	
	
}
