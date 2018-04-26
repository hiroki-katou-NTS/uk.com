package nts.uk.ctx.at.function.dom.adapter.standardtime;

import lombok.Value;

@Value
public class AgreementOperationSettingImport {
	/** 起算月 */
	private int startingMonth;
	

	public AgreementOperationSettingImport(int startingMonth) {
		this.startingMonth = startingMonth;
	}
}
