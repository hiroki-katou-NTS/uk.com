/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.workingtype;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * @author danpv
 *
 */
@Getter
@Setter
public class WorkingTypeChangedByEmployment extends AggregateRoot {

	private CompanyId companyId;

	private EmploymentCode empCode;

	private List<ChangeableWorktypeGroup> changeableWorkTypeGroups;

	public WorkingTypeChangedByEmployment(CompanyId companyId, EmploymentCode empCode,
			List<ChangeableWorktypeGroup> changeableWorkTypeGroups) {
		this.companyId = companyId;
		this.empCode = empCode;
		this.changeableWorkTypeGroups = changeableWorkTypeGroups;
	}

}
