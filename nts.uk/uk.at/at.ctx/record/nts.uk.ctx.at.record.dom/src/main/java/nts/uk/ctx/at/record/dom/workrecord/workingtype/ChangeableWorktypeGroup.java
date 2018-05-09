/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.workingtype;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author danpv
 *
 */
@Getter
@Setter
public class ChangeableWorktypeGroup extends DomainObject{

	private int no;
	
	private ChangeableWorkTypeGroupName name;
	
	private Set<String> workTypeList;
	
	public ChangeableWorktypeGroup(int no, String name) {
		this.no = no;
		this.name = new ChangeableWorkTypeGroupName(name);
		this.workTypeList = new HashSet<>();
	}
	
	public ChangeableWorktypeGroup(int no, String name, Set<String> workTypeList) {
		this.no = no;
		this.name = new ChangeableWorkTypeGroupName(name);
		this.workTypeList = workTypeList;
	}
}
