/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EventFinderParam {
	
	public String wkpId;                     // ・wkpid
	public String wkpGrId;                   // ・wkpGrId
	public String startDate;            	 // ・期間
	public String endDate;    	        	 // ・期間
	public TargetOrgIdenInfor targetOrgIdenInfor;
}
