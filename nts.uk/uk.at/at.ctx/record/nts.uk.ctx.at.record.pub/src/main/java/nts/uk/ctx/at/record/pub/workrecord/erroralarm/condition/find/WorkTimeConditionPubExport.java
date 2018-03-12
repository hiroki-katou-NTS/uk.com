package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieunv
 *
 */
@Getter
@Setter
public class WorkTimeConditionPubExport {
	private boolean useAtr;
    private int comparePlanAndActual;
    private boolean planFilterAtr;
    private List<String> planLstWorkTime;
    private boolean actualFilterAtr;
    private List<String> actualLstWorkTime;
    
	public WorkTimeConditionPubExport() {
		super();
	}

	public WorkTimeConditionPubExport(boolean useAtr, int comparePlanAndActual, boolean planFilterAtr,
			List<String> planLstWorkTime, boolean actualFilterAtr, List<String> actualLstWorkTime) {
		super();
		this.useAtr = useAtr;
		this.comparePlanAndActual = comparePlanAndActual;
		this.planFilterAtr = planFilterAtr;
		this.planLstWorkTime = planLstWorkTime;
		this.actualFilterAtr = actualFilterAtr;
		this.actualLstWorkTime = actualLstWorkTime;
	}
	
    
}
