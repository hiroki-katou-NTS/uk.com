package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;


import java.util.List;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.primitivevalue.BusinessTypeCode;

public interface DailyServiceTypeControlRepository {
	
	List<DailyServiceTypeControl> getListDailyServiceTypeControl(BusinessTypeCode businessTypeCode, String companyId);
	
	void updateListDailyServiceTypeControl(List<DailyServiceTypeControl> lstDailyServiceTypeControl);

}
