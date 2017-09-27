/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.workingtype;

import java.util.List;

import lombok.Getter;

/**
 * @author danpv
 *
 */
@Getter
public class ChangeableWorktypeGroup {

	private int no;
	
	private ChangeableWorkTypeGroupName name;
	
	private List<String> workTypeList;
}
