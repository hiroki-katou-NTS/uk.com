package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtSubJobPosition;

@Stateless
public class JpaSubJobPosition extends JpaRepository implements SubJobPosRepository {

	private static final String SELECT_SUB_JOB_POS_BY_DEPT_ID = "SELECT s FROM BsymtSubJobPosition s"
			+ " WHERE s.affiDeptId = :affiDeptId";

	private static final String SELECT_BY_EID_STD = "select s from BsymtSubJobPosition s"
			+ " where s.bsymtCurrAffiDept.sid = :empId and s.strD <= :std and s.endD >= :std";

	private SubJobPosition toDomainEmployee(BsymtSubJobPosition entity) {
		val domain = SubJobPosition.createFromJavaType(entity.subJobPosId, entity.affiDeptId, entity.jobTitleId,
				entity.strD, entity.endD);
		return domain;
	}

	@Override
	public List<SubJobPosition> getSubJobPosByDeptId(String deptId) {
		return this.queryProxy().query(SELECT_SUB_JOB_POS_BY_DEPT_ID, BsymtSubJobPosition.class)
				.setParameter("affiDeptId", deptId).getList().stream().map(x -> this.toDomainEmployee(x))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<SubJobPosition> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		Optional<BsymtSubJobPosition> dataOpt = this.queryProxy().query(SELECT_BY_EID_STD, BsymtSubJobPosition.class)
				.setParameter("empId", employeeId).setParameter("std", standandDate).getSingle();
		if (dataOpt.isPresent()) {
			BsymtSubJobPosition ent = dataOpt.get();
			return Optional.of(SubJobPosition.createFromJavaType(ent.subJobPosId, ent.affiDeptId, ent.jobTitleId,
					ent.strD, ent.endD));
		}
		return Optional.empty();
	}

}
