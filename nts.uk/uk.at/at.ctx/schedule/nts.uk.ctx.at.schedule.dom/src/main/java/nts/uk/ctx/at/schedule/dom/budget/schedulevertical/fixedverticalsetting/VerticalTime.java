package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerticalTime {

	/*会社ID*/
	private String companyId;
	
	/**/
	private int FixedVerticalNo;
	
	/*表示区分*/
	private DisplayAtr displayAtr;
	
	/*時刻*/
	private StartClock startClock;
	
}
