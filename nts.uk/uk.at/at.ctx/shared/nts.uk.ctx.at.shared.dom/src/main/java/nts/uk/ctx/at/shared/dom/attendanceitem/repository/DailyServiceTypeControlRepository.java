package nts.uk.ctx.at.shared.dom.attendanceitem.repository;


import java.util.List;

import nts.uk.ctx.at.shared.dom.attendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;

public interface DailyServiceTypeControlRepository {
	
	List<DailyServiceTypeControl> getListDailyServiceTypeControl(BusinessTypeCode businessTypeCode, String companyId);
	
	void updateListDailyServiceTypeControl(List<DailyServiceTypeControl> lstDailyServiceTypeControl);

}
