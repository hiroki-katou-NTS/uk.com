package nts.uk.ctx.at.record.dom.adapter.employee;

import java.util.List;

public interface NarrowEmployeeAdapter {
	List<String> findByEmpId(List<String> sID, int roleType);
}
