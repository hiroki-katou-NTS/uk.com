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
    
}
