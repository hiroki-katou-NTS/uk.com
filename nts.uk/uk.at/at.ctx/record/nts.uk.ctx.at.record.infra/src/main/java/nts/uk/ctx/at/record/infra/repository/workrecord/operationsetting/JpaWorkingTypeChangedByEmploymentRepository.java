/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.ChangeableWorktypeGroup;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.WorkingTypeChangedByEmployment;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.WorkingTypeChangedByEmploymentRepoInterface;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtWorktypeChangeable;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaWorkingTypeChangedByEmploymentRepository extends JpaRepository
		implements WorkingTypeChangedByEmploymentRepoInterface {

	private final String GET_ALL_OF_EMPLOYEE = "SELECT wtc FROM KrcmtWorktypeChangeable wtc"
			+ " WHERE wtc.pk.cid = :companyId AND wtc.pk.empCode = :employeeCode";

	@Override
	public WorkingTypeChangedByEmployment getWorkingTypeChangedByEmployment(CompanyId companyId,
			EmploymentCode empCode) {
		List<KrcmtWorktypeChangeable> entities = this.queryProxy()
				.query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class)
				.setParameter("companyId", companyId.v())
				.setParameter("employeeCode", empCode.v())
				.getList();
		Map<Integer, ChangeableWorktypeGroup> map = new HashMap<>();
		entities.forEach(ent -> {
			ChangeableWorktypeGroup group = null;
			if (!map.containsKey(ent.pk.worktypeGroupNo.intValue())) {
				group = new ChangeableWorktypeGroup(ent.pk.worktypeGroupNo.intValue(), ent.worktypeGroupName);
			} else {
				group = map.get(ent.pk.worktypeGroupNo);
			}
			group.getWorkTypeList().add(ent.pk.worktypeCode);
			map.put(ent.pk.worktypeGroupNo.intValue(), group);
		});
		return new WorkingTypeChangedByEmployment(companyId, empCode, new ArrayList<>(map.values()));
	}

}
