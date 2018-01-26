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
public class WorkTypeConditionPubExport {
	
	private boolean useAtr;
    private int comparePlanAndActual;
    private boolean planFilterAtr;
    private List<String> planLstWorkType;
    private boolean actualFilterAtr;
    private List<String> actualLstWorkType;
    
    public WorkTypeConditionPubExport(
    		boolean useAtr,
    	    int comparePlanAndActual,
    	    boolean planFilterAtr,
    	    List<String> planLstWorkType,
    	    boolean actualFilterAtr,
    	    List<String> actualLstWorkType) {
    	this.useAtr = useAtr;
    	this.comparePlanAndActual = comparePlanAndActual;
    	this.planFilterAtr = planFilterAtr;
    	this.planLstWorkType = planLstWorkType;
    	this.actualFilterAtr = actualFilterAtr;
    	this.actualLstWorkType = actualLstWorkType;
    }
	
	public WorkTypeConditionPubExport() {
		super();
	}
}

