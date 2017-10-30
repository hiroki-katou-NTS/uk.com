/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.workingtype;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * @author danpv
 *
 */
public interface WorkingTypeChangedByEmpRepo {

	public WorkingTypeChangedByEmployment get(CompanyId companyId, EmploymentCode empCode);

	public void save(WorkingTypeChangedByEmployment workingType);

}
