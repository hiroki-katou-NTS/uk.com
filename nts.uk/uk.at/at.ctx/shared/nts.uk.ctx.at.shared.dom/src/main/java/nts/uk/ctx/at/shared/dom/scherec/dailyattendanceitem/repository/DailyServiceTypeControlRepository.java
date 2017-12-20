package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;


import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;

public interface DailyServiceTypeControlRepository {
	
	List<DailyServiceTypeControl> getListDailyServiceTypeControl(BusinessTypeCode businessTypeCode, String companyId);
	
	void updateListDailyServiceTypeControl(List<DailyServiceTypeControl> lstDailyServiceTypeControl);

}
