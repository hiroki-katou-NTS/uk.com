/**
 * 3:00:37 PM Dec 4, 2017
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class WorkTimeConditionDto {
	private boolean useAtr;
    private int comparePlanAndActual;
    private boolean planFilterAtr;
    private List<String> planLstWorkTime;
    private boolean actualFilterAtr;
    private List<String> actualLstWorkTime;
    
	public WorkTimeConditionDto() {
		super();
	}
    
}
