package nts.uk.ctx.bs.employee.pub.jobtitle;

import java.util.List;

public interface SubJobPosPub {
	public List<SubJobPosExport> getSubJobPosByDeptId(String deptId);
}
