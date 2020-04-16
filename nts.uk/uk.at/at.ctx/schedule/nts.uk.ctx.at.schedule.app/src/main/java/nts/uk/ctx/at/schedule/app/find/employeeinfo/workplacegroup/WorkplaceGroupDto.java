package nts.uk.ctx.at.schedule.app.find.employeeinfo.workplacegroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroup;

/**
 * @author anhdt
 *
 */
@Data
public class WorkplaceGroupDto {
	
	private List<IWorkplace> workplaces = new ArrayList<>();
	
	public WorkplaceGroupDto(List<WorkplaceGroup> wkpGroups) {
		for (WorkplaceGroup dom: wkpGroups) {
			this.workplaces.add(new IWorkplace(dom));
		}
	}
	
	@Data
	class IWorkplace {
		private String id;
		private String code;
		private String name;
		private Integer type;
		
		public IWorkplace(WorkplaceGroup dom) {
			this.id = dom.getWKPGRPID();
			this.code = dom.getWKPGRPCode().v();
			this.name = dom.getWKPGRPName().v();
			this.type = dom.getWKPGRPType().value;
		}
	}

}
