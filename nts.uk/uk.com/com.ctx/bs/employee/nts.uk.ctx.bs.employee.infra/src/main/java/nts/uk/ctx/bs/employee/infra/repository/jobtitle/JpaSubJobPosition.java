package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtSubJobPosition;

public class JpaSubJobPosition extends JpaRepository implements SubJobPosRepository{

	private static final String SELECT_SUB_JOB_POS_BY_DEPT_ID = "SELECT s FROM BsymtSubJobPosition s"
			+ " WHERE s.affiDeptId = :affiDeptId";
	
	private SubJobPosition toDomainEmployee(BsymtSubJobPosition entity){
		val domain = SubJobPosition.createFromJavaType(entity.getBsymtSubJobPositionPK().getSubJobPosId(), 
				entity.getAffiDeptId(), entity.getJobTitleId(), entity.getStrD(), entity.getEndD());
		return domain;
	}
	
	@Override
	public List<SubJobPosition> getSubJobPosByDeptId(String deptId) {
		return this.queryProxy().query(SELECT_SUB_JOB_POS_BY_DEPT_ID, BsymtSubJobPosition.class)
				.setParameter("affiDeptId", deptId).getList().stream()
				.map(x -> this.toDomainEmployee(x)).collect(Collectors.toList());
	}

}
