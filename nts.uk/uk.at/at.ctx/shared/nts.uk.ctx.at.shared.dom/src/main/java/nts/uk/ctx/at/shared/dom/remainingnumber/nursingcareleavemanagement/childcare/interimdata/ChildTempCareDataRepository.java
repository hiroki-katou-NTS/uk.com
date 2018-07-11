package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ChildTempCareDataRepository {
	
	public List<ChildTempCareData> findByEmpIdInPeriod(String employeeId, GeneralDate startDate, GeneralDate endDate);

}
