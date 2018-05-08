/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting.old;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.ChangeableWorktypeGroup;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.WorkingTypeChangedByEmployment;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.WorkingTypeChangedByEmpRepo;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.old.KrcmtWorktypeChangeable;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.old.KrcmtWorktypeChangeablePk;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaWorkingTypeChangedByEmploymentRepository extends JpaRepository implements WorkingTypeChangedByEmpRepo {

	private final String GET_ALL_OF_EMPLOYEE = "SELECT wtc FROM KrcmtWorktypeChangeable wtc"
			+ " WHERE wtc.pk.cid = :companyId AND wtc.pk.empCode = :employeeCode";

	@Override
	public WorkingTypeChangedByEmployment get(CompanyId companyId, EmploymentCode empCode) {
		List<KrcmtWorktypeChangeable> entities = this.queryProxy()
				.query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class).setParameter("companyId", companyId.v())
				.setParameter("employeeCode", empCode.v()).getList();
		if (entities.isEmpty()) {
			// default company
			entities = this.queryProxy().query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class)
					.setParameter("companyId", "000000000000-0000").setParameter("employeeCode", "0").getList();
		}
		Map<Integer, ChangeableWorktypeGroup> map = new HashMap<>();
		entities.forEach(ent -> {
			ChangeableWorktypeGroup group = null;
			if (!map.containsKey(ent.pk.workTypeGroupNo.intValue())) {
				group = new ChangeableWorktypeGroup(ent.pk.workTypeGroupNo.intValue(), ent.workTypeGroupName);
			} else {
				group = map.get(ent.pk.workTypeGroupNo.intValue());
			}
			group.getWorkTypeList().add(ent.pk.workTypeCode);
			map.put(ent.pk.workTypeGroupNo.intValue(), group);
		});

		List<ChangeableWorktypeGroup> groups = new ArrayList<>(map.values());
		groups.forEach(group -> {
			if (group.getWorkTypeList().size() == 1) {
				List<String> workTypes = new ArrayList<>(group.getWorkTypeList());
				if (workTypes.get(0) == "") {
					group.getWorkTypeList().clear();
				}
			}
		});

		return new WorkingTypeChangedByEmployment(companyId, empCode, new ArrayList<>(map.values()));
	}

	@Override
	public void save(WorkingTypeChangedByEmployment workingType) {
		String cid = workingType.getCompanyId().v();
		String empCode = workingType.getEmpCode().v();

		// delete all old results
		List<KrcmtWorktypeChangeable> deleteEntities = this.queryProxy()
				.query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class).setParameter("companyId", cid)
				.setParameter("employeeCode", empCode).getList();
		this.commandProxy().removeAll(deleteEntities);
		this.getEntityManager().flush();
		// create and insert new results
		workingType.getChangeableWorkTypeGroups().forEach(group -> {
			if (group.getWorkTypeList().isEmpty()) {
				KrcmtWorktypeChangeablePk pk = new KrcmtWorktypeChangeablePk(cid, empCode,
						new BigDecimal(group.getNo()), "　");
				KrcmtWorktypeChangeable entity = new KrcmtWorktypeChangeable(pk, group.getName().v());
				this.commandProxy().insert(entity);
			} else {
				group.getWorkTypeList().forEach(workTypeCode -> {
					KrcmtWorktypeChangeablePk pk = new KrcmtWorktypeChangeablePk(cid, empCode,
							new BigDecimal(group.getNo()), workTypeCode);
					KrcmtWorktypeChangeable entity = new KrcmtWorktypeChangeable(pk,
							!group.getName().v().isEmpty() ? group.getName().v() : "　");
					this.commandProxy().insert(entity);
				});
			}
		});

	}

}
