package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class KTGRsvLeaveInfoImport {

	//付与前残数
	private Double befRemainDay;
	//付与後残数
	private Double aftRemainDay;
	/*積立年休残数*/
	private Double remainingDays;
	//付与日
	private GeneralDate grantDay;
}
