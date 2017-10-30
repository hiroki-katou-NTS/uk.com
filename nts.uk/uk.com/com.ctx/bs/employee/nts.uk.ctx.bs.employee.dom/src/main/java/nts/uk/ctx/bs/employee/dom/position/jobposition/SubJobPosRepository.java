package nts.uk.ctx.bs.employee.dom.position.jobposition;

import java.util.List;

public interface SubJobPosRepository {
	public List<SubJobPosition> getSubJobPosByDeptId(String deptId);
}
