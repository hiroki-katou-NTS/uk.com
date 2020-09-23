/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChangePeriodModeParam {
	
	public String viewMode;    
	
	public String startDate;            	 
	public String endDate;    
	
	public int unit;
	public String workplaceId;     	         
	public String workplaceGroupId;
	public List<String> sids;
	public List<ShiftMasterMapWithWorkStyle> listShiftMasterNotNeedGetNew;
	public boolean getActualData;
	
	
}
