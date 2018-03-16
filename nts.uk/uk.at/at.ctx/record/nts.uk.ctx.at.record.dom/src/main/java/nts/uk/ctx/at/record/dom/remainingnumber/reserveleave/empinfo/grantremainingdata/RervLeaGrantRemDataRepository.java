package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.util.List;

public interface RervLeaGrantRemDataRepository {

	List<ReserveLeaveGrantRemainingData> find(String employeeId);

}
