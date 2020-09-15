package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayRemainingNumberDataInformation {
	private double expirationDate;
	//private remainingNumberData;
	private double totalRemainingNumber;
	private char employeeId;
	private GeneralDate closingEndDate;
	private GeneralDate ClosingStartDate;
}
